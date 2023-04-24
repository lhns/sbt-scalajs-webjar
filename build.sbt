ThisBuild / versionScheme := Some("early-semver")
ThisBuild / organization := "de.lhns"
name := (`sbt-scalajs-webjar` / name).value

val V = new {
  val scalajs = "1.13.1"
  val scalajsBundler = "0.21.1"
}

lazy val commonSettings: Seq[Setting[_]] = Seq(
  version := {
    val Tag = "refs/tags/v?([0-9]+(?:\\.[0-9]+)+(?:[+-].*)?)".r
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

  publishTo := sonatypePublishToBundle.value,

  sonatypeCredentialHost := {
    if (sonatypeProfileName.value == "de.lolhens")
      "oss.sonatype.org"
    else
      "s01.oss.sonatype.org"
  },

  credentials ++= (for {
    username <- sys.env.get("SONATYPE_USERNAME")
    password <- sys.env.get("SONATYPE_PASSWORD")
  } yield Credentials(
    "Sonatype Nexus Repository Manager",
    sonatypeCredentialHost.value,
    username,
    password
  )).toList,

  pomExtra := {
    if (sonatypeProfileName.value == "de.lolhens")
      <distributionManagement>
        <relocation>
          <groupId>de.lhns</groupId>
        </relocation>
      </distributionManagement>
    else
      pomExtra.value
  }
)

lazy val root = project.in(file("."))
  .settings(commonSettings)
  .settings(
    publishArtifact := false,
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
