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
  var screen = 1
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
    else if (gameTrue){
      gameScreen()
    }
  }
  //draw highscores to highscore page
  private def drawScores() {
    fill(0, 0, 0)
    textSize(50)
    text( game.highScoreHard, 483, 275)
    text( game.highScoreNormal, 483, 360)
    text( game.highScoreEasy, 483, 448)
  }
  // draw scores to gameover page
  private def gameOverScores() = {
    fill(0, 0, 0)
    textSize(50)
    text( game.count, 449, 288)
    text( game.correctHighScore, 449, 410)
  }
  
  //what is drawn in the main menu
  private def drawFirstScreen() {
    updateGameView()    
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
  
   private def updateGameView() = {
     if(screen != 6 && screen != 62 && screen != 4 && screen != 42){
    image(loadImage("photos/screen"+screen+".png"),0,0,windowWidth*blockSize,windowHeight*blockSize)
     }
     else if(screen == 6 || screen == 62){
     image(loadImage("photos/screen"+screen+".png"),0,0,windowWidth*blockSize,windowHeight*blockSize)
     this.drawScores
     }
     else{
     image(loadImage("photos/screen"+screen+".png"),0,0,windowWidth*blockSize,windowHeight*blockSize)
     this.gameOverScores
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
      if(music.mute){
        screen = 42
        updateGameView()
      }
      else{
        screen = 4
        updateGameView()
      }
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
  
  override def mouseClicked(){
    //mute main menu
    if(screen ==1 && mouseX >888 && mouseX<968 && mouseY >516 && mouseY<580&& !music.mute){
          screen = 12
          music.stop("jokeri")
          music.mute = true
    }
    //unmute main menu
    else if(screen ==12 &&mouseX >888 && mouseX<968 && mouseY >516 && mouseY<580 && music.mute){
      screen = 1
      music.mute = false
      music.play("jokeri") 
    }
    //open levelscreen
    else if(screen == 1 &&mouseX >342 && mouseX<632 && mouseY >197 && mouseY<274){
      screen = 2
    }
    //open mutedlevelscreen
    else if(screen == 12 &&mouseX >342 && mouseX<632 && mouseY >197 && mouseY<274){
      screen = 22
    }
    // open helpscreen
    else if(screen == 1 && mouseX>342 && mouseX<632 && mouseY>292 && mouseY<363){
      screen = 5
    }
    // open muted helpscreen
    else if(screen == 12 && mouseX>342 && mouseX<632 && mouseY>292 && mouseY<363){
          screen = 52
    }
    // open highscores
    else if((screen == 1) && mouseX >342 && mouseX<632 && mouseY >380 && mouseY<456){
      screen = 6
    }
    //open muted highscores
    else if((screen == 12) && mouseX >342 && mouseX<632 && mouseY >380 && mouseY<456){
      screen = 62
    }
    //open muted main menu
        else if(screen == 62 &&mouseX >83 && mouseX<320 && mouseY >528 && mouseY<588){
          screen = 12
        }
    //open unmuted mainmenu
        else if(screen == 6 &&mouseX >83 && mouseX<320 && mouseY >528 && mouseY<588){
          screen = 1
        }
    //mute highscores
        else if(screen ==6 &&mouseX >888 && mouseX<968 && mouseY >516 && mouseY<580 && !music.mute){
          screen = 62
          music.stop("jokeri") 
          music.mute = true
        }
    //unmute highscores
        else if(screen == 62 &&mouseX >888 && mouseX<968 && mouseY >516 && mouseY<580 && music.mute){
          screen = 6
        music.mute = false
        music.play("jokeri") 
        }
    // start easy
        else if((screen == 2 || screen==22) &&mouseX >342 && mouseX<632 && mouseY >198 && mouseY<274){
          if(screen == 2){
          screen = 3
          if(!gameTrue) {
          if(music.clip2.isActive()) {
            music.stop("jokeri")
          }
          game.clear()
          music.play("juna")
          game.start(1)
          gameTrue = true
          }
          }
          else{
            game.clear()
            game.start(1)
          gameTrue = true
          }
          screen = 3
        }
   //start medium
        else if((screen == 2 || screen == 22) && mouseX >342 && mouseX<632 && mouseY >275 && mouseY<360){
          if(screen == 2){
          if(!gameTrue) {
          if(music.clip2.isActive()) {
            music.stop("jokeri")
          }
          game.clear()
          music.play("juna")
          game.start(2)
          gameTrue = true
          }
          }
          else{
            game.clear()
            game.start(2)
          gameTrue = true
          }
          screen = 3
        }
   //start hard
        else if((screen == 2 || screen == 22) && mouseX >342 && mouseX<632 && mouseY >380 && mouseY<456){
          if(screen == 2){
          if(!gameTrue) {
          if(music.clip2.isActive()) {
            music.stop("jokeri")
          }
          game.clear()
          music.play("juna")
          game.start(3)
          gameTrue = true
          }
          }
          else{
            game.clear()
            game.start(3)
          gameTrue = true
          }
          screen = 3
        }
    //mute levelscreen
      else if(screen ==2 &&mouseX >888 && mouseX<968 && mouseY >516 && mouseY<580 && !music.mute){
        screen = 22
        music.stop("jokeri") 
        music.mute = true
      }
    //unmute levelscreen
      else if(screen == 22 &&mouseX >888 && mouseX<968 && mouseY >516 && mouseY<580 && music.mute){
        screen = 2
        music.mute = false
        music.play("jokeri") 
      }
    //open unmuted mainmenu
      else if(screen == 2 &&mouseX >83 && mouseX<320 && mouseY >528 && mouseY<588){
        screen = 1          
      }
    //open muted mainmenu
      else if(screen == 22 &&mouseX >83 && mouseX<320 && mouseY >528 && mouseY<588) screen = 12
        
    //game over
      else if(screen == 4 && mouseX >402 && mouseX<682 && mouseY >516 && mouseY<590){
        screen = 3
        game.gameOver = false
        game.clear()
        game.start(game.originalLevel)
        gameTrue = true
      }
     // restart
      else if(screen == 42 && mouseX >402 && mouseX<682 && mouseY >516 && mouseY<590){
        screen = 3
        game.gameOver = false
        game.clear()
        game.start(game.originalLevel)
        gameTrue = true
      }
    //open unmuted main menu
      else if(screen == 4 &&mouseX >83 && mouseX<320 && mouseY >528 && mouseY<588){
        screen = 1
        music.stop("juna")
        music.play("jokeri")
        game.gameOver = false
        gameTrue = false
      }
      // open muted main menu  
      else if(screen == 42 &&mouseX >83 && mouseX<320 && mouseY >528 && mouseY<588){
        screen = 12
        game.gameOver = false
        gameTrue = false
      }
      // mute gameover
      else if(screen == 4 && mouseX >892 && mouseX<965 && mouseY >516 && mouseY<577 && !music.mute){
        screen = 42
        music.stop("juna")
        music.mute = true
      }
    // unmute gameover
      else if (screen == 42 && mouseX >892 && mouseX<965 && mouseY >516 && mouseY<577 && music.mute){
        screen = 4
        music.mute = false
        music.play("juna")
      }
    //open unmuted helpscreen
      else if(screen == 5 &&mouseX >83 && mouseX<320 && mouseY >528 && mouseY<588){
        screen = 1
      }
    //open muted main menu
      else if(screen == 52 &&mouseX >83 && mouseX<320 && mouseY >528 && mouseY<588){
        screen = 12
      }
    //mute helpscreen
      else if(screen ==5 &&mouseX >888 && mouseX<968 && mouseY >516 && mouseY<580 && !music.mute){
        screen = 52
        music.stop("jokeri") 
        music.mute = true
      }
    //unmute helpscreen
      else if(screen == 52 &&mouseX >888 && mouseX<968 && mouseY >516 && mouseY<580 && music.mute){
        screen = 5
      music.mute = false
      music.play("jokeri") 
      }
      if(screen != 3){
        this.updateGameView()
      }
  }
  //detecting key presses
  override def keyPressed(){
    keyCode match {
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
      //any other - does nothing
      case _ => {}
    }
  }
}
