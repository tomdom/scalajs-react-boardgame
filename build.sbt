enablePlugins(ScalaJSPlugin, WorkbenchPlugin)

name := "scalajs-react-boardgame"

version := "0.1-SNAPSHOT"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.9.1",
  "com.github.japgolly.scalajs-react" %%% "core" % "1.0.0",
  "com.github.japgolly.scalacss" %%% "core" % "0.5.3",
  "com.github.japgolly.scalacss" %%% "ext-react" % "0.5.3",
  "be.doeraene" %%% "scalajs-jquery" % "0.9.1"
)

jsDependencies ++= Seq(
  "org.webjars.bower" % "react" % "15.5.4"
    /        "react-with-addons.js"
    minified "react-with-addons.min.js"
    commonJSName "React",

  "org.webjars.bower" % "react" % "15.5.4"
    /         "react-dom.js"
    minified  "react-dom.min.js"
    dependsOn "react-with-addons.js"
    commonJSName "ReactDOM",

  "org.webjars.bower" % "react" % "15.5.4"
    /         "react-dom-server.js"
    minified  "react-dom-server.min.js"
    dependsOn "react-dom.js"
    commonJSName "ReactDOMServer",

  "org.webjars" % "jquery" % "3.2.0" / "3.2.0/jquery.js"
)
