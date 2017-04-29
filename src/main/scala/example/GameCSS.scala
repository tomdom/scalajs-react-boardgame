package example

import scalacss.Defaults._
import scalacss.internal.mutable.{GlobalRegistry, StyleSheet}

object GameCSS {
  object Style extends StyleSheet.Inline {
    import dsl._

    style(
      unsafeRoot("body")(
        fontFamily := """"Century Gothic", Futura, sans-serif""",
        fontSize(14.px),
        margin(20.px)
      ),
      unsafeRoot("ol")(
        paddingLeft(30.px)
      ),
      unsafeRoot("ul")(
        paddingLeft(30.px)
      )
    )

    val boardRow = style(
      &.after(
        clear.both,
        content := """""""",
        display.table
      )
    )

    val status = style(
      marginBottom(10.px)
    )

    val square = style(
      backgroundColor(c"#fff"),
      border(1.px, solid, c"#999"),
      float.left,
      fontSize(24.px),
      fontWeight.bold,
      lineHeight(34.px),
      height(34.px),
      marginRight(-1.px),
      marginTop(-1.px),
      padding(0.px),
      textAlign.center,
      width(34.px),

      &.focus(
        outline.none,

      )
    )

    val kbdNavigation = style(
      unsafeChild(".square")(
        &.focus(
          backgroundColor(c"#ddd")
        )
      )
    )

    val game = style(
      display.flex,
      flexDirection.row
    )

    val gameInfo = style(
      marginLeft(20.px)
    )
  }

  def load = {
    GlobalRegistry.register(
      Style
    )
    GlobalRegistry.onRegistration(_.addToDocument())
  }
}
