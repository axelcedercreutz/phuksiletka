package game

import processing.core._
import scala.collection.mutable.ArrayBuffer
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
  private val snakeArrayX = new ArrayBuffer[Int]()
  private val snakeArrayY = new ArrayBuffer[Int]()
  private val highScore = 0
  private val game = new Game(blockSize, blockSize)
  private val test = new Snake(blockSize);
  private var gameTrue = false
  private var helpTrue = false
  
  override def settings() = {
    size(windowHeight * blockSize, windowWidth * blockSize)
  }
  
  override def setup(){
    frameRate(10); 
    val audioIn = AudioSystem.getAudioInputStream(new File("music/juna_kulkee.wav").getAbsoluteFile())
    val clip = AudioSystem.getClip
    clip.open(audioIn)
    clip.loop(Clip.LOOP_CONTINUOUSLY)
  }
  override def draw() = {
    background(250)
    if(!gameTrue && !helpTrue) {
      firstScreen()
    }
    else if(helpTrue) {
      helpScreen()
    }
    else {
      gameScreen()
    }

  }
  private def drawFirstScreen() {
    fill(250, 0, 250);
    textSize(65);
    text( "PHUKSILETKA!", (windowWidth * blockSize)/2, 80);
    fill(118, 22, 167);
    textSize(17);
    text( "Aloita painamalla SHIFT!", (windowWidth * blockSize)/2, 300);
    text( "Mikäli tarvitset ohjeita, paina H", (windowWidth * blockSize)/2, 350);
    //background(new File(
  }
  private def drawHelpScreen() {
    text("Yritä kerätä mahdollisimman monta viinaa, kerätyt viinat keräävät sinulle kavereita mukaan!", (windowWidth * blockSize)/2, 260)
    text( "Liiku käyttämällä nuolinäppäimiä", (windowWidth * blockSize)/2, 300)
    text( "Aloita painamalla SHIFT!", (windowWidth * blockSize)/2, 340)
  }
  private def drawScoreboard() {
    // draw scoreboard
    stroke(179, 140, 198);
    fill(118, 22, 167);
    textSize(17);
    text( "Score: " + game.score, 70, 50);
    fill(118, 22, 167);
    textSize(17);
    text( "High Score: "+ highScore, 70, 70);
  }
  private def drawBasicSnake() {
    snakeArrayX += 5
    snakeArrayY += 5
    for(i <- 0 until snakeArrayX.size) {
      fill(0,255,0)
      rect(snakeArrayX(i)*blockSize, snakeArrayY(i)*blockSize, blockSize, blockSize)
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
        gameTrue = false
        helpTrue = false
        println("back to home page")
      }
      case PConstants.LEFT => {
        println("testLEft")
      }
      case PConstants.RIGHT => {
        println("testRight")
      }
      case PConstants.UP => {
        println("testUp")
      }
      case PConstants.DOWN => {
        println("down")
      }
      //m
      case 77 => {
        println("mute")
      }
      //b
      case 66 => {
        println("background music gone")
      }
      case 72 => {
        helpTrue = true
        println("help")
      }
      case 16 => {
        gameTrue = true
        println("shift")
      }
      case _ => {}
    }
  }
}
