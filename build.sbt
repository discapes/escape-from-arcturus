val scala3Version = "3.10.0"

lazy val root = project
  .in(file("."))
  .settings(
    name := "o1-tekstipeli",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    Compile / mainClass := Some("o1.adventure.ui.AdventureTextUI")
  )
