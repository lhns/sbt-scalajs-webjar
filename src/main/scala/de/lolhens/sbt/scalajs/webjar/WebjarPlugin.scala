package de.lolhens.sbt.scalajs.webjar

import sbt._

import scala.language.implicitConversions

object WebjarPlugin extends AutoPlugin {

  object autoImport extends WebjarKeys

  import autoImport._

  override def derivedProjects(proj: ProjectDefinition[_]): Seq[Project] = proj match {
    case project: Project =>
      Seq(project.webjar)

    case _ => Seq.empty
  }
}
