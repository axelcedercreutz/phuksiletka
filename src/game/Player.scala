package game

class Player(var x: Int, var y: Int) {
  def move(newX: Int, newY: Int) = {
    x = newX
    y = newY
    var z = ""
  }

  def canMoveTo(xPos: Int, yPos: Int) = {
    Math.abs(xPos - x) <= 2 && Math.abs(yPos - y) <= 2
  }

}


