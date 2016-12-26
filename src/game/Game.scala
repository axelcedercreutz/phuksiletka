package game

import processing.core._

class Game(Height: Int, Width: Int) {
  val height = Height
  val width = Width
  var highScore1 = 0
  var highScore2 = 0
  var highScore3 = 0
  
  def newHighScore(frameRate: Float, length: Int) = {
    if(frameRate == 80) {
      highScore1 = length - 1
    }
    else if (frameRate == 140) {
      highScore2 = length - 1
    }
    else highScore3 = length - 1
  }
}