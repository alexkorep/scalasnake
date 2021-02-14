import org.scalajs.dom
import org.scalajs.dom.document
import org.scalajs.dom.html.Canvas
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel


object Main extends App {
  val ROWS = 10
  val COLS = 10
  val grid = Array.ofDim[Integer](ROWS, COLS)
  var canvas: Canvas = null
  var ctx: dom.CanvasRenderingContext2D = null

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
    for {
      i <- 0 until ROWS
      j <- 0 until COLS
    } {
      grid(i)(j) = 0
    }
    // for {
    //   i <- 0 until ROWS
    // } appendRow(document.body)
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

  // Update game objects
  def update(modifier: Double) {
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
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // if (bgImage.isReady) {
    //   ctx.drawImage(bgImage.element, 0, 0, canvas.width, canvas.height)
    // }
    // if (heroImage.isReady) {
    //   ctx.drawImage(heroImage.element, hero.pos.x, hero.pos.y)
    // }
    // if (monsterImage.isReady) {
    //   ctx.drawImage(monsterImage.element, monster.pos.x, monster.pos.y)
    // }

    // // Score
    // ctx.fillStyle = "rgb(250, 250, 250)"
    // ctx.font = "24px Helvetica"
    // ctx.textAlign = "left"
    // ctx.textBaseline = "top"
    // ctx.fillText("Goblins caught: " + monstersCaught, 32, 32)
  }

  // println("Hello, World!")
  // appendPar(document.body, "Hello World")
  // initGrid()
  def newGame = () => {
    createCanvas()
  }

  var prev = js.Date.now()
  // The main game loop
  val gameLoop = () => {
    val now = js.Date.now()
    val delta = now - prev
    update(delta / 1000)
    render()
    prev = now
  }

  // Let's play this game!
  dom.window.setInterval(gameLoop, 1) // Execute as fast as possible
}

