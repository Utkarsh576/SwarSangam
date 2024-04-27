package com.play.swarsangam.fregmentnav.musicfregment

import android.media.MediaPlayer
import android.os.Bundle
import com.play.swarsangam.service.MusicService
import android.os.IBinder



import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.play.swarsangam.MainActivity
import com.play.swarsangam.R
import com.play.swarsangam.databinding.ActivityPlayerBinding


import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.media.AudioManager

import android.os.Handler
import androidx.core.content.getSystemService


import com.bumptech.glide.Glide


class PlayerActivity : AppCompatActivity(), ServiceConnection {

    companion object {
        lateinit var songList: List<AudioFile>
        var currentPosition: Int = 0
        var isPlaying: Boolean = false
        var handler: Handler = Handler()
        var runnable: Runnable? = null
        var musicService: MusicService? = null

        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityPlayerBinding
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val intent = Intent(this, MusicService::class.java)
        bindService(intent, this, BIND_AUTO_CREATE)
        startService(intent)

        initializeLayout()

        binding.imageButtonprev.setOnClickListener {
            prevnextsong(false)
        }

        binding.imageButton2playpause.setOnClickListener {
            togglePlayPause()
        }

        binding.imageButton3next.setOnClickListener {
            prevnextsong(true)
        }

        musicService?.let {
            it.mediaPlayer?.setOnCompletionListener {
                // Automatically move to the next song when the current one finishes
                prevnextsong(true)
            }
        }
    }

    private fun setLayoutUI() {
        Glide.with(this)
            .load(songList[currentPosition].albumArtUri)
            .placeholder(R.drawable.musicplayer)
            .error(R.drawable.musicplayer)
            .into(binding.audioAlbum)
        binding.songName.text = songList[currentPosition].title
    }

    fun createMediaPlayer() {
        try {
            if (musicService!!.mediaPlayer == null) musicService!!.mediaPlayer = MediaPlayer()
            musicService!!.mediaPlayer!!.reset()
            musicService!!.mediaPlayer!!.setDataSource(songList[currentPosition].path)
            musicService!!.mediaPlayer!!.prepare()
            musicService!!.mediaPlayer!!.start()
            isPlaying = true
            musicService!!.showNotification(R.drawable.ic_pause)

            updatePlayPauseButton()
            updateSeekBar()

        } catch (e: Exception) {
            return
        }
    }

    private fun initializeLayout() {
        currentPosition = intent.getIntExtra("position", 0)
        songList = MainActivity.audioList

        setLayoutUI()
    }

    private fun updateSeekBar() {
        runnable = Runnable {
            musicService!!.mediaPlayer?.let {
                binding.seekBar.max = it.duration
                binding.seekBar.progress = it.currentPosition
                //binding.currentTimeTextView.text = formatDuration(it.currentPosition)
            }
            handler.postDelayed(runnable!!, 1000)
        }
        handler.postDelayed(runnable!!, 0)
    }

    private fun togglePlayPause() {
        if (isPlaying) {
            pauseSong()
        } else {
            resumeSong()
        }
    }

    private fun pauseSong() {
        musicService!!.mediaPlayer!!.pause()
        musicService!!.showNotification(R.drawable.ic_play)
        isPlaying = false
        updatePlayPauseButton()
    }

    private fun resumeSong() {
        musicService!!.mediaPlayer!!.start()
        musicService!!.showNotification(R.drawable.ic_pause)
        isPlaying = true
        updatePlayPauseButton()
        updateSeekBar()
    }

    private fun updatePlayPauseButton() {
        val imageResource = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        binding.imageButton2playpause.setImageResource(imageResource)
    }


    fun playSongWithIncrement(increment: Boolean) {
        val incrementValue = if (increment) 1 else -1
        PlayerActivity.currentPosition =
            (PlayerActivity.currentPosition + incrementValue + PlayerActivity.songList.size) % PlayerActivity.songList.size
    }

    /*fun setSongPosition(increment:Boolean){
        if (increment) {
            PlayerActivity.currentPosition++
            if (PlayerActivity.currentPosition >= PlayerActivity.songList.size) {
                PlayerActivity.currentPosition = 0
            }
        } else {
            PlayerActivity.currentPosition--
            if (PlayerActivity.currentPosition < 0) {
                PlayerActivity.currentPosition = PlayerActivity.songList.size - 1
            }
        }
    }*/
    fun prevnextsong(increment: Boolean) {
        if (increment) {
            setSongPosition(true)
            //++currentPosition
            ui()
        } else {
            setSongPosition(false)
            // --currentPosition
            ui()
        }
    }


    fun ui() {
        createMediaPlayer()
        setLayoutUI()
    }
    /*
    private fun playPrevious() {
        playSongWithIncrement(false)
        ui()
    }

    fun playNext() {
        playSongWithIncrement(true)
        ui()
    }*/


    private fun formatDuration(duration: Int): String {
        val minutes = duration / 1000 / 60
        val seconds = duration / 1000 % 60
        return "%d:%02d".format(minutes, seconds)
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        val binder = service as MusicService.MusicBinder
        musicService = binder.getService()
        createMediaPlayer()
        //musicService!!.showNotification(R.drawable.ic_pause)
        musicService!!.audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        musicService!!.audioManager.requestAudioFocus(
            musicService,
            AudioManager.STREAM_MUSIC,
            AudioManager.AUDIOFOCUS_GAIN
        )
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        musicService = null
    }


}