package game

import processing.core._
import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import javax.imageio.ImageIO
import java.net.URL
import javax.sound.sampled._
import scala.io.Source
import java.io.File

object Window {
  def main(args: Array[String]) {
    PApplet.main(Array[String]("game.Window"))
  }
}

class Window extends PApplet {
  private val windowHeight = 40
  private val windowWidth = 24
  private val blockSize = 25
  private val snakeX = new ArrayBuffer[Int]()
  private val snakeY = new ArrayBuffer[Int]()
  snakeX += 20
  snakeY += 12
  private var appleX = 12
  private var appleY = 10
  private val dirX = new ArrayBuffer[Int]()
  private val dirY = new ArrayBuffer[Int]()
  private val audioIn = AudioSystem.getAudioInputStream(new File("music/juna_kulkee.wav").getAbsoluteFile())
  private val clip = AudioSystem.getClip
  private var highScore = 0
  private val game = new Game(blockSize, blockSize)
  private var gameTrue = false
  private var helpTrue = false
  private var gameOver = false
  private var dir = 2
  private var muted = false
  private val random = Random                       // random number
  
  dirX += (0,0,1,-1)
  dirY += (1,-1,0,0)
  
  override def settings() = {
    size(windowHeight * blockSize, windowWidth * blockSize)
  }
  
  override def setup(){
    clip.open(audioIn)
    clip.loop(Clip.LOOP_CONTINUOUSLY)
  }
  override def draw() = {
    background(250)
    if(!gameTrue && !helpTrue) {
      firstScreen()
    }
    else if (gameTrue){
      gameScreen()
    }
    else if(helpTrue) {
      helpScreen()
    }
    

  }
  private def drawFirstScreen() {
    fill(250, 0, 250);
    textSize(65);
    text( "PHUKSILETKA!", (windowWidth * blockSize)/2, 80);
    fill(118, 22, 167);
    textSize(17);
    text("To play on the easy level, press 1", (windowWidth * blockSize)/2, 260)
    text("To play on the normal level, press 2", (windowWidth * blockSize)/2, 280)
    text("To play on the hard level, press 3", (windowWidth * blockSize)/2, 300)
    text( "Mikäli tarvitset ohjeita, paina H", (windowWidth * blockSize)/2, 400)
  }
  private def drawHelpScreen() {
    text("Yritä kerätä mahdollisimman monta viinaa,\nkerätyt viinat keräävät sinulle kavereita mukaan!", (windowWidth * blockSize)/2, 260)
    text( "Liiku käyttämällä nuolinäppäimiä!", (windowWidth * blockSize)/2, 320)
    text( "Aloita painamalla 1,2 tai 3!", (windowWidth * blockSize)/2, 350)
  }
  private def drawScoreboard() {
    // draw scoreboard
    stroke(179, 140, 198);
    fill(118, 22, 167);
    textSize(17);
    text( "Score: " + (snakeX.size - 1), 70, 50);
    fill(118, 22, 167);
    textSize(17);
    text( "High Score: "+ highScore, 70, 70);
  }
  private def drawBasicSnake() {
    for(i <- 0 until snakeX.size) {
      fill(0,255,0)
      rect(snakeX(i)*blockSize, snakeY(i)*blockSize, blockSize, blockSize)
    }
    if(!gameOver) {
      fill(255,0,0)
      rect(appleX*blockSize,appleY*blockSize, blockSize, blockSize)
      if(frameCount % 10 == 0) {
        snakeX.prepend(snakeX(0) + dirX(dir))
        snakeY.prepend(snakeY(0) + dirY(dir))
        for(i <- 1 until snakeX.size) {
          if(snakeX(0) == snakeX(i) && snakeY(0) == snakeY(i)) gameOver = true
        }
        if(snakeX(0) == appleX && snakeY(0) == appleY) {
          println("something")
          appleX = random.nextInt(windowWidth)
          println(appleX)
          appleY = random.nextInt(windowHeight/2)
          println(appleY)
        }
        else {
        snakeX.remove(snakeX.size - 1)
        snakeY.remove(snakeY.size - 1)
        }
      }
    }
    else {
      if((snakeX.size - 1) > highScore) {
        highScore = snakeX.size - 1
      }
      fill (0)
      textSize(30)
      text("Game over! \nPress SHIFT to start new game on the same level \nq to go back to the main menu",windowWidth*5,windowHeight*5)
    }
  }
  def firstScreen() {
    drawFirstScreen()
    
  }
  def gameScreen() {
    drawScoreboard()
    drawBasicSnake()
  }
  def helpScreen() {
    drawHelpScreen()
  }
  override def keyPressed(){
    keyCode match {
      //q
      case 81  => {
        gameOver = false
        snakeX.clear
        snakeY.clear
        gameTrue = false
        helpTrue = false
        println("back to home page")
      }
      case PConstants.LEFT => {
        if(dir == 0 || dir == 1) {
          dir = 3
        }
      }
      case PConstants.RIGHT => {
        if(dir == 0 || dir == 1) {
          dir = 2
        }
      }
      case PConstants.UP => {
        if(dir == 2 || dir == 3) {
          dir = 1
        }
      }
      case PConstants.DOWN => {
        if(dir == 2 || dir == 3) {
          dir = 0
        }
      }
      //m
      case 77 => {
        if(!muted) {
          clip.stop()
          muted = true
        }
        else {
          clip.loop(Clip.LOOP_CONTINUOUSLY)
          muted = false
        }
        
      }
      //b
      case 66 => {
        println("background music gone")
      }
      //h
      case 72 => {
        helpTrue = true
      }
      //shift
      case 16 => {
        gameOver = false
        snakeX.clear
        snakeY.clear
        snakeX += 20
        snakeY += 12
        gameTrue = true
      }
      //1
      case 49 => {
        if(!gameTrue) {
          frameRate(80)
          snakeX += 20
          snakeY += 12
          gameTrue = true
        }
      }
      //2
      case 50 => {
        if(!gameTrue) {
          frameRate(140)
          snakeX += 20
          snakeY += 12
          gameTrue = true
        }
      }
      //3
      case 51 => {
        if(!gameTrue) {
          frameRate(280)
          snakeX += 20
          snakeY += 12
          gameTrue = true
        }
      }
      //any other
      case _ => {}
    }
  }
}
