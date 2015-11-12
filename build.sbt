name := "SlickTest"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "3.1.0",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.1.0",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test",
  "org.postgresql" % "postgresql" % "9.4-1205-jdbc42",
  "net.sourceforge.jtds" % "jtds" % "1.3.1"
)

