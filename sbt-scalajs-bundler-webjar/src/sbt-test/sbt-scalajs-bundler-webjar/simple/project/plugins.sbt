sys.props.get("plugin.version") match {
  case Some(version) => addSbtPlugin("de.lolhens" % "sbt-scalajs-bundler-webjar" % version)
  case _ => sys.error(
    """|The system property 'plugin.version' is not defined.
       |Specify this property using the scriptedLaunchOpts -D.""".stripMargin)
}

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.13.0")

addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.21.1")
