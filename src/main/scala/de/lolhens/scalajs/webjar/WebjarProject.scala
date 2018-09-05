package de.lolhens.scalajs.webjar

import de.lolhens.scalajs.webjar.WebJarPlugin.autoImport._
import sbt._

import scala.language.implicitConversions

class WebJarProject(val self: Project) extends AnyVal {
  def webJar: ClasspathDep[ProjectReference] = self % s"${Compile.name}->${WebJar.name}"
}

object WebJarProject {
  implicit def webJarProject(project: Project): WebJarProject = new WebJarProject(project)
}
