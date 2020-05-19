package de.lolhens.sbt.scalajs.webjar

import de.lolhens.sbt.scalajs.webjar.ScalaJSWebjarPlugin.scalaJSLinkedFileTask
import de.lolhens.sbt.scalajs.webjar.WebjarPlugin.autoImport._
import sbt.Keys._
import sbt._
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin
import scalajsbundler.sbtplugin.ScalaJSBundlerPlugin.autoImport._

object ScalaJSBundlerWebjarPlugin extends AutoPlugin {
  override def requires: Plugins = ScalaJSBundlerPlugin && WebjarPlugin

  override lazy val projectSettings = Seq(
    Compile / npmUpdate / crossTarget := (Compile / webjarArtifacts / crossTarget).value,

    Compile / webjarMainResourceName := scalaJSLinkedFileTask(Compile / _ / artifactPath).value.name.stripSuffix(".js"),

    Compile / webjarArtifacts := {
      val attributedFiles = scalaJSLinkedFileTask(Compile / _ / webpack).value

      Seq(
        attributedFiles.find(_.metadata.get(BundlerFileTypeAttr).exists(_ == BundlerFileType.ApplicationBundle)).map(_.data),
        attributedFiles.find(_.metadata.get(BundlerFileTypeAttr).exists(_ == BundlerFileType.Asset)).map(_.data),
      ).flatMap(_.toList)
    }
  )
}
