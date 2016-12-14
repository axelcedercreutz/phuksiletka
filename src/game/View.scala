package game

import processing.core._
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
import java.io.File

object Window {
  def main(args: Array[String]) {
    PApplet.main(Array[String]("game.Window"))
  }
}

class Window extends PApplet {
  private val windowHeight = 1000
  private val windowWidth = 600
  private val highScore = 0
  private val game = new Game(windowHeight, windowWidth)
  override def settings() = {
    size(game.height, game.width)
  }
  override def setup(){
    frameRate(14); 
//    var test = new snake();
//    var food1 = new food();
    //rectMode(CENTER);
    //textAlign(CENTER,CENTER)
}
  override def draw() = {
    background(250);
    drawScoreboard();
  }
  def drawScoreboard(){
    
    // All of the scode for code and title
    fill(250, 0, 250);
    textSize(65);
    text( "PHUKSILETKA!", windowWidth/2, 80);
    
    // draw scoreboard
    stroke(179, 140, 198);
    fill(118, 22, 167);
    textSize(17);
    text( "Score: "/*+ test.len*/, 70, 50);
    fill(118, 22, 167);
    textSize(17);
    text( "High Score: "+ highScore, 70, 70);
  }
  override def keyPressed(){
  key match {
    case 'q'  => {
      println("test")
    }
    case KeyEvent.VK_LEFT => {
      println("testLEft")
    }
    case KeyEvent.VK_RIGHT => {
      println("testRight")
    }
    case KeyEvent.VK_UP => {
      println("testUp")
    }
    case KeyEvent.VK_DOWN => {
      println("down")
    }
    case _ => {}
  }
}
}

//  val audioIn = AudioSystem.getAudioInputStream(new File("music/juna_kulkee.wav").getAbsoluteFile())
//  val clip = AudioSystem.getClip
//  clip.open(audioIn)
//  clip.loop(Clip.LOOP_CONTINUOUSLY)
