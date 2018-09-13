# sbt-scalajs-webjar

Allows you to build a WebJar from a ScalaJS project.

###Example
```scala
lazy val shared = crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Pure).in(file("shared"))

lazy val sharedJs = shared.js
lazy val sharedJvm = shared.jvm

lazy val server = project.in(file("server"))
  .dependsOn(sharedJvm)
  .dependsOn(client.webjar)

lazy val client = project.in(file("client"))
  .enablePlugins(ScalaJSWebjarPlugin)
  .dependsOn(sharedJs)
```