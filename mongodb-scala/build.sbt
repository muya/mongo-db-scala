import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.5",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Hello",
    libraryDependencies ++= Seq(
      scalaTest % Test,
      "org.mongodb.scala" %% "mongo-scala-driver" % "2.2.1",
      "io.netty" % "netty-all" % "4.0.4.Final"
    )
  )
