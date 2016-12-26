package game

import processing.core._

class Game(val width: Int, val height: Int) {
  var highScoreEasy = 0
  var highScoreNormal = 0
  var highScoreHard = 0
  
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