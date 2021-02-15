import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html.Canvas
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel


object Main extends App {
  val ROWS = 10
  val COLS = 10

  class Point(var x: Int, var y: Int) {
    def draw(ctx: dom.CanvasRenderingContext2D, color: String): Unit = {
      val SEGMENT_SIZE = .6
      ctx.beginPath()
      val rowWidth = canvas.width/COLS
      val rowHeight = canvas.height/ROWS
      ctx.arc(rowWidth*(x + 0.5), rowHeight*(y + .5), 
        rowWidth*SEGMENT_SIZE, 0, 2 * Math.PI)
      ctx.fillStyle = color
      ctx.fill()
    }
  }

  var canvas: Canvas = null
  var ctx: dom.CanvasRenderingContext2D = null
  var segments:Array[Point] = null
  var dir = 0 // 0-up, 1-left, 2-down, 3-right
  var aiming = false

  def createCanvas = () => {
    canvas = dom.document.createElement("canvas").asInstanceOf[Canvas]
    ctx = canvas.getContext("2d").asInstanceOf[dom.CanvasRenderingContext2D]
    canvas.width = (0.95 * dom.window.innerWidth).toInt
    canvas.height = (0.95 * dom.window.innerHeight).toInt
    dom.document.body.appendChild(canvas)
    dom.document.body.onmousedown = { (e: dom.MouseEvent) =>
      aiming = true
    }
    // dom.document.body.ontouchstart = { (e: dom.TouchEvent) =>
    //   aiming = true
    // }
    canvas.addEventListener("touchstart", { (e0: dom.TouchEvent) => 
      aiming = true
      e0.preventDefault()
      false
    }, false)
    dom.document.body.onmouseup = { (e: dom.MouseEvent) =>
      aiming = false
    }
    canvas.addEventListener("touchend", { (e0: dom.TouchEvent) => 
      aiming = false
      e0.preventDefault()
      false
    }, false)
  }

  val checkGameOver = (newPoint: Point) => {
    newPoint.x < 0 || newPoint.y < 0 || 
      newPoint.x > COLS - 1 || newPoint.y > ROWS - 1
  }

  val getAimingPoint = () => {
    val head = segments(0)
    var newPoint: Point = null
    dir match {
      case 0 => newPoint = new Point(head.x, head.y - 1)
      case 1 => newPoint = new Point(head.x - 1, head.y)
      case 2 => newPoint = new Point(head.x, head.y + 1)
      case 3 => newPoint = new Point(head.x + 1, head.y)
    }
    newPoint
  }

  // Update game objects
  def update() {
    if (aiming) {
      dir = dir + 1
      if (dir > 3) {
        dir = 0
      }
    } else {
      var newPoint: Point = getAimingPoint()
      if (checkGameOver(newPoint)) {
        newGame()
      } else {
        segments = newPoint +: segments
        segments = segments.dropRight(1)
      }
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
      segment.draw(ctx, "#937A7E")
    }

    if (aiming) {
      var newPoint: Point = getAimingPoint()
      newPoint.draw(ctx, "#AFC4BD")
    }
  }

  def newGame = () => {
    segments = new Array[Point](1)
    segments(0) = new Point(COLS/2, ROWS/2)
    dir = 0
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
