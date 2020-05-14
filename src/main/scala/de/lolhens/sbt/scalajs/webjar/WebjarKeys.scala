package de.lolhens.sbt.scalajs.webjar

import sbt.{File, Project, taskKey}

import scala.language.implicitConversions

class WebjarKeys {
  lazy val webjarArtifacts = taskKey[Seq[File]]("WebJar artifacts.")

  implicit def webjarProject(project: Project): WebjarProject = new WebjarProject(project)
}
