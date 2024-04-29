package com.play.swarsangam.fregmentnav.musicfregment.albumfregmentadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.play.swarsangam.R
import com.play.swarsangam.fregmentnav.musicfregment.AudioFile

class AlbumDAdapter(
    private val context: Context,
    private val audioList: ArrayList<AudioFile>
) : RecyclerView.Adapter<AlbumDAdapter.AlbumViewHolder>() {

    // Inner ViewHolder class
    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val songTitleTextView: TextView = itemView.findViewById(R.id.dsongTitle)
        val songArtImageView: ImageView = itemView.findViewById(R.id.dSongArt)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.detail_item, parent, false)
        return AlbumViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val audioFile = audioList[position]
        holder.songTitleTextView.text = audioFile.title
        Glide.with(context)
            .load(audioFile.albumArtUri)
            .placeholder(R.drawable.musicplayer)
            .error(R.drawable.musicplayer)
            .into(holder.songArtImageView)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = audioList.size
}
