package de.lolhens.scalajs.webjar

import de.lolhens.scalajs.webjar.WebjarPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._
import sbt.Keys._
import sbt._

object ScalaJSWebjarPlugin extends AutoPlugin {
  override def requires: Plugins = ScalaJSPlugin && WebjarPlugin

  override lazy val projectSettings = Seq(
    scalaJSUseMainModuleInitializer := true,

    skip in packageJSDependencies := false,

    Compile / fullOptJS / crossTarget := (Compile / webjarArtifacts / crossTarget).value,
    Compile / fastOptJS / crossTarget := (Compile / webjarArtifacts / crossTarget).value,
    Compile / packageJSDependencies / crossTarget := (Compile / webjarArtifacts / crossTarget).value,
    Compile / packageMinifiedJSDependencies / crossTarget := (Compile / webjarArtifacts / crossTarget).value,

    Compile / webjarArtifacts := Seq(
      (Compile / fullOptJS).value.data,
      (Compile / fullOptJS).value.map(file => new File(file.toString + ".map")).data,
      (Compile / packageMinifiedJSDependencies).value
    ),

    webjarMappings ++= (Compile / webjarArtifacts).value.map(file => file -> file.name)
  )
}
