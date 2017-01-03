package game
//processing for the window
import processing.core._
//import ArrayBuffers
import scala.collection.mutable.ArrayBuffer
//import sound
import javax.sound.sampled._
//import Files
import java.io.File
//import math
import scala.math._


//the main window object
object Window {
  def main(args: Array[String]) {
    PApplet.main(Array[String]("game.Window"))
  }
}

//creates the main window
class Window extends PApplet {
  //width for the game
  private val windowWidth = 40
  //hight for the game
  private val windowHeight = 24
  //one blocks size
  private val blockSize = 25
  //creating the game
  private val game = new Game(windowWidth, windowHeight)
  //variable for the game's music
  private val music = game.music
  //variable for gamescreen
  private var gameTrue = false
  //variable for helpscreen
  private var helpTrue = false
  
  //setting the size of the window
  override def settings() = {
    size(windowWidth * blockSize, windowHeight * blockSize)
  }
  
  //at setup, start the menu music
  override def setup() = {
    music.play("jokeri")
  }
  
  //what is drawn in the window
  override def draw() = {
    //our background color
    background(250)
    //main menu
    if(!gameTrue && !helpTrue) {
      firstScreen()
    }
    //helpscreen
    else if(helpTrue) {
      helpScreen()
    }
    //gamescreen
    else if (gameTrue){
      gameScreen()
    }
  }
  
  //what is drawn in the main menu
  private def drawFirstScreen() {
    //textcolor
    fill(250, 0, 250)
    //textsize
    textSize(65)
    //Title
    text( "PHUKSILETKA!", (windowHeight * blockSize)/2, 80)
    //new color
    fill(118, 22, 167)
    //new size
    textSize(17)
    //instructions
    text("To play on the easy level, press 1", (windowHeight * blockSize)/2, 260)
    text("To play on the normal level, press 2", (windowHeight * blockSize)/2, 280)
    text("To play on the hard level, press 3", (windowHeight * blockSize)/2, 300)
    text( "Mikäli tarvitset ohjeita, paina H", (windowHeight * blockSize)/2, 400)
  }
  //add walls if playing with game modes 2 or 3
  private def drawWalls() = {
    val kuva = loadImage("photos/wall2.png")
    if(game.gameLevelForWalls >1){
   for(coordy <- 0 until game.height){
         image(kuva,0,coordy*blockSize,blockSize,blockSize)
         image(kuva,(game.width-1)*blockSize,coordy*blockSize,blockSize,blockSize)
   }
   for(coordx <- 0 until game.width){
     image(kuva,coordx*blockSize,0,blockSize,blockSize)
     image(kuva,coordx*blockSize,(game.height-1)*blockSize,blockSize,blockSize)
   }
    }
  }
  //what is drawn in the helpscreen
  private def drawHelpScreen() {
    //color
    fill(118, 22, 167)
    //size
    textSize(17)
    text("Yritä kerätä mahdollisimman monta viinaa,\nkerätyt viinat keräävät sinulle kavereita mukaan!", (windowHeight * blockSize)/2, 260)
    text( "Liiku käyttämällä nuolinäppäimiä!", (windowHeight * blockSize)/2, 320)
    text( "Aloita painamalla 1,2 tai 3!", (windowHeight * blockSize)/2, 350)
  }
  //scoreboard for the gamescreen
  private def drawScoreboard() {
    fill(118, 22, 167)
    textSize(17)
    text( "Score: " + game.count, 70, 50)
    //shows the highscore
    text( "High Score: "+ max(game.count, game.correctHighScore()), 70, 70)
  }
  //draws the characters
  private def drawBasicSnake() {
    //image for the "snake"
    val phuksi = loadImage("photos/nerd.png")
    //image for the "apples"
    val bollinger = loadImage("photos/bollinger.png")
    //image for the coffees
    val coffee = loadImage("photos/coffee.png")
    
    //chooses the gamespeed
    if(game.gameLevel == 4) {
      frameRate(60)
    }
    else if(game.gameLevel == 3) {
      frameRate(45)
    }
    else if(game.gameLevel == 2) {
      frameRate(30)
    }
    else {
      frameRate(15)
    }
    //creates the "snake"
    for(i <- 0 until game.snakeX.size) {
      image(phuksi, game.snakeX(i) * blockSize, game.snakeY(i) * blockSize, blockSize, blockSize)
    }
    //what happens when the game is on
    if(!game.gameOver) {
      //adds the "apples"
      image(bollinger,game.appleX * blockSize,game.appleY * blockSize, blockSize, blockSize)
      //adds the movement of the snake
      if(frameCount % 2 == 0) {
        game.moveSnake()
      }
      //how it chooses when the coffees are seen an when not
      if(frameCount % 300 > 100 && frameCount % 300 < 200 && game.count >= 5) {
        fill(0,255,255)
        image(coffee, game.powerUpX * blockSize,game.powerUpY * blockSize, blockSize, blockSize)
        game.powerUps()
      }        
    }
    //what happens when game is over
    else {
      fill (0)
      textSize(30)
      text("Game over! \nPress Shift to start new game on the same level \nq to go back to the main menu",windowWidth*5,windowHeight*5)
    }
  }
  
