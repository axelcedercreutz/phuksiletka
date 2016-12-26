package game

import scala.util.Random
import processing.core._

class PowerUp(width: Int, height: Int) {
  var powerUpX = 10
  var powerUpY = 10
  
  val random = Random
  var next = random.nextInt(4)
  
  def effects() = {
    if(next == 0) {
      "speed up"
    }
    else if(next == 1) {
      "slow down"
    }
    else if(next == 2) {
      "cut length"
    }
    else if(next == 3) {
      "add length"
    }
  }
}