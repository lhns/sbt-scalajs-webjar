organization := "de.lolhens"
name := "sbt-scalajs-webjar"
version := "0.1.1-SNAPSHOT"

enablePlugins(SbtPlugin)

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.0.1")

scriptedLaunchOpts ++= Seq(
  "-Xmx1024M",
  "-Dplugin.version=" + version.value
)

scriptedBufferLog := false
