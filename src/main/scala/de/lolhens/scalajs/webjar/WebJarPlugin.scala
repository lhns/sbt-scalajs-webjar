package de.lolhens.scalajs.webjar

import sbt.Defaults.ConfigZero
import sbt.Keys._
import sbt._

import scala.language.implicitConversions

object WebJarPlugin extends AutoPlugin {

  object autoImport {
    lazy val WebJar = config("webjar")
    lazy val packageWebJar = taskKey[File]("Produces a WebJar.")

    implicit def webJarProject(project: Project): WebJarProject = WebJarProject.webJarProject(project)
  }

  import autoImport._

  override def projectConfigurations: Seq[Configuration] = Seq(
    WebJar
  )

  override lazy val projectSettings: Seq[Def.Setting[_]] =
    inConfig(WebJar) {
      Defaults.packageTaskSettings(packageWebJar, Def.task {
        def webjarMappings = (ConfigZero / packageWebJar / mappings).value

        def artifactName = name.value

        def artifactVersion = version.value

        webjarMappings.map {
          case (file, mapping) =>
            file -> s"META-INF/resources/webjars/$artifactName/$artifactVersion/$mapping"
        }
      }) ++ Seq(
        exportedProductJars := {
          val data = packageWebJar.value
          val attributed = Attributed.blank(data)
            .put(artifact.key, (packageWebJar / artifact).value)
            .put(configuration.key, WebJar)

          Seq(attributed)
        },

        exportJars := true
      )
    } ++ Seq(
      packageWebJar / mappings := Seq.empty
    )
}
