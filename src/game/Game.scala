package game

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


//the game's logic
class Game(val width: Int, val height: Int) {
  //creating the music for the game
  val music = new Music()
  //an array for the x-axel for the "snake"
  var snakeX = new ArrayBuffer[Int]()
  //an array for the y-axel for the "snake"
  var snakeY = new ArrayBuffer[Int]()
  ////an array for the x-axel for the direction
  val dirX = new ArrayBuffer[Int]()
  //an array for the y-axel for the "snake"
  val dirY = new ArrayBuffer[Int]()
  //the possible directions Down-Up-Right-Left
  dirX += (0,0,1,-1)
  dirY += (1,-1,0,0)
  //the variable that changes when the "snake" hits itself
  var gameOver = false
  //starting direction
  var dir = 2
  //starting spot for the "apple"
  var appleX = 12
  var appleY = 10
  //random number
  val startNumber = 1
  val endX = this.width- 2
  val endY = this.height - 2
  private val random = Random
  //random number for powerups
  private var next = random.nextInt(4)
  //starting spot for the powerup
  var powerUpX = 10
  var powerUpY = 10
  //variables for gamelevels
  var gameLevelForWalls = 1
  var gameLevel = 1
  var originalLevel = 1
  //score count
  var count = 0
  //highscores for each level
   var highScoreEasy = 0
   var highScoreNormal = 0
   var highScoreHard = 0
  
  //the movement of a snake
  def moveSnake() = {
    //if the "snake" "goes out" on the left
    if(snakeX(0) + dirX(dir) < 0) {
      snakeX.prepend(width)
      snakeY.prepend(snakeY(0) + dirY(dir))
    }
    //if the "snake" "goes out" on the top
    else if(snakeY(0) + dirY(dir) < 0) {
      snakeX.prepend(snakeX(0) + dirX(dir))
      snakeY.prepend(height)
    }
    //moving the "snake" in the specific direction
    else {
      snakeX.prepend((snakeX(0) + dirX(dir)) % width)
      snakeY.prepend((snakeY(0) + dirY(dir)) % height)
    }
    //if the snake hits itself it's gameover
    for(i <- 1 until snakeX.size) {
      if(snakeX(0) == snakeX(i) && snakeY(0) == snakeY(i)) gameOver = true
    }
    //if snake hits wall game ends
    if(gameLevelForWalls>1 && snakeX(0) == this.width-1 && dirX(dir) == 1){
      snakeX = snakeX.map(_-1)
      gameOver = true
    }
    else if(gameLevelForWalls>1 && snakeX(0) == 0 && dirX(dir) == -1){
      snakeX = snakeX.map(_+1)
      gameOver = true
    }
    else if(gameLevelForWalls>1 && snakeY(0) == 0 && dirY(dir) == -1){
      snakeY = snakeY.map(_+1)
      gameOver = true
    }
    else if(gameLevelForWalls>1 && snakeY(0) == this.height-1 && dirY(dir) == 1){
      snakeY = snakeY.map(_-1)
      gameOver = true
    }
    //when the "snake's head" and the "apple" are aligned
    if(snakeX(0) == appleX && snakeY(0) == appleY) {
      //score adds one
      count += 1
      for(i <- 0 until snakeX.size) {
        if(appleX == snakeX(i) || appleY == snakeY(i)) {
         //prevents the apples to go out of bounds
          if(gameLevelForWalls>1){
           appleX = startNumber + random.nextInt( (endX - startNumber) + 1 )  
          appleY = startNumber + random.nextInt( (endY - startNumber) + 1 )  
          }
          else{
        appleX = random.nextInt(this.width)
        appleY = random.nextInt(this.height)
      }
        }
      }
    }
    //makes the snake stay the same length if it doesn't hit a "apple"
    else {
      snakeX.remove(snakeX.size - 1)
      snakeY.remove(snakeY.size - 1)
    }
    //changes the highscore
    if((snakeX.size - 1) > correctHighScore()) {
        newHighScore()
    }
  }
  def swapFirstApplelocation = {
    if(gameLevelForWalls>1){
      appleX = startNumber + random.nextInt( (endX - startNumber) + 1 )
      appleY = startNumber + random.nextInt( (endY - startNumber) + 1 )  
      }
    else{
      appleX = random.nextInt(this.width)
      appleY = random.nextInt(this.height)
      }
  }
  //how the powerups works
  def powerUps() = {
    //when the "snake's head" is aligned with the powerup
    if(snakeX(0) == powerUpX && snakeY(0) == powerUpY) {
      count += 3
      //plays the slurp sound
      music.play("slurp")
      //speeds up the "snake"
      if(next == 0) {
        if(gameLevel < 4) {
          gameLevel += 1
        }
      }
      //slows down the "snake"
      else if(next == 1) {
        if(gameLevel > 1) {
          gameLevel -= 1
        }
      }
      //cuts the "snake"
      else if(next == 2) {
        if(snakeX.size >= 5) {
          snakeX.remove(snakeX.size - 4, 3)
          snakeY.remove(snakeY.size - 4, 3)
        }
      }
      //makes the "snake" longer
      else if(next == 3) {
        snakeX.append((snakeX(snakeX.size -1) + dirX(dir)) % width,(snakeX(snakeX.size -1) + dirX(dir) + dirX(dir)) % width)
        snakeY.append((snakeY(snakeY.size -1) + dirY(dir)) % height,(snakeY(snakeY.size -1) + dirY(dir) + dirY(dir)) % height)
      }
      //creates a new random number for the next powerup to be
      next = random.nextInt(4)
      //places the next powerup in a random spot
      //prevents the apples to go out of bounds
      if(gameLevelForWalls>1){
        powerUpX = startNumber + random.nextInt( (endX - startNumber) + 1 )  
        powerUpY = startNumber + random.nextInt( (endY - startNumber) + 1 )
      }
      else{
        powerUpX = random.nextInt(this.width-1)
        powerUpY = random.nextInt(this.height-1)
      }
    }
       
  }
  
  //when the game has ended and the screen is cleared
  def clear() = {
    count = 0
    snakeX.clear
    snakeY.clear
  }
  //when the game starts
  def start(level: Int) = {
    gameLevelForWalls = level
    gameLevel = level
    originalLevel = gameLevel
    swapFirstApplelocation
    snakeX += 20
    snakeY += 12
  }
  //showing the correct highscore for a specific level
  def correctHighScore() = {
    if(originalLevel == 3) {
      highScoreHard
    }
    else if(originalLevel == 2) {
      highScoreNormal
    }
    else {
      highScoreEasy
    }
  }
  //creating the new highscore for the correct level
  def newHighScore() = {
    if(originalLevel == 3) {
      highScoreHard = count
    }
    else if (originalLevel == 2) {
      highScoreNormal = count
    }
    else {
      highScoreEasy = count
    }
  }
}