package game

import scala.collection.mutable.ArrayBuffer
import scala.util.Random

class Game(val width: Int, val height: Int) {
  
  private val random = Random // random number
  val snakeX = new ArrayBuffer[Int]()
  val snakeY = new ArrayBuffer[Int]()
  val dirX = new ArrayBuffer[Int]()
  val dirY = new ArrayBuffer[Int]()
  var gameOver = false
  var dir = 2
  var appleX = 12
  var appleY = 10
  var next = random.nextInt(4)
  var powerUpX = 10
  var powerUpY = 10
  dirX += (0,0,1,-1)
  dirY += (1,-1,0,0)
  var gameLevel = 1
  var originalLevel = 0
  var count = 0
  var highScoreEasy = 0
  var highScoreNormal = 0
  var highScoreHard = 0
  
  def moveSnake() = {
    println(count)
    println(gameLevel)
    println(originalLevel)
    println(highScoreNormal)
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
      count += 1
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
    if((snakeX.size - 1) > correctHighScore()) {
        newHighScore()
    }
  }
  
  def powerUps() = {
    if(snakeX(0) == powerUpX && snakeY(0) == powerUpY) {
      if(next == 0) {
        if(gameLevel < 4) {
          gameLevel += 1
        }
      }
      else if(next == 1) {
        if(gameLevel > 1) {
          gameLevel -= 1
        }
      }
      else if(next == 2) {
        if(snakeX.size >= 5) {
          snakeX.remove(snakeX.size - 4, 3)
          snakeY.remove(snakeY.size - 4, 3)
        }
      }
      else if(next == 3) {
        snakeX.append((snakeX(snakeX.size -1) + dirX(dir))*2 % width,(snakeX(snakeX.size -1) + dirX(dir)) % width)
        snakeY.append((snakeY(snakeY.size -1) + dirY(dir))*2 % height,(snakeY(snakeY.size -1) + dirY(dir)) % height)
      }
      next = random.nextInt(4)
      powerUpX = random.nextInt(width)
      powerUpY = random.nextInt(height)
    }
  }
  
  
  def clear() = {
    count = 0
    snakeX.clear
    snakeY.clear
  }
  
  def start(level: Int) = {
    gameLevel = level
    originalLevel = gameLevel
    snakeX += 20
    snakeY += 12
  }
  
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