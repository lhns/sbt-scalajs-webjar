package de.lolhens.scalajs.webjar

import sbt.Keys._
import sbt._

import scala.language.implicitConversions

object WebjarPlugin extends AutoPlugin {

  object autoImport {
    lazy val webjarMappings = taskKey[Seq[(File, String)]]("Defines the mappings from a file to a path, used by packaging the WebJar.")

    implicit def webjarProject(project: Project): WebjarProject = WebjarProject.webjarProject(project)
  }

  import autoImport._

  override lazy val projectSettings: Seq[Def.Setting[_]] = Seq(
    webjarMappings := (resources / mappings).value
  )
}
