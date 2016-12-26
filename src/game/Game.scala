package game

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Game(val width: Int, val height: Int) {
  
  val powerUp = new PowerUp(width,height)
  private val fruit = new Fruit(width, height)
  private val random = Random // random number 
  val snakeX = new ArrayBuffer[Int]()
  val snakeY = new ArrayBuffer[Int]()
  val dirX = new ArrayBuffer[Int]()
  val dirY = new ArrayBuffer[Int]()
  var gameOver = false
  var dir = 2
  var appleX = fruit.appleX
  var appleY = fruit.appleY
  var powerUpX = powerUp.powerUpX
  var powerUpY = powerUp.powerUpY
  dirX += (0,0,1,-1)
  dirY += (1,-1,0,0)
  snakeX += 20
  snakeY += 12
  var gameLevel = 1
  
  
  var highScoreEasy = 0
  var highScoreNormal = 0
  var highScoreHard = 0
  
  def moveSnake() = {
    if(snakeX(0) + dirX(dir) < 0) {
      snakeX.prepend(width)
      snakeY.prepend(snakeY(0) + dirY(dir))
    }
    else if(snakeY(0) + dirY(dir) < 0) {
      snakeX.prepend(snakeX(0) + dirX(dir))
      snakeY.prepend(height)
    }
    else {
      snakeX.prepend((snakeX(0) + dirX(dir)) % width)
      snakeY.prepend((snakeY(0) + dirY(dir)) % height)
    }
    for(i <- 1 until snakeX.size) {
      if(snakeX(0) == snakeX(i) && snakeY(0) == snakeY(i)) gameOver = true
    }
    if(snakeX(0) == appleX && snakeY(0) == appleY) {
      for(i <- 0 until snakeX.size) {
        if(appleX == snakeX(i) || appleY == snakeY(i)) {
          appleX = random.nextInt(width)
          appleY = random.nextInt(height)
        }
      }
    }
    else {
      snakeX.remove(snakeX.size - 1)
      snakeY.remove(snakeY.size - 1)
    }
    if((snakeX.size - 1) > correctHighScore(gameLevel)) {
        newHighScore(gameLevel, snakeX.size - 1)
    }
  }
  
  def powerUps() = {
    if(snakeX(0) == powerUpX && snakeY(0) == powerUpY) {
      if(powerUp.effects() == "speed up") {
        if(gameLevel < 4) {
          gameLevel += 1
        }
      }
      else if(powerUp.effects() == "slow down") {
        if(gameLevel > 1) {
          gameLevel -= 1
        }
      }
      else if(powerUp.effects() == "cut length") {
        if(snakeX.size >= 5) {
          snakeX.remove(snakeX.size - 4, 3)
          snakeY.remove(snakeY.size - 4, 3)
        }
      }
      else if(powerUp.effects() == "add length") {
        snakeX.append((snakeX(snakeX.size -1) + dirX(dir))*2 % width,(snakeX(snakeX.size -1) + dirX(dir)) % width)
        snakeY.append((snakeY(snakeY.size -1) + dirY(dir))*2 % height,(snakeY(snakeY.size -1) + dirY(dir)) % height)
      }
      powerUpX = random.nextInt(width)
      powerUpY = random.nextInt(height)
    }
  }
  
  
  def clear() = {
    snakeX.clear
    snakeY.clear
  }
  
  def start() = {
    snakeX += 20
    snakeY += 12
  }
  
  def correctHighScore(level: Int) = {
    if(level == 3) {
      highScoreHard
    }
    else if(level == 2) {
      highScoreNormal
    }
    else {
      highScoreEasy
    }
  }
  
  def newHighScore(level: Int, length: Int) = {
    if(level == 3) {
      println(length)
      highScoreHard = length
    }
    else if (level == 2) {
      highScoreNormal = length
    }
    else {
      highScoreEasy = length
    }
  }
}