package game

import scala.collection.mutable.ArrayBuffer

class Snake(gridSize: Int) {
  var parts = List(new BodyPart((gridSize/2, gridSize/2), "down"))

  def updateSnake {
    val p: BodyPart = parts.last
    parts = parts.tail :+
    	 (p.direction match {
            case "up" => p.copy(coordinates = (p.coordinates._1, p.coordinates._2 - 1))
            case "down" => p.copy(coordinates = (p.coordinates._1, p.coordinates._2 + 1))
            case "left" => p.copy(coordinates = (p.coordinates._1 - 1, p.coordinates._2))
            case "right" => p.copy(coordinates = (p.coordinates._1 + 1, p.coordinates._2))
          })
  }
  
  def grow {
    val tail = parts.last
    val newTail = tail.direction match {
      case "up" => new BodyPart((tail.coordinates._1, tail.coordinates._2 + 1), tail.direction)
      case "down" => new BodyPart((tail.coordinates._1, tail.coordinates._2 - 1), tail.direction)
      case "left" => new BodyPart((tail.coordinates._1 + 1, tail.coordinates._2), tail.direction)
      case "right" => new BodyPart((tail.coordinates._1 - 1, tail.coordinates._2), tail.direction)
    }
    parts = newTail +: parts
  }
}
//  //define varibles
//  var len = 1
//  var sidelen = 1f
//  var dir = "right" 
//  //xpos = new ArrayList();
//    //ypos = new ArrayList();
//    //var xpos = game.Game.width
//    //var ypos = random(height)
//  var ArrayList = new ArrayBuffer(xpos:Float, ypos:Float)
//  
//  // constructor
//    len = 1;
//    sidelen = 17;
//    dir = "right";
//    
//  
//  // functions
//  
//  
//  def move() = {
//   for(int i = len - 1; i > 0; i = i -1 ){
//    xpos.set(i, xpos.get(i - 1));
//    ypos.set(i, ypos.get(i - 1));  
//   } 
//   if(dir == "left"){
//     xpos.set(0, xpos.get(0) - sidelen);
//   }
//   if(dir == "right"){
//     xpos.set(0, xpos.get(0) + sidelen);
//   }
//   
//   if(dir == "up"){
//     ypos.set(0, ypos.get(0) - sidelen);
//  
//   }
//   
//   if(dir == "down"){
//     ypos.set(0, ypos.get(0) + sidelen);
//   }
//   xpos.set(0, (xpos.get(0) + width) % width);
//   ypos.set(0, (ypos.get(0) + height) % height);
//   
//    // check if hit itself and if so cut off the tail
//    if( checkHit() == true){
//      len = 1;
//      float xtemp = xpos.get(0);
//      float ytemp = ypos.get(0);
//      xpos.clear();
//      ypos.clear();
//      xpos.add(xtemp);
//      ypos.add(ytemp);
//    }
//  }
//  
//  
//  
//  def display() = {
//    for(i <- 0 until len){
//      stroke(179, 140, 198);
//      fill(100, 0, 100, map(i-1, 0, len-1, 250, 50));
//      rect(xpos.get(i), ypos.get(i), sidelen, sidelen);
//    }  
//  }
//  
//  
//  def addLink() = {
//    xpos.add( xpos.get(len-1) + sidelen);
//    ypos.add( ypos.get(len-1) + sidelen);
//    len++;
//  }
//  
//   def checkHit() = {
//    for(i <- 1 until len){
//     if( dist(xpos.get(0), ypos.get(0), xpos.get(i), ypos.get(i)) < sidelen){
//       return true;
//     } 
//    } 
//    return false;
//   } 
//}