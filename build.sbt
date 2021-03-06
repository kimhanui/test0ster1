import ProjectInfo._

ThisBuild / organization := "io.kevinlee"
ThisBuild / version := ProjectVersion
ThisBuild / scalaVersion := "2.13.3"

ThisBuild / developers   := projectDevelopers
ThisBuild / homepage     := projectHomePage
ThisBuild / scmInfo      := projectScmInfo

val projectBuildSourceEncoding = "UTF-8"
val junitJupiterVersion = "5.6.2"

lazy val test0ster1 = (project in file("."))
    .enablePlugins(DevOopsJavaPlugin)
    .enablePlugins(DevOopsGitReleasePlugin)
    .enablePlugins(JacocoCoverallsPlugin)
    .settings(
      name := "test0ster1"
    , javacOptions := Seq(
        "-source", javaVersion.value
      , "-encoding", "UTF-8"
      )
    , javacOptions in (Compile, compile) ++= Seq(
        "-target", javaVersion.value
      , "-Xlint:unchecked"
      , "-g"
      , "-deprecation"
      )
    , javacOptions in (Compile, test) := (javacOptions in (Compile, compile)).value
    , resolvers ++= Seq(
        Resolver.jcenterRepo
      )
    , libraryDependencies ++= Seq(
        "org.junit.jupiter" % "junit-jupiter" % junitJupiterVersion % Test
      , "net.aichler" % "jupiter-interface" % JupiterKeys.jupiterVersion.value % Test
      , "org.assertj" % "assertj-core" % "3.17.2" % Test
      , "org.mockito" % "mockito-core" % "3.5.10" % Test
      )
    , testOptions += Tests.Argument(TestFrameworks.JUnit, "-a")

    /* Jacoco { */
    , jacocoReportSettings := JacocoReportSettings(
      "Jacoco Coverage Report"
      , None
      , JacocoThresholds()
      , Seq(JacocoReportFormats.ScalaHTML, JacocoReportFormats.XML)
      , "utf-8"
    )
    , jacocoCoverallsServiceName := "github-actions"
    , jacocoCoverallsBranch := sys.env.get("CI_BRANCH")
    , jacocoCoverallsPullRequest := sys.env.get("GITHUB_EVENT_NAME")
    , jacocoCoverallsRepoToken := sys.env.get("COVERALLS_REPO_TOKEN")
      /* } Jacoco */

    , bintrayPackageLabels := Seq("maven", "java", "test", "java8")
    , bintrayVcsUrl := Some("git@github.com:Kevin-Lee/test0ster1.git")
    , bintrayRepository := "maven"

    , publishMavenStyle := true
    , publishArtifact in Test := false
    , pomIncludeRepository := { _ => false }
    , licenses += ("Apache-2.0", url("http://opensource.org/licenses/apache2.0"))
    /* GitHub Release { */
    , devOopsPackagedArtifacts := List(s"target/${name.value}*.jar")
    /* } GitHub Release */
    )



