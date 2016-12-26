package game

import scala.util.Random
import processing.core._

class powerUp(width: Int, height: Int) {
  private val random = Random
  private val next = random.nextInt(4)
  
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