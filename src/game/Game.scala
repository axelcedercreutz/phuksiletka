package game

import processing.core._

class Game(Height: Int, Width: Int) {
  val height = Height
  val width = Width
  var highScoreEasy = 0
  var highScoreNormal = 0
  var highScoreHard = 0
  
  def correctHighScore(level: Int) = {
    if(level == 3) {
      println("fuckedi fuck")
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
      println("fucked")
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