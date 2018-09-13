package de.lolhens.scalajs.webjar

import de.lolhens.scalajs.webjar.WebjarPlugin.autoImport._
import sbt.Keys._
import sbt._

import scala.language.implicitConversions

class WebjarProject(val self: Project) extends AnyVal {
  def webjar: Project = {
    Project(self.id + "-webjar", self.base.toPath.resolve(".webjar").toFile)
      .settings(
        name := (self / name).value + "-webjar",
        version := (self / version).value,

        watchSources := (self / watchSources).value,

        exportJars := true,

        Compile / packageBin / mappings := {
          def m = (self / webjarMappings).value

          def n = (self / name).value

          def v = (self / version).value

          m.map {
            case (file, mapping) =>
              file -> s"META-INF/resources/webjars/$n/$v/$mapping"
          }
        }
      )
  }
}

trait WebjarProjectOps {
  implicit def webjarProject(project: Project): WebjarProject = new WebjarProject(project)
}
