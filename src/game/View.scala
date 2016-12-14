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
  private val windowHeight = 1200
  private val windowWidth = 800
  private val game = new Game(windowHeight, windowWidth)
  override def settings() = {
    size(game.height, game.width)
  }
  override def draw() = {
    background(250);
    drawScoreboard();
  }
  def drawScoreboard(){
  // All of the scode for code and title
    fill(250, 0, 250);
    textSize(65);
    text( "PHUKSILETKA!", width/2, 80);
    fill(250, 0, 250);
    textSize(20);
    
    
    // draw scoreboard
    stroke(179, 140, 198);
    fill(255, 0 ,255);
    rect(90, 70, 160, 80);
    fill(118, 22, 167);
    textSize(17);
    text( "Score: "/* + test.len*/, 70, 50);
    
    fill(118, 22, 167);
    textSize(17);
    text( "High Score: "/* + highScore*/, 70, 70);
  }
}

//  val audioIn = AudioSystem.getAudioInputStream(new File("music/juna_kulkee.wav").getAbsoluteFile())
//  val clip = AudioSystem.getClip
//  clip.open(audioIn)
//  clip.loop(Clip.LOOP_CONTINUOUSLY)
