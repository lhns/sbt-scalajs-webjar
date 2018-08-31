package de.lolhens.scalajs.webjar

import sbt.Keys._
import sbt.{AutoPlugin, Defaults}

object ScalaJSWebjarPlugin extends AutoPlugin {

  object autoImport {

  }

  override lazy val projectSettings = Seq(
    //packageBin := Defaults.packageTaskSettings(packageBin, Nil)
  )
}
