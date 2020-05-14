lazy val root = project.in(file(".")).settings(
  scalaVersion := "2.13.1"
)
  .aggregate(backend)

lazy val frontend = project
  .enablePlugins(
    ScalaJSPlugin,
    ScalaJSWebjarPlugin
  )
  .settings(
    name := "frontend",

    scalaVersion := "2.13.1",

    scalaJSUseMainModuleInitializer := true
  )

lazy val backend = project
  .settings(
    name := "backend",

    scalaVersion := "2.13.1"
  )
  .dependsOn(frontend.webjar)
