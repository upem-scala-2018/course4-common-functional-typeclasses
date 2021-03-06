lazy val `common-functional-typeclasses` = (project in file("."))
  .settings(
    organization := "fr.upem",
    name := "common-functional-typeclasses",
    scalaVersion := "2.12.7",
    scalacOptions += "-Ypartial-unification",
    libraryDependencies ++= List(
      "org.typelevel"              %% "cats-core"                 % "1.4.0",
      "org.typelevel"              %% "cats-testkit"              % "1.5.0" % Test,
      "org.scalatest"              %% "scalatest"                 % "3.0.5" % Test
    ),
    addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.6")
  )
