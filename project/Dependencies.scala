import sbt._

/**
 * Declaration of dependencies, library versions etc.
 *
 * These will all be available in your build.sbt file
 */
object Dependencies {
  // Define version numbers of libraries that could (and should) change here
  val scalatestVersion = "2.2.0"
  val pegdownVersion = "1.4.2"

  // Common groups of dependencies
  lazy val testDependencies = Seq(scalatest, pegdown)

  lazy val scalaFxDependencies = Seq(scalaFx)

  // The actual dependency definitions
  lazy val scalatest = "org.scalatest" %% "scalatest" % scalatestVersion % "test"
  lazy val pegdown = "org.pegdown" % "pegdown" % pegdownVersion % "test"

  lazy val scalaFx = "org.scalafx" %% "scalafx" % "1.0.0-R8"
}
