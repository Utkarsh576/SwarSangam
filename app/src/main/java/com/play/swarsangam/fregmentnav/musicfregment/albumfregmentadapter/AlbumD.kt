package com.play.swarsangam.fregmentnav.musicfregment.albumfregmentadapter

import android.content.ContentUris
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.play.swarsangam.AlbumInfo
import com.play.swarsangam.MainActivity
import com.play.swarsangam.R
import com.play.swarsangam.fregmentnav.musicfregment.AudioFile

class AlbumD : AppCompatActivity() {
    private val audioList = ArrayList<AudioFile>()
    private lateinit var albumAdapter: AlbumDAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_album_d)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize the RecyclerView and its adapter
        recyclerView = findViewById(R.id.albumDRv)
        recyclerView.layoutManager = LinearLayoutManager(this)
        albumAdapter = AlbumDAdapter(this, audioList)
        recyclerView.adapter = albumAdapter

        // Retrieve data from Intent extras
        val position = intent.getIntExtra("position", -1)
        val albumList=MainActivity.albumList

        // Now you can use the position and albumList as needed
        Toast.makeText(this,"$position",Toast.LENGTH_LONG).show()

        // Check if albumList is not null and position is valid
        if (position != -1 && albumList != null && position < albumList.size) {
            // Access the album at the specified position
            val selectedAlbum = albumList[position]

            // Now you have the selected album, you can use it as needed
            // For example, you can access its properties like selectedAlbum.albumId, selectedAlbum.album, etc.

            // Once you have the album, you can filter the audio files list based on this album
            loadAudioFiles(selectedAlbum)

            // Now audioList contains all audio files for the selected album
            // You can use this list to display or perform operations related to the selected album's audio files
        } else {
            // Handle invalid position or null albumList
        }
    }


    private fun loadAudioFiles(selectedAlbum: AlbumInfo) {
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
        val selection = "${MediaStore.Audio.Media.ALBUM_ID}=?"
        val selectionArgs = arrayOf(selectedAlbum.albumId.toString())
        val audioCursor = contentResolver.query(
            audioUri,
            audioProjection,
            selection,
            selectionArgs,
            null
        )

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
            }
        }
        // Notify the adapter that data has changed
        albumAdapter.notifyDataSetChanged()
    }
}
