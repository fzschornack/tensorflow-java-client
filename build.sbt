name := "tensorflow-java-client"

version := "1.0"

scalaVersion := "2.11.7"

unmanagedClasspath in Compile += file("lib/grpc-protobuf-1.1.0-SNAPSHOT.jar")
unmanagedClasspath in Compile += file("lib/grpc-protobuf-lite-1.1.0-SNAPSHOT.jar")
unmanagedClasspath in Compile += file("lib/grpc-core-1.1.0-SNAPSHOT.jar")
unmanagedClasspath in Compile += file("lib/grpc-stub-1.1.0-SNAPSHOT.jar")
unmanagedClasspath in Compile += file("lib/grpc-netty-1.1.0-SNAPSHOT.jar")
unmanagedClasspath in Compile += file("lib/grpc-context-1.1.0-SNAPSHOT.jar")

libraryDependencies ++= Seq(
  "com.google.protobuf" % "protobuf-java" % "3.1.0",
  "com.google.guava" % "guava" % "20.0",
  "io.netty" % "netty-all" % "4.1.6.Final",
  "com.google.census" % "census-api" % "0.2.0"
)
    