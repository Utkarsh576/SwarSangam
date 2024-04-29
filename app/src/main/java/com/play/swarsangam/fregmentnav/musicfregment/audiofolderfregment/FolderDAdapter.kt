package com.play.swarsangam.fregmentnav.musicfregment.audiofolderfregment

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

class FolderDAdapter(
    private val context: Context,
    private val audioList: ArrayList<AudioFile>
) : RecyclerView.Adapter<FolderDAdapter.FolderDViewHolder>() {

    inner class FolderDViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val songTitle: TextView = itemView.findViewById(R.id.folderdsongTitle)
        val songArtImageView: ImageView = itemView.findViewById(R.id.folderdSongArt)
        // You can add more views here if needed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderDViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.folde_ritem_d, parent, false) // Change to the correct layout resource
        return FolderDViewHolder(view)
    }


    override fun onBindViewHolder(holder: FolderDViewHolder, position: Int) {
        val audioFile = audioList[position]
        holder.songTitle.text = audioFile.title
        // Bind other data here if needed
        Glide.with(context)
            .load(audioFile.albumArtUri)
            .placeholder(R.drawable.musicplayer)
            .error(R.drawable.musicplayer)
            .into(holder.songArtImageView)
    }

    override fun getItemCount(): Int {
        return audioList.size
    }
}
