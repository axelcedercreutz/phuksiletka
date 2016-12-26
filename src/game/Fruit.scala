package game

import scala.util.Random

class Fruit(width: Int, height: Int) {
  private val random = Random                       // random number
  def nextX = random.nextInt(width)
  def nextY = random.nextInt(height)
}