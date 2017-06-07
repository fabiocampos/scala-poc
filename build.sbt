name := """ada"""
organization := "br.com.ada"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies += filters
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "2.0.0" % Test
libraryDependencies ++= Seq(
  "org.reactivemongo" %% "play2-reactivemongo" % "0.12.3",
  "ch.qos.logback"  %  "logback-classic"   % "1.2.1"
)

//addSbtPlugin("org.scalikejdbc"           %% "scalikejdbc-mapper-generator" % "2.2.0")

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "br.com.ada.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "br.com.ada.binders._"
