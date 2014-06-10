import Dependencies._

val commonSettings = Seq(
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.11.0",
  organization := "uk.co.randomcoding",
  scalacOptions in Compile ++= Seq("-feature", "-deprecation", "-unchecked", "-language:implicitConversions"))

lazy val root = project.in(file("."))
  .settings(commonSettings: _*)
  .settings(
	name := "Minecraft Mods Manager",
	test := {},
    publish := {},
    publishLocal := {},
    aggregate in update := false)
  .aggregate(gui, backend)

lazy val gui = project.in(file("gui"))
  .settings(commonSettings: _*)
  .settings(
    name := "Minecraft Mods Manager Application",
    libraryDependencies ++= commonDependencies ++ guiDependencies,
    //unmanagedJars in Compile += Attributed.blank(file(scala.util.Properties.javaHome) / "/lib/jfxrt.jar"),
    fork := true)
  .dependsOn(backend)

lazy val backend = project.in(file("backend"))
  .settings(commonSettings: _*)
  .settings(
    name := "Minecraft Mods Manager Backend",
    libraryDependencies ++= commonDependencies ++ backendDependencies)

lazy val commonDependencies = testDependencies

lazy val guiDependencies = scalaFxDependencies

lazy val backendDependencies = Seq()
