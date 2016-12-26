package game

import scala.util.Random
import processing.core._

class PowerUp(width: Int, height: Int) {
  
  var powerUpX = 10
  var powerUpY = 10
  
  private val random = Random
  private var next = random.nextInt(4)
  
  def effects() = {
    if(next == 0) {
      println("speed up")
      next = random.nextInt(4)
    }
    else if(next == 1) {
      println("slow down")
      next = random.nextInt(4)
    }
    else if(next == 2) {
      println("cut length")
      next = random.nextInt(4)
    }
    else if(next == 3) {
      println("add length")
      next = random.nextInt(4)
    }
  }
}