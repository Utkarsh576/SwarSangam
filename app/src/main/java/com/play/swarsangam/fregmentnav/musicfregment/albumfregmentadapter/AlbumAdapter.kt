package com.play.swarsangam.fregmentnav.musicfregment.albumfregmentadapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.play.swarsangam.AlbumInfo
import com.play.swarsangam.R

class AlbumAdapter(private val context: Context,private val albumList: ArrayList<AlbumInfo>) :
    RecyclerView.Adapter    <AlbumAdapter.AlbumViewHolder>() {

    // Inner ViewHolder class
    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val albumNameTextView: TextView = itemView.findViewById(R.id.albumName)
        val albumArt: ImageView= itemView.findViewById(R.id.albumArt)
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.album_item, parent, false)
        return AlbumViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albumList[position]
        holder.albumNameTextView.text = album.album
        Glide.with(context)
            .load(album.albumArtUri) // Assuming albumArtUri is the URI of the album art
            .placeholder(R.drawable.musicplayer) // Placeholder image while loading
            .error(R.drawable.musicplayer) // Error image if Glide fails to load
            .into(holder.albumArt)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = albumList.size
}

