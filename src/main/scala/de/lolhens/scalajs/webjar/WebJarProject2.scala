package de.lolhens.scalajs.webjar

import de.lolhens.scalajs.webjar.WebJarPlugin2.autoImport._
import sbt._

import scala.language.implicitConversions

class WebJarProject2(val self: Project) extends AnyVal {
  def webJar: ClasspathDep[ProjectReference] = self % s"${Compile.name}->${WebJar.name}"
}

object WebJarProject2 {
  implicit def webJarProject(project: Project): WebJarProject2 = new WebJarProject2(project)
}
