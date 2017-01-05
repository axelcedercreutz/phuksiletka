package game

import javax.sound.sampled._
import java.io.File
import  sun.audio._

//class for starting and stopping music
class Music {
  //creating AudioInputStreams of all the required songs
  private val junaKulkee = AudioSystem.getAudioInputStream(new File("music/juna_kulkee.wav"))
  private val jokeriTuplaus = AudioSystem.getAudioInputStream(new File("music/jokeri_pokeri_tuplaus_musiikki.wav"))
  private val slurp = AudioSystem.getAudioInputStream(new File("music/slurp.wav")) //.getAbsoluteFile())
  //clips for all of the different songs
  val clip = AudioSystem.getClip
  val clip2 = AudioSystem.getClip
  val clip3 = AudioSystem.getClip
  var mute = false
 
  //open slurp sound for powerups
  clip3.open(slurp)
  
  //starting the songs to play
  def play(Song: String) = {
   
    //if it's the background song for the game
     if(Song == "juna" && !mute) {
      //if the clip is not already open it opens it, otherwise it just starts the endless loop
      if(!clip.isOpen()) {
        clip.open(junaKulkee)
      }
      clip.loop(Clip.LOOP_CONTINUOUSLY)
    }
    
     //if it's the background song for the main menu
    else if (Song == "jokeri" && !mute) {
      //if the clip is not already open it opens it, otherwise it just starts the endless loop
      if(!clip2.isOpen()) {
        clip2.open(jokeriTuplaus)
      }
      clip2.loop(Clip.LOOP_CONTINUOUSLY)
    }    
    //if the effect-sounds have been turned off / is not playing
  else if (Song == "slurp" && !mute){
    clip3.setFramePosition(0)
    clip3.start
    }
  }
  //stops the song if it's playing, calls for the play function if the song is not 
  def stop(Song: String) = {
    if(Song == "jokeri") {
      if(clip2.isActive()) {
        clip2.stop()
      }
    }
    else if(Song == "juna") {
      if(clip.isActive()) {
        clip.stop()
      }
    }
    else if(Song == "slurp") {
      if(clip3.isActive())
        clip3.stop()
    } 
  }
}