lazy val root = project.in(file("."))
  .settings(
    scalaVersion := "2.13.11"
  )
  .aggregate(backend, frontendWebjar)

lazy val frontend = project
  .enablePlugins(
    ScalaJSBundlerWebjarPlugin
  )
  .settings(
    name := "frontend",

    scalaVersion := "2.13.11",

    scalaJSUseMainModuleInitializer := true,

    Compile / npmDependencies ++= Seq(
      "react" -> "18.2.0",
      "react-dom" -> "18.2.0"
    ),

    webpack / version := "5.80.0",

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

    scalaVersion := "2.13.11",

    Compile / compile := {
      println((frontend / Compile / webjarMainResource).value)

      (Compile / compile).value
    }
  )
  .dependsOn(frontendWebjar)
