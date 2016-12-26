package game

import scala.util.Random
import processing.core._

class PowerUp(width: Int, height: Int) {
  //private val game = new Game(width, height)
  var powerUpX = 10
  var powerUpY = 10
  
  private val random = Random
  //private var next = random.nextInt(4)
  var next = 3
  
  def effects() = {
    if(next == 0) {
      next = random.nextInt(4)
      "speed up"
    }
    else if(next == 1) {
      next = random.nextInt(4)
      "slow down"
    }
    else if(next == 2) {
      //next = random.nextInt(4)
      "cut length"
    }
    else if(next == 3) {
      //next = random.nextInt(4)
      "add length"
    }
  }
}