package de.lolhens.sbt.scalajs.webjar

import de.lolhens.sbt.scalajs.webjar.ScalaJSWebjarPlugin.stagedOptJS
import de.lolhens.sbt.scalajs.webjar.WebjarPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._

object ScalaJSBundlerWebjarPlugin extends AutoPlugin {
  override def requires: Plugins = ScalaJSBundlerPlugin && WebjarPlugin

  override lazy val projectSettings: Seq[Setting[_]] = Seq(
    Compile / webjarMainResourceName := stagedOptJS(Compile / _ / artifactPath).value.name.stripSuffix(".js") + "-bundle.js",

    Compile / webjarArtifacts := {
      val attributedFiles = stagedOptJS(Compile / _ / webpack).value
      val emitSourceMaps = {
        stagedOptJS(e => Def.optional(e / webpackEmitSourceMaps)(identity)).value
          .getOrElse(stagedOptJS(_ / scalaJSLinkerConfig).value.sourceMap)
      }
      val target = (Compile / webjarArtifacts / crossTarget).value

      def sourceMapFor(file: File): Option[File] =
        if (emitSourceMaps && file.getName.toLowerCase.endsWith(".js"))
          Some(new File(file.toString + ".map"))
        else
          None

      val artifacts = Seq(
        attributedFiles.find(_.metadata.get(BundlerFileTypeAttr).exists(_ == BundlerFileType.ApplicationBundle)).map(_.data),
        attributedFiles.find(_.metadata.get(BundlerFileTypeAttr).exists(_ == BundlerFileType.Asset)).map(_.data),
      )
        .flatMap(_.toList)
        .flatMap(file => file +: sourceMapFor(file).toList)
        .map(file => (file, target / file.name))

      IO.copy(artifacts)

      artifacts.map(_._2)
    }
  )
}
