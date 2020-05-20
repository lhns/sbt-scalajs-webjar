package de.lolhens.sbt.scalajs.webjar

import de.lolhens.sbt.scalajs.webjar.ScalaJSWebjarPlugin.stagedOptJS
import de.lolhens.sbt.scalajs.webjar.WebjarPlugin.autoImport._
import sbt.Keys._
import sbt._
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._

object ScalaJSBundlerWebjarPlugin extends AutoPlugin {
  override def requires: Plugins = ScalaJSBundlerPlugin && WebjarPlugin

  override lazy val projectSettings: Seq[Setting[_]] =
    Seq(
      Compile / npmUpdate / crossTarget := (Compile / webjarArtifacts / crossTarget).value,

      Compile / webjarMainResourceName := stagedOptJS(Compile / _ / artifactPath).value.name.stripSuffix(".js") + "-bundle.js",

      Compile / webjarArtifacts := {
        val attributedFiles = stagedOptJS(Compile / _ / webpack).value

        Seq(
          attributedFiles.find(_.metadata.get(BundlerFileTypeAttr).exists(_ == BundlerFileType.ApplicationBundle)).map(_.data),
          attributedFiles.find(_.metadata.get(BundlerFileTypeAttr).exists(_ == BundlerFileType.Asset)).map(_.data),
        ).flatMap(_.toList)
      }
    )
}
