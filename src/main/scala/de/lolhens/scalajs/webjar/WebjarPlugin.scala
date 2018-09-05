package de.lolhens.scalajs.webjar

import sbt.Keys._
import sbt._

import scala.language.implicitConversions

object WebjarPlugin extends AutoPlugin {

  object autoImport {
    lazy val Webjar = config("webjar")
    lazy val packageWebjar = taskKey[File]("Produces a webjar.")

    implicit def webjarProject(project: Project): WebjarProject = WebjarProject.webjarProject(project)
  }

  import autoImport._

  override def projectConfigurations: Seq[Configuration] = Seq(
    Webjar
  )

  override lazy val projectSettings: Seq[Def.Setting[_]] =
    Defaults.packageTaskSettings(Webjar / packageWebjar, Def.task {
      def webjarMappings = (packageWebjar / mappings).value

      def artifactName = name.value

      def artifactVersion = version.value

      webjarMappings.map {
        case (file, mapping) =>
          file -> s"META-INF/resources/webjars/$artifactName/$artifactVersion/$mapping"
      }
    }) ++ Seq[Def.Setting[_]](
      packageWebjar / mappings := Seq.empty,

      packageWebjar / artifact := {
        val artifactValue = (packageWebjar / artifact).value
        artifactValue.withName(artifactValue.name + "-webjar")
      },

      Webjar / exportedProductJars := {
        val data = (Webjar / packageWebjar).value
        val attributed = Attributed.blank(data)
          .put(artifact.key, (packageWebjar / artifact).value)
          .put(configuration.key, Webjar)

        Seq(attributed)
      },

      Webjar / exportJars := true
    )
}
