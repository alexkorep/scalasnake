import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html.Canvas
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel


object Main extends App {
  val ROWS = 10
  val COLS = 10

  class Point(var x: Int, var y: Int) {
    def draw(ctx: dom.CanvasRenderingContext2D): Unit = {
      val SEGMENT_SIZE = .6
      ctx.beginPath()
      val rowWidth = canvas.width/COLS
      val rowHeight = canvas.height/ROWS
      ctx.arc(rowWidth*(x + 0.5), rowHeight*(y + .5), 
        rowWidth*SEGMENT_SIZE, 0, 2 * Math.PI)
      ctx.fillStyle = "#2a9d8f";
      ctx.fill()
    }
  }

  var canvas: Canvas = null
  var ctx: dom.CanvasRenderingContext2D = null
  var segments:Array[Point] = null
  var dir = 0 // 0-up, 1-left, 2-down, 3-right

  @JSExportTopLevel("onBodyClicked")
  def addClickedMessage(): Unit = {
    print("clicked")
  }

  def createCanvas = () => {
    canvas = dom.document.createElement("canvas").asInstanceOf[Canvas]
    ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    canvas.width = (0.95 * dom.window.innerWidth).toInt
    canvas.height = (0.95 * dom.window.innerHeight).toInt
    dom.document.body.appendChild(canvas)

    ctx.beginPath()
    ctx.rect(20, 20, 150, 100)
    ctx.stroke()
  }

  val checkGameOver = (newPoint: Point) => {
    newPoint.x < 0 || newPoint.y < 0 || 
      newPoint.x > COLS - 1 || newPoint.y > ROWS - 1
  }

  // Update game objects
  def update() {
    val head = segments(0)
    var newPoint: Point = null
    dir match {
      case 0 => newPoint = new Point(head.x, head.y - 1)
      case 1 => newPoint = new Point(head.x - 1, head.y)
      case 2 => newPoint = new Point(head.x, head.y + 1)
      case 3 => newPoint = new Point(head.x + 1, head.y)
    }
    if (checkGameOver(newPoint)) {
      newGame()
    } else {
      segments = newPoint +: segments
      segments = segments.dropRight(1)
    }


    // val modif = (hero.speed * modifier).toInt
    // var Position(x, y) = hero.pos
    // if (keysDown.contains(KeyCode.Left))  x -= modif
    // if (keysDown.contains(KeyCode.Right)) x += modif
    // if (keysDown.contains(KeyCode.Up))    y -= modif
    // if (keysDown.contains(KeyCode.Down))  y += modif

    // val newPos = Position(x, y)
    // if (isValidPosition(newPos, canvas)) {
    //   hero = hero.copy(pos = newPos)
    // }

    // // Are they touching?
    // if (areTouching(hero.pos, monster.pos)) {
    //   monstersCaught += 1
    //   reset()
    // }
  }

  // Draw everything
  def render() {
    ctx.clearRect(0, 0, canvas.width, canvas.height)
    for (segment <- segments) {
      segment.draw(ctx)
    }
  }

  def newGame = () => {
    segments = new Array[Point](1)
    segments(0) = new Point(COLS/2, ROWS/2)
  }

  var prev = js.Date.now()
  // The main game loop
  val gameLoop = () => {
    val now = js.Date.now()
    val delta = now - prev
    if (delta >= 1000) {
      update()
      render()
      prev = now
    }
  }

  // Let's play this game!
  createCanvas()
  newGame()
  dom.window.setInterval(gameLoop, 1) // Execute as fast as possible
}