  //this is drawn in draw - main menu
  def firstScreen() {
    drawFirstScreen()
    
  }
  //this is drawn in draw - gamescreen
  def gameScreen() {
    drawScoreboard()
    drawWalls()
    drawBasicSnake()
  }
  //this is drawn in draw - helpscreen
  def helpScreen() {
    drawHelpScreen()
  }
  
  //detecting key presses
  override def keyPressed(){
    keyCode match {
      //q
      case 81  => {
        //checks if game music is on
        if(music.clip.isActive()) {
          music.stop("juna")
        }
        //starts menu music
        music.play("jokeri")
        game.gameOver = false
        //clears the floor
        game.clear()
        //goes to the main menu
        gameTrue = false
        helpTrue = false
      }
      //when left key is pressed
      case PConstants.LEFT => {
        //if the direction is up or down
        if(game.dir == 0 || game.dir == 1) {
          game.dir = 3
        }
      }
      //when right key is pressed
      case PConstants.RIGHT => {
        //if the direction is up or down
        if(game.dir == 0 || game.dir == 1) {
          game.dir = 2
        }
      }
      //when up key is pressed
      case PConstants.UP => {
        //if the direction is left or right
        if(game.dir == 2 || game.dir == 3) {
          game.dir = 1
        }
      }
      //when down key is pressed
      case PConstants.DOWN => {
        //if the direction is left or right
        if(game.dir == 2 || game.dir == 3) {
          game.dir = 0
        }
      }
      //m 
      case 77 => {
        //checks what to mute
        if(!gameTrue) {
          music.stop("jokeri") 
        }
        else {
            music.stop("juna")
            music.backgroundMute = !music.backgroundMute
        }
      }
      //b
      case 66 => {
        //only stops the gamesounds
        music.backgroundMute = !music.backgroundMute
      }
      //h
      case 72 => {
        //shows & hides the helpscreen
        helpTrue = !helpTrue
      }
      //SHIFT
      case 16 => {
        //restarts a new game on the same level
        game.gameOver = false
        game.clear()
        game.start(game.originalLevel)
        gameTrue = true
      }
      //1
      case 49 => {
        //starts a new game on level easy if there's no game happening
        if(!gameTrue) {
          if(music.clip2.isActive()) {
            music.stop("jokeri")
          }
          music.play("juna")
          game.start(1)
          gameTrue = true
        }
      }
      //2
      case 50 => {
        //starts a new game on level normal if there's no game happening
        if(!gameTrue) {
          if(music.clip2.isActive()) {
            music.stop("jokeri")
          }
          music.play("juna")
          game.start(2)
          gameTrue = true
        }
      }
      //3
      case 51 => {
        //starts a new game on level hard if there's no game happening
        if(!gameTrue) {
          if(music.clip2.isActive()) {
            music.stop("jokeri")
          }
          music.play("juna")
          game.start(3)
          gameTrue = true
        }
      }
      //any other - does nothing
      case _ => {}
    }
  }
}
