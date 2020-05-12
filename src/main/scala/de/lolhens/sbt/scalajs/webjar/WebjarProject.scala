package de.lolhens.sbt.scalajs.webjar

import de.lolhens.sbt.scalajs.webjar.WebjarPlugin.autoImport._
import de.lolhens.sbt.scalajs.webjar.WebjarProject._
import sbt.Keys._
import sbt._

import scala.language.implicitConversions

class WebjarProject(val self: Project) extends AnyVal {
  def webjar: Project = {
    Project(self.id + "-webjar", self.base.toPath.resolve(".webjar").toFile)
      .settings(
        name := (self / name).value + "-webjar",
        version := (self / version).value,

        scalaVersion := (self / scalaVersion).value,

        watchSources := (self / watchSources).value,

        self / Compile / webjarArtifacts / crossTarget := {
          (Compile / classDirectory).value
            .toPath
            .resolve(webjarPath((self / name).value, (self / version).value))
            .toFile
        },

        Compile / compile := (Compile / compile).dependsOn(self / Compile / webjarArtifacts).value
      )
  }
}

object WebjarProject {
  def webjarPath(library: String, version: String): String =
    s"META-INF/resources/webjars/$library/$version"
}
