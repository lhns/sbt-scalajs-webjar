inThisBuild(Seq(
  name := "sbt-scalajs-webjar",
  organization := "de.lolhens",
  version := "0.0.4",
))

name := (ThisBuild / name).value

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.24")
  )
