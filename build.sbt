name := "minefields-scala-meter"

version := "0.1"

scalaVersion := "2.12.4"


// scala meter repository
resolvers += "Sonatype OSS Snapshots" at
  "https://oss.sonatype.org/content/repositories/snapshots"

// scala meter dependency
libraryDependencies += "com.storm-enroute" %% "scalameter" % "[version]"

// setting up scala meter as additional test framework
testFrameworks += new TestFramework("org.scalameter.ScalaMeterFramework")

logBuffered := false

// parallel execution should be turned off for microbenchmarking
parallelExecution in Test := false