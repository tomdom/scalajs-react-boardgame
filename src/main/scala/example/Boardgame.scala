package example

import example.GameCSS.Style
import japgolly.scalajs.react._
import japgolly.scalajs.react.vdom.html_<^._
import org.scalajs.jquery.jQuery

import scala.scalajs.js.annotation.JSExportTopLevel
import scalacss.ScalaCssReact._

object Boardgame {
  val Square = ScalaComponent.builder[(Int, String, Int => Callback)]("Square")
    .render_P {
      case (index, value, clickHandler) =>
        <.button(
          Style.square,
          ^.onClick --> clickHandler(index),
          value
        )
    }
    .build

  val Board = ScalaComponent.builder[(Array[Option[String]], Int => Callback)]("Board")
    .render_P { p =>
      def renderSquare(squares: Array[Option[String]], i: Int, handleClick: Int => Callback) =
        Square((i, squares(i).getOrElse(""), handleClick))

      p match {
        case (squares, handleClick) =>
          <.div(
            <.div(Style.boardRow,
              renderSquare(squares, 0, handleClick),
              renderSquare(squares, 1, handleClick),
              renderSquare(squares, 2, handleClick)
            ),
            <.div(Style.boardRow,
              renderSquare(squares, 3, handleClick),
              renderSquare(squares, 4, handleClick),
              renderSquare(squares, 5, handleClick)
            ),
            <.div(Style.boardRow,
              renderSquare(squares, 6, handleClick),
              renderSquare(squares, 7, handleClick),
              renderSquare(squares, 8, handleClick)
            )
          )
      }
    }
    .build

  case class Row(squares: Array[Option[String]])
  case class GameState(stepNumber: Int, history: Array[Row], xIsNext: Boolean)
  class GameBackend(bs: BackendScope[Unit, GameState]) {
    def handleClick(i: Int) = bs.modState { s =>
      val current = s.history.last.squares
      val historyStep = s.stepNumber < s.history.length - 1
      if (historyStep || calculateWinner(current).isDefined || current(i).isDefined)
        s
      else
        s.copy(s.stepNumber + 1, s.history :+ Row(current.updated(i, if (s.xIsNext) Some("X") else Some("O"))), !s.xIsNext)
    }

    def jumpTo(step: Int) = bs.modState { s =>
      s.copy(stepNumber = step, xIsNext = if (step % 2 == 0) true else false)
    }

    def render(s: GameState) = {
      val current = s.history(s.stepNumber)
      val status = calculateWinner(current.squares)
        .map(w => s"Winner: $w")
        .getOrElse(s"Next player: ${if (s.xIsNext) "X" else "O"}")

      val moves =
        (for (move <- s.history.indices) yield
          <.li(^.key := move,
            <.a(
              ^.href := "#",
              ^.onClick --> jumpTo(move),
              if (move > 0) "Move #" + move else "Game start"
            )
          )
        ).toVdomArray

      <.div(
        Style.game,
        <.div(Board(current.squares, handleClick)),
        <.div(
          Style.gameInfo,
          <.div(status),
          <.ol(moves)
        )
      )
    }
  }

  val Game = ScalaComponent.builder[Unit]("Game")
    .initialState(GameState(0, Array(Row(Array.fill(9)(None))), true))
    .renderBackend[GameBackend]
    .build

  val lines = Array(
    (0, 1, 2),
    (3, 4, 5),
    (6, 7, 8),
    (0, 3, 6),
    (1, 4, 7),
    (2, 5, 8),
    (0, 4, 8),
    (2, 4, 6)
  )

  def calculateWinner(squares: Array[Option[String]]): Option[String] = {
    lines
      .find {case (a, b, c) => squares(a).isDefined && squares(a) == squares(b) && squares(a) == squares(c)}
      .flatMap {case (a, b, c) => squares(a)}
  }

  @JSExportTopLevel("main")
  def main(): Unit = {
    GameCSS.load
    Game().renderIntoDOM(jQuery("#board")(0))
  }
}
