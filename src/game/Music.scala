package game

import javax.sound.sampled._
import java.io.File

class Music {
  private val clip = AudioSystem.getClip
  private var muted = false
  private var count = 0
  
  def close {
    clip.close()
  }
  
  def play(MusicFile: AudioInputStream) = {
    if(count == 0){
        clip.open(MusicFile)
        clip.loop(Clip.LOOP_CONTINUOUSLY)
        muted = false
        count += 1
      }
      else {
        clip.loop(Clip.LOOP_CONTINUOUSLY)
        muted = false
      }
  }
  def stop() = {
    if(!muted) {
      clip.stop()
      muted = true
    }
    else {
     clip.loop(Clip.LOOP_CONTINUOUSLY)
     muted = false
    }
  }
}