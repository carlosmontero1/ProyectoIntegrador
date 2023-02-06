ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "ProyectoIntegrador",
      libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.10", // lectura CSV
      libraryDependencies += "io.github.cibotech" %% "evilplot" % "0.8.1", // diagramas
      libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.4", // Json
      libraryDependencies += "org.scalikejdbc" %% "scalikejdbc" % "4.0.0", //
      libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3", //
      libraryDependencies += "com.mysql" % "mysql-connector-j" % "8.0.32", // conectarse a BD
      libraryDependencies += "com.lihaoyi" %% "requests" % "0.8.0" // Meaningcloud
  )
