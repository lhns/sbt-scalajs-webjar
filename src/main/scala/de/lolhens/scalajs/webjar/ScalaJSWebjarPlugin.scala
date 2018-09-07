package de.lolhens.scalajs.webjar

import de.lolhens.scalajs.webjar.WebjarPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.AutoImport._
import sbt.Keys._
import sbt._

object ScalaJSWebjarPlugin extends AutoPlugin {

  object autoImport {
    lazy val isDevMode = taskKey[Boolean]("Whether the app runs in development mode")
    lazy val scalaJS = taskKey[Seq[File]]("ScalaJS output files")
  }

  import autoImport._

  override def requires: Plugins = ScalaJSPlugin && WebjarPlugin

  override lazy val projectSettings = Seq(
    scalaJSUseMainModuleInitializer := true,

    skip in packageJSDependencies := false,
    Compile / packageJSDependencies / crossTarget := (Compile / resourceManaged).value,

    fastOptJS / scalaJS := Seq(
      (Compile / fastOptJS).value.data,
      (Compile / fastOptJS).value.map(file => new File(file.toString + ".map")).data,
      (Compile / packageJSDependencies).value
    ),

    fullOptJS / scalaJS := Seq(
      (Compile / fullOptJS).value.data,
      (Compile / fullOptJS).value.map(file => new File(file.toString + ".map")).data,
      (Compile / packageMinifiedJSDependencies).value
    ),

    isDevMode := {
      val devCommands = Seq("run", "compile", "re-start", "reStart", "runAll")
      val executedCommandKey =
        state.value.history.currentOption
          .flatMap(_.commandLine.takeWhile(c => !c.isWhitespace).split(Array('/', ':')).lastOption)
          .getOrElse("")
      devCommands.contains(executedCommandKey)
    },

    scalaJS := Def.taskDyn {
      if (isDevMode.value)
        fastOptJS / scalaJS
      else
        fullOptJS / scalaJS
    }.value,

    webjarMappings ++= scalaJS.value.map { file =>
      file -> s"js/${file.name}"
    }
  )
}
