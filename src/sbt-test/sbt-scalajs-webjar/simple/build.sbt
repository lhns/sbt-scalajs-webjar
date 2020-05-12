lazy val root = project.in(file(".")).settings(
  scalaVersion := "2.13.1"
)
  .aggregate(frontendWebjar)

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

lazy val frontendWebjar = frontend.webjar
