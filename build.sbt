name := "samspickem"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

enablePlugins(JavaAppPackaging)

libraryDependencies ++= {
  val akkaVersion = "2.4.0"
  val sprayVersion = "1.3.1"
  Seq(
    "io.spray"            %%  "spray-can"     % sprayVersion,
    "io.spray"            %%  "spray-routing" % sprayVersion,
    "io.spray"            %%  "spray-json"    % "1.2.6",
    "io.spray"            %%  "spray-testkit" % sprayVersion  % "test",
    "com.typesafe.akka"   %%  "akka-actor"    % akkaVersion,
    "com.typesafe.akka"   %%  "akka-testkit"  % akkaVersion   % "test",
    "mysql"               % "mysql-connector-java" % "5.1.27",
    "com.mchange"         % "c3p0"            % "0.9.5-pre8",
    "org.scalikejdbc"     %% "scalikejdbc"    % "2.0.0" withSources() withJavadoc(),
    "com.zaxxer"          % "HikariCP"        % "2.4.1",
    "org.scalactic"       %% "scalactic"      % "2.2.6",
    "org.scalatest"       %% "scalatest"      % "2.2.6"       % "test"
  )
}
