val V = new {
  val scalajs = "1.13.1"
  val scalajsBundler = "0.21.1"
}

lazy val commonSettings: Seq[Setting[_]] = Seq(
  organization := "de.lolhens",
  version := {
    val Tag = "refs/tags/(.*)".r
    sys.env.get("CI_VERSION").collect { case Tag(tag) => tag }
      .getOrElse("0.0.1-SNAPSHOT")
  },

  licenses += ("Apache-2.0", url("https://www.apache.org/licenses/LICENSE-2.0")),

  homepage := scmInfo.value.map(_.browseUrl),
  scmInfo := Some(
    ScmInfo(
      url("https://github.com/lhns/sbt-scalajs-webjar"),
      "scm:git@github.com:lhns/sbt-scalajs-webjar.git"
    )
  ),
  developers := List(
    Developer(id = "lhns", name = "Pierre Kisters", email = "pierrekisters@gmail.com", url = url("https://github.com/lhns/"))
  ),

  scriptedLaunchOpts ++= Seq(
    "-Xmx1024M",
    "-Dplugin.version=" + version.value
  ),

  scriptedBufferLog := false,

  Compile / doc / sources := Seq.empty,

  publishMavenStyle := true,

  publishTo := sonatypePublishToBundle.value,

  credentials ++= (for {
    username <- sys.env.get("SONATYPE_USERNAME")
    password <- sys.env.get("SONATYPE_PASSWORD")
  } yield Credentials(
    "Sonatype Nexus Repository Manager",
    "oss.sonatype.org",
    username,
    password
  )).toList
)

lazy val root = project.in(file("."))
  .settings(commonSettings)
  .settings(
    publish / skip := true
  )
  .aggregate(
    `sbt-scalajs-webjar`,
    `sbt-scalajs-bundler-webjar`
  )

lazy val `sbt-scalajs-webjar` = project
  .enablePlugins(SbtPlugin)
  .settings(commonSettings)
  .settings(
    name := "sbt-scalajs-webjar",

    addSbtPlugin("org.scala-js" % "sbt-scalajs" % V.scalajs)
  )

lazy val `sbt-scalajs-bundler-webjar` = project
  .enablePlugins(SbtPlugin)
  .dependsOn(`sbt-scalajs-webjar`)
  .settings(commonSettings)
  .settings(
    name := "sbt-scalajs-bundler-webjar",

    addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % V.scalajsBundler)
  )
