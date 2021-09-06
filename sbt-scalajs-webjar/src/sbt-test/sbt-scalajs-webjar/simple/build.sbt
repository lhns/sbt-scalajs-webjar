lazy val root = project.in(file(".")).settings(
  scalaVersion := "2.13.1"
)
  .aggregate(backend, frontendWebjar)

lazy val frontend = project
  .enablePlugins(
    ScalaJSWebjarPlugin
  )
  .settings(
    name := "frontend",

    scalaVersion := "2.13.1",

    scalaJSUseMainModuleInitializer := true
  )

lazy val frontendWebjar = frontend.webjar

lazy val backend = project
  .settings(
    name := "backend",

    scalaVersion := "2.13.1",

    Compile / compile := {
      println((frontend / Compile / webjarMainResource).value)

      (Compile / compile).value
    }
  )
  .dependsOn(frontendWebjar)
