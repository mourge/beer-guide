import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "beer"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    javaEbean,
    "com.googlecode.json-simple" % "json-simple" % "1.1.1",
    "redis.clients" % "jedis" % "2.1.0"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )
}
