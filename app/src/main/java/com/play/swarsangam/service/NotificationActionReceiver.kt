package com.play.swarsangam.service




import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.bumptech.glide.Glide
import com.play.swarsangam.R
import com.play.swarsangam.fregmentnav.musicfregment.PlayerActivity
import com.play.swarsangam.fregmentnav.musicfregment.setSongPosition


import kotlin.system.exitProcess

class NotificationActionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (intent?.action) {
            AppClass.PREV -> {
                if (context != null) {
                    nextMusic(increment = false, context)
                }
            }
            AppClass.PLAY -> {
                if (PlayerActivity.isPlaying) pauseMusic() else playMusic()
            }
            AppClass.NEXT -> {
                if (context != null) {
                    nextMusic(increment = true, context)
                }
            }
            AppClass.EXIT -> {
                PlayerActivity.musicService?.stopForeground(true)
                PlayerActivity.musicService = null
                exitProcess(1) // This will close the app
            }
        }
    }

    private fun playMusic() {
        PlayerActivity.isPlaying = true
        PlayerActivity.musicService?.mediaPlayer?.start()
        PlayerActivity.musicService?.showNotification(R.drawable.pause)
        PlayerActivity.binding.imageButton2playpause.setImageResource(R.drawable.pause)
    }

    private fun pauseMusic() {
        PlayerActivity.isPlaying = false
        PlayerActivity.musicService?.mediaPlayer?.pause()
        PlayerActivity.musicService?.showNotification(R.drawable.playbutton)
        PlayerActivity.binding.imageButton2playpause.setImageResource(R.drawable.playbutton)
    }

    private fun nextMusic(increment: Boolean, context: Context) {
        setSongPosition(increment)

        try {
            PlayerActivity.musicService?.mediaPlayer?.reset()
            PlayerActivity.musicService?.mediaPlayer?.setDataSource(PlayerActivity.songList[PlayerActivity.currentPosition].path)
            PlayerActivity.musicService?.mediaPlayer?.prepare()
            PlayerActivity.musicService?.showNotification(R.drawable.pause)
            playMusic()

            PlayerActivity.binding.songName.text = PlayerActivity.songList[PlayerActivity.currentPosition].title

            if (context != null) {
                Glide.with(context)
                    .load(PlayerActivity.songList[PlayerActivity.currentPosition].albumArtUri)
                    .placeholder(R.drawable.musicplayer)
                    .error(R.drawable.musicplayer)
                    .into(PlayerActivity.binding.audioAlbum)
            }

        } catch (e: Exception) {
            Log.e("MediaPlayer", "Error preparing media player: ${e.message}")
        }
    }
}
