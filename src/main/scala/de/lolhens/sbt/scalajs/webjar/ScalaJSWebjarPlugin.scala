package de.lolhens.sbt.scalajs.webjar

import de.lolhens.sbt.scalajs.webjar.WebjarPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import org.scalajs.sbtplugin.{ScalaJSPlugin, Stage}
import sbt.Keys._
import sbt._

object ScalaJSWebjarPlugin extends AutoPlugin {
  override def requires: Plugins = ScalaJSPlugin && WebjarPlugin

  override lazy val projectSettings = Seq(
    Compile / fastOptJS / crossTarget := (Compile / webjarArtifacts / crossTarget).value,
    Compile / fullOptJS / crossTarget := (Compile / webjarArtifacts / crossTarget).value,

    Compile / scalaJSLinkedFile / artifactPath := Def.settingDyn {
      scalaJSStage.value match {
        case Stage.FastOpt => Compile / fastOptJS / artifactPath
        case Stage.FullOpt => Compile / fullOptJS / artifactPath
      }
    }.value,

    Compile / webjarMainResource := (Compile / webjarResourcePath).value + "/" + (Compile / scalaJSLinkedFile / artifactPath).value.getName,

    Compile / webjarArtifacts := Seq(
      (Compile / scalaJSLinkedFile).value.data,
      (Compile / scalaJSLinkedFile).value.metadata(scalaJSSourceMap),
    )
  )
}
