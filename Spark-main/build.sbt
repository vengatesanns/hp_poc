name := "spark-basics"
organization := "com.hackprotech"
version := "1.0"
scalaVersion := "2.12.12"

//ThisBuild / useCoursier := false

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.1.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "3.1.1" % "provided"
)