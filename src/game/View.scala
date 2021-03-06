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
  //for helpScreen
  private var helpOn = false
  //for time with powerUp
  private var powerUpCount = 0
     
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
    if(!gameTrue) {
      firstScreen()
    } else {
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
   
   private def startGame(level: Int) = {
     if(screen==2) {
       if(music.clip2.isActive()) {
            music.stop("jokeri")
       }
       game.wasGameOn = true
       game.clear()
       music.play("juna")
       game.start(level)
       gameTrue = true
    }
    else{
      game.clear()
      game.start(level)
      gameTrue = true
    }
    screen = 3
   }

  //scoreboard for the gamescreen
  private def drawScoreboard() {
    fill(118, 22, 167)
    textSize(17)
    if(game.snakeX.head <= 10 && game.snakeY.head <= 7 ||
       game.snakeX.last <= 10 && game.snakeY.last <= 7   ) {
      text( "Score: " + game.count, 820, 500)
      //shows the highscore
      text( "High Score: "+ max(game.count, game.correctHighScore()), 820, 520)
    }
    else {
      text( "Score: " + game.count, 70, 50)
      //shows the highscore
      text( "High Score: "+ max(game.count, game.correctHighScore()), 70, 70)
    }
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
      //making sure the speed up/down goes away
      if(game.originalLevel != game.gameLevel) {
        powerUpCount += 1
        if(powerUpCount % 300 == 0) {
          game.gameLevel = game.originalLevel
          powerUpCount = 0
        }
      }
    }
    else if(game.gameLevel == 3) {
      frameRate(45)
      //making sure the speed up/down goes away
      if(game.originalLevel != game.gameLevel) {
        powerUpCount += 1
        if(powerUpCount % 300 == 0) {
          game.gameLevel = game.originalLevel
          powerUpCount = 0
        }
      }
    }
    else if(game.gameLevel == 2) {
      frameRate(30)
      //making sure the speed up/down goes away
      if(game.originalLevel != game.gameLevel) {
        powerUpCount += 1
        if(powerUpCount % 300 == 0) {
          game.gameLevel = game.originalLevel
          powerUpCount = 0
        }
      }
    }
    else {
      frameRate(15)
      //making sure the speed up/down goes away
      if(game.originalLevel != game.gameLevel) {
        powerUpCount += 1
        if(powerUpCount % 250 == 0) {
          game.gameLevel = game.originalLevel
          powerUpCount = 0
        }
      }
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
  
  def muteScreen(mute: Boolean) = {
    if(mute == true){ 
      //mute main menu
      if(screen ==1){
            screen = 12
            music.stop("jokeri")
            music.mute = true
      }
      //mute highscores
      else if(screen ==6){
        screen = 62
        music.stop("jokeri") 
        music.mute = true
      }
      //mute levelscreen
      else if(screen ==2){
        screen = 22
        music.stop("jokeri") 
        music.mute = true
      }
      // mute gameover
      else if(screen == 4){
        screen = 42
        music.stop("juna")
        music.mute = true
      }
      //mute helpscreen
      else if(screen ==5){
        screen = 52
        music.stop("jokeri") 
        music.mute = true
      }
    }
    else{
      //unmute main menu
      if(screen ==12){
         screen = 1
         music.mute = false
         music.play("jokeri") 
      }
      //unmute highscores
      else if(screen == 62){
        screen = 6
        music.mute = false
        music.play("jokeri") 
      }
      //unmute levelscreen
      else if(screen == 12){
        screen = 2
        music.mute = false
        music.play("jokeri") 
      }
      //unmute helpscreen
      else if(screen == 52){
        screen = 5
        music.mute = false
        music.play("jokeri") 
      }
      // mute gameover
      else if(screen == 42){
        screen = 42
        music.stop("juna")
        music.mute = true
      }
      if(screen != 3){
        this.updateGameView()
      }
    }
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
      helpOn = true
    }
    // open muted helpscreen
    else if(screen == 12 && mouseX>342 && mouseX<632 && mouseY>292 && mouseY<363){
      screen = 52
      helpOn = true
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
      this.startGame(1)
    }
   //start medium
    else if((screen == 2 || screen == 22) && mouseX >342 && mouseX<632 && mouseY >275 && mouseY<360){
      this.startGame(2)
    }
   //start hard
    else if((screen == 2 || screen == 22) && mouseX >342 && mouseX<632 && mouseY >380 && mouseY<456){
      this.startGame(3)
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
      helpOn = false
      screen = 1
    }
    //open muted main menu
    else if(screen == 52 &&mouseX >83 && mouseX<320 && mouseY >528 && mouseY<588){
      screen = 12
      helpOn = false
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
      //m-key
      case 77 => {
         //checks what to mute
         if(!gameTrue) {
           if(music.mute == true){
             this.muteScreen(false)
             music.mute = false
             music.play("jokeri")
             this.updateGameView
           }
           else{
             this.muteScreen(true)
             music.stop("jokeri")
             music.mute = true
             this.updateGameView
           }
         }
         else {
           if(music.mute == true){
             music.mute = false
             music.play("juna")
           }
           else{
             music.stop("juna")
             music.mute =  true
           }
         }
       }
      //h-key
      case 72 => {
         //shows & hides the helpscreen
          if(gameTrue == false && music.mute == true && helpOn == true && game.wasGameOn == false){
            game.gameOver = false
            helpOn = false
            screen = 12
          }
          else if(gameTrue == false && music.mute == false && helpOn == true && game.wasGameOn == false){
            music.play("jokeri")
            music.stop("juna")
            game.gameOver = false
            helpOn = false
            screen = 1
          }
          else if(gameTrue == false && music.mute == true && helpOn == false && game.wasGameOn == false){
            helpOn = true
            screen = 52
          }
          else if(gameTrue == false && music.mute == false && helpOn == false && game.wasGameOn == false){ 
            helpOn = true
            screen = 5
            }
          else if(gameTrue == true && music.mute == false && helpOn == false && game.wasGameOn == false){
            gameTrue = false
            helpOn = true
            screen = 5
          }
          else if(gameTrue == true && music.mute == true && helpOn == false && game.wasGameOn == false){
            gameTrue = false
            helpOn = true
            screen = 52
          }
          else if(gameTrue == true && music.mute == false && helpOn == true && game.wasGameOn == false){
            helpOn = false
            game.gameOver = false
            gameTrue = false
            screen = 1
          }
          else if(gameTrue == true && music.mute == true && helpOn == true && game.wasGameOn == false){
            helpOn = false
            game.gameOver = false
            gameTrue = false
            screen = 12
          }
          else if(gameTrue == true && music.mute == false && helpOn == false && game.wasGameOn == true){
            helpOn = true
            gameTrue = false
            screen = 5
          }
          else if(gameTrue == true && music.mute == true && helpOn == false && game.wasGameOn == true){
            helpOn = true
            gameTrue = false
            screen = 52
          }
          else if(gameTrue == false && music.mute == true && helpOn == true && game.wasGameOn == true){
            helpOn = false
            screen = 3
            gameTrue = true
          }
          else if(gameTrue == false && music.mute == false && helpOn == true && game.wasGameOn == true){
            helpOn = false
            screen = 3
            gameTrue = true
          }
          if(screen != 3 ) this.updateGameView
          
      }
      //any other - does nothing
      case _ => {}
    }
  }
}
