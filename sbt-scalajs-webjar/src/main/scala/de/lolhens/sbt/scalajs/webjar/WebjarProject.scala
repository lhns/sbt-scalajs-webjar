package de.lolhens.sbt.scalajs.webjar

import de.lolhens.sbt.scalajs.webjar.WebjarPlugin.autoImport._
import sbt.Keys._
import sbt._

import java.nio.charset.StandardCharsets
import scala.language.implicitConversions

class WebjarProject(val parent: Project) extends AnyVal {
  def webjar: Project = {
    Project(parent.id + "-webjar", parent.base.toPath.resolve(".webjar").toFile)
      .settings(
        name := (parent / name).value + "-webjar",
        normalizedName := (parent / normalizedName).value + "-webjar",
        version := (parent / version).value,

        scalaVersion := (parent / scalaVersion).value,

        watchSources := (parent / watchSources).value,

        parent / Compile / webjarResourcePath := s"META-INF/resources/webjars/${(parent / normalizedName).value}/${(parent / version).value}",

        parent / Compile / webjarMainResource := (parent / Compile / webjarResourcePath).value + "/" + (parent / Compile / webjarMainResourceName).value,

        parent / Compile / webjarArtifacts / crossTarget := {
          (Compile / classDirectory).value.toPath.resolve((parent / Compile / webjarResourcePath).value).toFile
        },

        Compile / compile := (Compile / compile).dependsOn(parent / Compile / webjarArtifacts).value,

        Compile / packageBin / mappings := {
          val artifacts = (parent / Compile / webjarArtifacts).value
          (Compile / packageBin / mappings).value.filter(mapping =>
            mapping._2.endsWith(".class") || artifacts.contains(mapping._1)
          )
        },

        webjarAssetReferenceType := None,

        Compile / sourceGenerators += Def.taskDyn {
          webjarAssetReferenceType.value.fold {
            Def.task(Seq.empty[File])
          } { referenceType =>
            Def.task {
              val file = (Compile / sourceManaged).value / "WebjarReference.scala"
              val webjarLibrary = (parent / normalizedName).value
              val webjarReference = makeWebjarReference(
                referenceType,
                library = webjarLibrary,
                version = (parent / version).value,
                asset = (parent / Compile / webjarMainResourceName).value
              )

              def quote(ident: String): String =
                if (ident.matches("[a-zA-Z_][a-zA-Z0-9_]*")) ident
                else s"`$ident`"

              val string =
                s"""package webjars
                   |object ${quote(webjarLibrary)} {
                   |  val webjarAsset = $webjarReference
                   |}""".stripMargin

              IO.write(file, string, StandardCharsets.UTF_8)

              Seq(file)
            }
          }
        }.taskValue,
      )
  }

  private def makeWebjarReference(
                                   referenceType: String,
                                   library: String,
                                   version: String,
                                   asset: String
                                 ): String = referenceType.toLowerCase match {
    case "tuple" => s"""("$library", "$version", "$asset")"""
    case "http4s" => s"""org.http4s.server.staticcontent.WebjarService.WebjarAsset("$library", "$version", "$asset")"""
    case _ => throw new IllegalArgumentException(s"Unsupported webjar asset reference type: $referenceType")
  }
}
