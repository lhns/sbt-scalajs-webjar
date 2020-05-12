package de.lolhens.sbt.scalajs.webjar

import WebjarPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import sbt.Keys._
import sbt._

object ScalaJSWebjarPlugin extends AutoPlugin {
  override def requires: Plugins = ScalaJSPlugin && WebjarPlugin

  override lazy val projectSettings = Seq(
    Compile / fastOptJS / crossTarget := (Compile / webjarArtifacts / crossTarget).value,
    Compile / fullOptJS / crossTarget := (Compile / webjarArtifacts / crossTarget).value,

    Compile / webjarArtifacts := Seq(
      (Compile / scalaJSLinkedFile).value.data,
      (Compile / scalaJSLinkedFile).value.metadata(scalaJSSourceMap),
    )
  )
}
