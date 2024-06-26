package com.play.swarsangam
import android.Manifest


import android.content.ContentUris
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.play.swarsangam.databinding.ActivityMainBinding
import com.play.swarsangam.fregmentnav.HomeFragment

import com.play.swarsangam.fregmentnav.SettingsFragment
import com.play.swarsangam.fregmentnav.musicfregment.AudioFile
import com.play.swarsangam.fregmentnav.musicfregment.AudioFolder
import com.play.swarsangam.fregmentnav.musicfregment.MusicFragment
import com.play.swarsangam.fregmentnav.musicfregment.PlayerActivity
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,0)
            insets
        }
        checkPermissions()

        // Set listener for bottom navigation item clicks
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                /*R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    true
                }*/
                R.id.menu_music -> {
                    replaceFragment(MusicFragment())
                    true
                }
                R.id.menu_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }

        }


        // Set default fragment
        binding.bottomNavigation.selectedItemId = R.id.menu_music
    }

    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
    private fun checkPermissions() {
        val readPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val writePermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val permissionsToRequest = arrayListOf<String>()

        if (readPermission != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISSIONS_REQUEST_CODE
            )
        } else {
            // Permissions already granted, proceed with loading audio files
            loadAudioFiles()
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                    // Permissions granted, proceed with loading audio and video files
                    loadAudioFiles()
                    //loadVideoFiles()
                }

                else{
                    checkPermissions()

                }
            }
        }

    }
    private fun loadAudioFiles() {
        val audioUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val audioProjection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.ALBUM_ID
        )
        val audioCursor = contentResolver.query(audioUri, audioProjection, null, null, null)

        audioCursor?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val albumIdColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID)
            val durationColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)
            val sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)
            val pathColumn = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val title = cursor.getString(titleColumn)
                val artist = cursor.getString(artistColumn)
                val album = cursor.getString(albumColumn)
                val albumId = cursor.getLong(albumIdColumn)
                val duration = cursor.getLong(durationColumn)
                val size = cursor.getLong(sizeColumn)
                val path = cursor.getString(pathColumn)
                val albumArtUri = ContentUris.withAppendedId(
                    Uri.parse("content://media/external/audio/albumart"),
                    albumId
                )
                val filePath = path.substringBeforeLast('/')
                val folderName = filePath.substringAfterLast('/')

                 val existingAlbum = albumList.find { info ->
                     info.album == album && info.albumId == albumId
                 }
                 if (existingAlbum == null) {
                     // If not, add it along with the art URI
                     val albumInfo = AlbumInfo(albumId, album, albumArtUri)
                     albumList.add(albumInfo)
                 }

                val audioFile = AudioFile(
                    id,
                    title,
                    artist,
                    album,
                    albumId,
                    duration,
                    size,
                    path,
                    albumArtUri
                )
                audioList.add(audioFile)
                val audioFolder = AudioFolder(folderName)
                if (!audioFolderList.contains(audioFolder)) {
                    audioFolderList.add(audioFolder)
                }

                if (!artistList.contains(artist)) {
                    artistList.add(artist)
                }

            }
        }
    }

    companion object {

        private const val PERMISSIONS_REQUEST_CODE = 100
        val audioList = ArrayList<AudioFile>() // ArrayList to store audio files
        //val videoList = ArrayList<VideoFile>() // ArrayList to store video files
        val audioFolderList = ArrayList<AudioFolder>()
        val albumList = ArrayList<AlbumInfo>()
        val artistList = ArrayList<String>()
        /*val folderList = ArrayList<VideoFolder>()

        */
    }

    /*override fun onDestroy() {
        super.onDestroy()
        if (!PlayerActivity.isPlaying && PlayerActivity.musicService != null){
            PlayerActivity.musicService!!.startForeground(true)
            PlayerActivity.musicService!!.mediaPlayer!!.release()
            PlayerActivity.musicService= null
            exitProcess(1)
        }
    }*/
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()


             // Close all activities and exit
            return
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()

        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000) // Milliseconds threshold for double click
    }
}




