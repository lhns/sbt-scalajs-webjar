lazy val root = project.in(file(".")).settings(
  scalaVersion := "2.13.10"
)
  .aggregate(backend, frontendWebjar)

lazy val frontend = project
  .enablePlugins(
    ScalaJSBundlerWebjarPlugin
  )
  .settings(
    name := "frontend",

    scalaVersion := "2.13.10",

    scalaJSUseMainModuleInitializer := true,

    Compile / npmDependencies ++= Seq(
      "react" -> "16.13.1",
      "react-dom" -> "16.13.1"
    ),

    Compile / fastOptJS / webpack := {
      val v = (Compile / fastOptJS / webpack).value
      println(v.seq.map(_.metadata.entries.toList).mkString("\n"))
      v
    }
  )

lazy val frontendWebjar = frontend.webjar

lazy val backend = project
  .settings(
    name := "backend",

    scalaVersion := "2.13.10",

    Compile / compile := {
      println((frontend / Compile / webjarMainResource).value)

      (Compile / compile).value
    }
  )
  .dependsOn(frontendWebjar)
