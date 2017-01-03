package game

import javax.sound.sampled._
import java.io.File

//class for starting and stopping music
class Music {
  //creating AudioInputStreams of all the required songs
  private val junaKulkee = AudioSystem.getAudioInputStream(new File("music/juna_kulkee.wav").getAbsoluteFile())
  private val jokeriTuplaus = AudioSystem.getAudioInputStream(new File("music/jokeri_pokeri_tuplaus_musiikki.wav").getAbsoluteFile())
  private val slurp = AudioSystem.getAudioInputStream(new File("music/slurp.wav").getAbsoluteFile())
  //clips for all of the different songs
  val clip = AudioSystem.getClip
  val clip2 = AudioSystem.getClip
  val clip3 = AudioSystem.getClip
  private var muted = false
  var backgroundMute = false
  
  //starting the songs to play
  def play(Song: String) = {
   
    //if it's the background song for the game
     if(Song == "juna") {
      //if the clip is not already open it opens it, otherwise it just starts the endless loop
      if(!clip.isOpen()) {
        clip.open(junaKulkee)
      }
      clip.loop(Clip.LOOP_CONTINUOUSLY)
      muted = false
    }
    
     //if it's the background song for the main menu
    else if (Song == "jokeri") {
      //if the clip is not already open it opens it, otherwise it just starts the endless loop
      if(!clip2.isOpen()) {
        clip2.open(jokeriTuplaus)
      }
      clip2.loop(Clip.LOOP_CONTINUOUSLY)
      muted = false
    }    
    //if the effect-sounds have been turned off / is not playing
  else{
      if(clip3.isOpen()) {
        clip3.loop(1)
      }
      else {
        clip3.open(slurp)
        clip3.start()
      }
    }  
  }
  //stops the song if it's playing, calls for the play function if the song is not 
  def stop(Song: String) = {
    if(Song == "slurp") {
      if(clip3.isActive()) {
        clip3.stop()
        backgroundMute = true
      }
      else {
        backgroundMute = false
      }
    }
    else if(Song == "juna") {
      if(clip.isActive()) {
        clip.stop()
        muted = true
      }
      else {
        play("juna")
      }
    }
    else {
      if(clip2.isActive()) {
        clip2.stop()
        muted = true
      }
      else {
       play("jokeri")
      }
    } 
  }
}