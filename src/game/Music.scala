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
  private var count = 0
  
  def close {
    clip.close()
  }
  
  def play(Song: String) = {
    if(Song == "juna") {
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
    if(Song == "juna") {
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