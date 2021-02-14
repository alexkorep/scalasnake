import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html.Canvas
import scala.scalajs.js.annotation.JSExportTopLevel


object Main extends App {
  val ROWS = 10
  val COLS = 10
  val grid = Array.ofDim[Boolean](ROWS, COLS)

  // def appendPar(targetNode: dom.Node, text: String): Unit = {
  //   val parNode = document.createElement("p")
  //   parNode.textContent = text
  //   targetNode.appendChild(parNode)
  // }


  @JSExportTopLevel("onBodyClicked")
  def addClickedMessage(): Unit = {
    // appendPar(document.body, "You clicked the button!")
    print("clicked")
  }

  def initGrid(): Unit = {
    // for {
    //   i <- 0 until ROWS
    //   j <- 0 until COLS
    // } println(s"($i)($j) = ${a(i)(j)}")
    // for {
    //   i <- 0 until ROWS
    // } appendRow(document.body)
  }

  def createCanvas(): Unit = {
    val canvas = dom.document.createElement("canvas").asInstanceOf[Canvas]
    val ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    canvas.width = (0.95 * dom.window.innerWidth).toInt
    canvas.height = (0.95 * dom.window.innerHeight).toInt
    dom.document.body.appendChild(canvas)

    ctx.beginPath()
    ctx.rect(20, 20, 150, 100)
    ctx.stroke()
  }

  // println("Hello, World!")
  // appendPar(document.body, "Hello World")
  // initGrid()
  createCanvas()
}

