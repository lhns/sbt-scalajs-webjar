# sbt-scalajs-webjar
[![Release Notes](https://img.shields.io/github/release/LolHens/sbt-scalajs-webjar.svg?maxAge=3600)](https://github.com/LolHens/sbt-scalajs-webjar/releases/latest)
[![Maven Central](https://img.shields.io/maven-central/v/de.lolhens/sbt-scalajs-webjar_2.12)](https://search.maven.org/artifact/de.lolhens/sbt-scalajs-webjar_2.12)
[![Apache License 2.0](https://img.shields.io/github/license/LolHens/sbt-scalajs-webjar.svg?maxAge=3600)](https://www.apache.org/licenses/LICENSE-2.0)

Allows you to build a WebJar from a ScalaJS project.

### plugins.sbt
```sbt
addSbtPlugin("de.lolhens" % "sbt-scalajs-webjar" % "0.1.1")
```

Example
-------
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

Licensing
---------
This project uses the Apache 2.0 License. See the file called LICENSE.
