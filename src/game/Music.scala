package game

import javax.sound.sampled._
import java.io.File

class Music {
  private val junaKulkee = AudioSystem.getAudioInputStream(new File("music/juna_kulkee.wav").getAbsoluteFile())
  private val jokeriTuplaus = AudioSystem.getAudioInputStream(new File("music/jokeri_pokeri_tuplaus_musiikki.wav").getAbsoluteFile())
  private val slurp = AudioSystem.getAudioInputStream(new File("music/slurp.wav").getAbsoluteFile())
  val clip = AudioSystem.getClip
  val clip2 = AudioSystem.getClip
  val clip3 = AudioSystem.getClip
  private var muted = false
  private var backgroundMute = false
  
  def close {
    clip.close()
  }
  
  def play(Song: String) = {
    if(Song == "slurp") {
      println("hore")
      if(!backgroundMute) {
        println("test2")
        if(clip3.isOpen()) {
          clip3.loop(1)
        }
        else {
          println("sataan")
          clip3.open(slurp)
          clip3.start()
        }
      }
    }
    else if(Song == "juna") {
      if(clip.isOpen()) {
        clip.loop(Clip.LOOP_CONTINUOUSLY)
      }
      else{
        clip.open(junaKulkee)
        clip.loop(Clip.LOOP_CONTINUOUSLY)
      }
      muted = false
    }
    else if (Song == "jokeri") {
      if(clip2.isOpen()) {
        clip2.loop(Clip.LOOP_CONTINUOUSLY)
      }
      else {
        clip2.open(jokeriTuplaus)
        clip2.loop(Clip.LOOP_CONTINUOUSLY)
      }
      muted = false
    }    
  }
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
        clip.loop(Clip.LOOP_CONTINUOUSLY)
        muted = false
      }
    }
    else {
      if(clip2.isActive()) {
        clip2.stop()
        muted = true
      }
      else {
       clip2.loop(Clip.LOOP_CONTINUOUSLY)
       muted = false
      }
    } 
  }
}