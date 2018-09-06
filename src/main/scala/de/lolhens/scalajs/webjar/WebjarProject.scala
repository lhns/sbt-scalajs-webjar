package de.lolhens.scalajs.webjar

import de.lolhens.scalajs.webjar.WebjarPlugin.autoImport._
import sbt._

import scala.language.implicitConversions

class WebjarProject(val self: Project) extends AnyVal {
  def webjar: ClasspathDep[ProjectReference] = self % s"${Compile.name}->${Webjar.name}"
}

object WebjarProject {
  implicit def webjarProject(project: Project): WebjarProject = new WebjarProject(project)
}
