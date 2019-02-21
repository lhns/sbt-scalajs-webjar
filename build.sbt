inThisBuild(Seq(
  name := "sbt-scalajs-webjar",
  organization := "de.lolhens",
  version := "0.1.0",

  resolvers += Resolver.bintrayRepo("lolhens", "sbt-plugins"),
  bintrayRepository := "sbt-plugins",
  bintrayReleaseOnPublish := false
))

name := (ThisBuild / name).value

lazy val root = (project in file("."))
  .enablePlugins(SbtPlugin)
  .settings(
    addSbtPlugin("org.scala-js" % "sbt-scalajs" % "0.6.26")
  )
