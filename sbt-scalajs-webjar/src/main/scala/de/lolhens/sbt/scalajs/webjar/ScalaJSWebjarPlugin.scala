package de.lolhens.sbt.scalajs.webjar

import de.lolhens.sbt.scalajs.webjar.WebjarPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import org.scalajs.sbtplugin.{ScalaJSPlugin, Stage}
import sbt.Keys._
import sbt._

object ScalaJSWebjarPlugin extends AutoPlugin {
  override def requires: Plugins = ScalaJSPlugin && WebjarPlugin

  private[webjar] def scalaJSLinkedFileTask[A](f: TaskKey[Attributed[File]] => Def.Initialize[A]): Def.Initialize[A] =
    Def.settingDyn {
      scalaJSStage.value match {
        case Stage.FastOpt => f(fastOptJS)
        case Stage.FullOpt => f(fullOptJS)
      }
    }

  override lazy val projectSettings = Seq(
    Compile / fastOptJS / crossTarget := (Compile / webjarArtifacts / crossTarget).value,
    Compile / fullOptJS / crossTarget := (Compile / webjarArtifacts / crossTarget).value,

    Compile / webjarMainResourceName := scalaJSLinkedFileTask(Compile / _ / artifactPath).value.name,

    Compile / webjarArtifacts := {
      val attributedFile = (Compile / scalaJSLinkedFile).value

      Seq(
        attributedFile.data,
        attributedFile.metadata(scalaJSSourceMap),
      )
    }
  )
}
