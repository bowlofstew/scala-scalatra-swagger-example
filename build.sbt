import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys
import org.scalatra.sbt._

import scalariform.formatter.preferences._

val ScalatraVersion = "2.5.0"

ScalatraPlugin.scalatraSettings

organization := "com.svinci.professionals"

name := "api"

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.8"

resolvers += Classpaths.typesafeReleases

SbtScalariform.scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(AlignParameters, true)
  .setPreference(AlignArguments, true)

libraryDependencies ++= Seq(
  "org.scalatra"        %%  "scalatra"          % ScalatraVersion,
  "org.scalatra"        %%  "scalatra-json"     % ScalatraVersion,
  "org.scalatra"        %%  "scalatra-swagger"  % ScalatraVersion,
  "org.json4s"          %%  "json4s-native"     % "3.5.0",
  "org.json4s"          %%  "json4s-jackson"    % "3.5.0",
  "com.typesafe"        %   "config"            % "1.3.1",
  "ch.qos.logback"      %   "logback-classic"   % "1.1.5"             % "runtime",
  "org.eclipse.jetty"   %   "jetty-webapp"      % "9.2.15.v20160210"  % "container;compile",
  "javax.servlet"       %   "javax.servlet-api" % "3.1.0"             % "provided"
)

mainClass in Compile := Some("com.svinci.professionals.api.JettyLauncher")
mainClass in assembly := Some("com.svinci.professionals.api.JettyLauncher")

assemblyJarName in assembly := "professionals-api.jar"

enablePlugins(JettyPlugin)
