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

    webjarMappings ++= Seq(
      (Compile / fullOptJS).value.data,
      (Compile / fullOptJS).value.map(file => new File(file.toString + ".map")).data,
      (Compile / packageMinifiedJSDependencies).value
    ).map { file =>
      file -> file.name
    }
  )
}
