organization := "de.lolhens"
name := "sbt-scalajs-webjar"
version := "0.2.1-SNAPSHOT"

licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0"))

homepage := Some(url("https://github.com/LolHens/sbt-scalajs-webjar"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/LolHens/sbt-scalajs-webjar"),
    "scm:git@github.com:LolHens/sbt-scalajs-webjar.git"
  )
)
developers := List(
  Developer(id = "LolHens", name = "Pierre Kisters", email = "pierrekisters@gmail.com", url = url("https://github.com/LolHens/"))
)


enablePlugins(SbtPlugin)

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.0.1")

scriptedLaunchOpts ++= Seq(
  "-Xmx1024M",
  "-Dplugin.version=" + version.value
)

scriptedBufferLog := false


Compile / doc / sources := Seq.empty

version := {
  val tagPrefix = "refs/tags/"
  sys.env.get("CI_VERSION").filter(_.startsWith(tagPrefix)).map(_.drop(tagPrefix.length)).getOrElse(version.value)
}

publishMavenStyle := true

publishTo := sonatypePublishToBundle.value

credentials ++= (for {
  username <- sys.env.get("SONATYPE_USERNAME")
  password <- sys.env.get("SONATYPE_PASSWORD")
} yield Credentials(
  "Sonatype Nexus Repository Manager",
  "oss.sonatype.org",
  username,
  password
)).toList
