# sbt-scalajs-webjar
[![Test Workflow](https://github.com/LolHens/sbt-scalajs-webjar/workflows/test/badge.svg)](https://github.com/LolHens/sbt-scalajs-webjar/actions?query=workflow%3Atest)
[![Release Notes](https://img.shields.io/github/release/LolHens/sbt-scalajs-webjar.svg?maxAge=3600)](https://github.com/LolHens/sbt-scalajs-webjar/releases/latest)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/de.lolhens/sbt-scalajs-webjar/badge.svg)](https://search.maven.org/artifact/de.lolhens/sbt-scalajs-webjar)
[![Apache License 2.0](https://img.shields.io/github/license/LolHens/sbt-scalajs-webjar.svg?maxAge=3600)](https://www.apache.org/licenses/LICENSE-2.0)
[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)

Allows you to build [WebJars](https://www.webjars.org/) from [Scala.js](https://www.scala-js.org/) projects.

### plugins.sbt
```sbt
addSbtPlugin("de.lolhens" % "sbt-scalajs-webjar" % "0.3.5")
```

Example
-------
```scala
lazy val shared = crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Pure).in(file("shared"))

lazy val sharedJs = shared.js
lazy val sharedJvm = shared.jvm

lazy val server = project.in(file("server"))
  .dependsOn(sharedJvm)
  .dependsOn(clientWebjar)

lazy val client = project.in(file("client"))
  .enablePlugins(ScalaJSWebjarPlugin)
  .dependsOn(sharedJs)

lazy val clientWebjar = client.webjar
```

Licensing
---------
This project uses the Apache 2.0 License. See the file called LICENSE.
