package game

import processing.core._
import scala.collection.mutable.ArrayBuffer
import javax.sound.sampled._
import java.io.File
import javax.imageio.ImageIO
import scala.math._

object Window {
  def main(args: Array[String]) {
    PApplet.main(Array[String]("game.Window"))
  }
}

class Window extends PApplet {
  private val windowWidth = 40
  private val windowHeight = 24
  private val blockSize = 25
  private val game = new Game(windowWidth, windowHeight)
  private val music = new Music()
  private val powerUp = new PowerUp(windowWidth,windowHeight)
  private val fruit = new Fruit(windowWidth, windowHeight)
  private val junaKulkee = AudioSystem.getAudioInputStream(new File("music/juna_kulkee.wav").getAbsoluteFile())
  private val jokeriTuplaus = AudioSystem.getAudioInputStream(new File("music/jokeri_pokeri_tuplaus_musiikki.wav").getAbsoluteFile())
  private var gameTrue = false
  private var helpTrue = false
  
  
  override def settings() = {
    size(windowWidth * blockSize, windowHeight * blockSize)
  }
  
  override def setup() = {
    //music.play(jokeriTuplaus)
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
    text( "PHUKSILETKA!", (windowHeight * blockSize)/2, 80);
    fill(118, 22, 167);
    textSize(17);
    text("To play on the easy level, press 1", (windowHeight * blockSize)/2, 260)
    text("To play on the normal level, press 2", (windowHeight * blockSize)/2, 280)
    text("To play on the hard level, press 3", (windowHeight * blockSize)/2, 300)
    text( "Mikäli tarvitset ohjeita, paina H", (windowHeight * blockSize)/2, 400)
  }
  private def drawHelpScreen() {
    text("Yritä kerätä mahdollisimman monta viinaa,\nkerätyt viinat keräävät sinulle kavereita mukaan!", (windowHeight * blockSize)/2, 260)
    text( "Liiku käyttämällä nuolinäppäimiä!", (windowHeight * blockSize)/2, 320)
    text( "Aloita painamalla 1,2 tai 3!", (windowHeight * blockSize)/2, 350)
  }
  private def drawScoreboard() {
    // draw scoreboard
    stroke(179, 140, 198);
    fill(118, 22, 167);
    textSize(17);
    text( "Score: " + (game.snakeX.size - 1), 70, 50);
    fill(118, 22, 167);
    textSize(17);
    text( "High Score: "+ game.correctHighScore(game.gameLevel), 70, 70);
  }
  private def drawBasicSnake() {
    if(game.gameLevel == 4) {
      frameRate(60)
    }
    else if(game.gameLevel == 3) {
      frameRate(50)
    }
    else if(game.gameLevel == 2) {
      frameRate(30)
    }
    else {
      frameRate(20)
    }
    for(i <- 0 until game.snakeX.size) {
      fill(0,255,0)
      rect(game.snakeX(i) * blockSize,game.snakeY(i) * blockSize, blockSize, blockSize)
    }
    if(!game.gameOver) {
      fill(255,0,0)
      rect(game.appleX * blockSize,game.appleY * blockSize, blockSize, blockSize)
      if(frameCount % 2 == 0) {
        game.moveSnake()
      }
      if(frameCount % 100 > 50 && frameCount % 100 < 80) {
        fill(0,255,255)
        rect(game.powerUpX * blockSize,game.powerUpY * blockSize, blockSize, blockSize)
        game.powerUps()
        println(frameRate)
        println(game.gameLevel)
      }        
    }
    else {
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
        music.stop()
        game.gameOver = false
        game.clear()
        gameTrue = false
        helpTrue = false
      }
      case PConstants.LEFT => {
        if(game.dir == 0 || game.dir == 1) {
          game.dir = 3
        }
      }
      case PConstants.RIGHT => {
        if(game.dir == 0 || game.dir == 1) {
          game.dir = 2
        }
      }
      case PConstants.UP => {
        if(game.dir == 2 || game.dir == 3) {
          game.dir = 1
        }
      }
      case PConstants.DOWN => {
        if(game.dir == 2 || game.dir == 3) {
          game.dir = 0
        }
      }
      //m 
      case 77 => {
        music.stop() 
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
        game.gameOver = false
        game.clear()
        game.start()
        gameTrue = true
      }
      //1
      case 49 => {
        if(!gameTrue) {
          music.play(junaKulkee)
          game.gameLevel = 1
          game.start()
          gameTrue = true
        }
      }
      //2
      case 50 => {
        if(!gameTrue) {
          music.play(junaKulkee)
          game.gameLevel = 2
          game.start()
          gameTrue = true
        }
      }
      //3
      case 51 => {
        if(!gameTrue) {
          music.play(junaKulkee)
          game.gameLevel = 3
          game.start()
          gameTrue = true
        }
      }
      //any other
      case _ => {}
    }
  }
}
