package game

import java.awt._
import java.awt.event._
import java.util.Scanner
import javax.imageio.ImageIO
import javax.swing._
import scala.collection.mutable.ArrayBuffer
import scala.swing._
import scala.swing.event._
import java.awt.event.{ActionEvent,ActionListener,KeyListener,KeyEvent}
import java.net.URL
import javax.sound.sampled._
import scala.io.Source
import java.awt.Color
import java.awt.event.KeyEvent._

object View extends SimpleSwingApplication {
  val url = new URL("file:///Users/axel/workspace/phuksiletka/music/juna_kulkee.wav")
  val audioIn = AudioSystem.getAudioInputStream(url)
  val clip = AudioSystem.getClip
  clip.open(audioIn)
  clip.loop(1000)
  val width = 20
  val height = 10
  val cellSize = 50
  var world: Array[Array[Spot]] = Array.fill(width, height)(Floor)
  val r = scala.util.Random
  val player = new Player(1, 1)
  var dir = (1,0)
  
  val canvas = new GridPanel(rows0 = height, cols0 = width) {
    
  preferredSize = new Dimension(width * cellSize, height * cellSize)

  
  
    override def paintComponent(g: Graphics2D) {
      for (i <- 0 until width) {
        for (j <- 0 until height) { // Loop through the world grid
          world(i)(j) match {       // Match what is found in every position
            case Wall => {          // If a wall is there, change color to black and paint a black tile representing a wall
              g.setColor(Color.BLACK)
              g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize)
            }
            case Floor => {         // If a floor is there, change color to cyan and paint a cyan tile representing floor
              g.setColor(Color.WHITE)
              g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize)
            }
          }
        }
      }
      g.setColor(Color.ORANGE) // Set color for the player to be drawn
      g.fillOval(player.x * 50, player.y * 50, 50, 50) // Draw player to its location
      g.setColor(Color.GRAY)
    }
  }
 
  def top = new MainFrame {
    title = "Phuksiletka"
    preferredSize = new Dimension(width * cellSize, height * cellSize)
    contents = canvas
    val timer = new Timer(1000,TimerListener)
    timer.start()
    
    for (i <- 0 until 20; j <- 0 until 10) {
      world(i)(0) = Wall
      world(19)(j) = Wall
      world(i)(9) = Wall
      world(0)(j) = Wall
    }
    
    listenTo(canvas.mouse.clicks)
    
    reactions += {
      case KeyPressed(canvas, point , _, _) => {
        //if (KeyPressed == KeyEvent.VK_LEFT) {
            player.move(player.x + 1 / cellSize, player.y / cellSize)
            println("touched left")
            repaint()
//          }
//        else if(KeyPressed == KeyEvent.VK_RIGHT) {
//            player.move(player.x - 1 / cellSize, player.y / cellSize)
//            println("touched right")
//            repaint()
//          }
//        else if(KeyPressed == KeyEvent.VK_UP) {
//            player.move(player.x / cellSize, player.y - 1 / cellSize)
//            println("touched up")
//            repaint()
//          }
//        else if(KeyPressed == KeyEvent.VK_DOWN) {
//          player.move(player.x / cellSize, player.y + 1 / cellSize)
//            println("touched down")
//            repaint()
//          }
//        else repaint()
      }
      case MouseClicked(canvas, point, _, _, _) => {
        if (point.x <= width * cellSize && point.y <= height * cellSize) {
          if (player.canMoveTo(point.x / cellSize, point.y / cellSize) &&
            world(point.x / cellSize)(point.y / cellSize) != Wall) player.move(point.x / cellSize, point.y / cellSize)
            repaint()
        }
      }
    }
    object TimerListener extends ActionListener {

  		override def actionPerformed(e : ActionEvent) {
  			repaint()
  		}

	  }
    
    object KeyboardListener extends KeyListener {

      override def keyPressed(e: KeyEvent) {
        //change direction
        dir = e.getKeyCode() match {
          case KeyEvent.VK_LEFT => {
            player.move(player.x + 1 / cellSize, player.y / cellSize)
            println("touched left")
            (1,1)
          }
          case KeyEvent.VK_RIGHT => {
            player.move(player.x - 1 / cellSize, player.y / cellSize)
            println("touched right")
            (2,2)
          }
          case KeyEvent.VK_UP => {
            player.move(player.x / cellSize, player.y - 1 / cellSize)
            println("touched up")
            (2,2)
          }
          case KeyEvent.VK_DOWN => {
            player.move(player.x / cellSize, player.y + 1 / cellSize)
            println("touched down")
            (3,3)
          }
          case _ => dir
//          case KeyEvent.VK_SPACE => {
//    						if(timer.isRunning())
//    							timer.stop()
//    						else
//    							timer.start()
//      					}
        }
    }
    override def keyReleased(e: KeyEvent) {}
    override def keyTyped(e: KeyEvent){}
  }
  }
}