package com.play.swarsangam.fregmentnav.musicfregment.artistfregment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.play.swarsangam.R
import com.play.swarsangam.fregmentnav.musicfregment.AudioFile

class ArtistDAdapter(private val audioList: List<AudioFile>) : RecyclerView.Adapter<ArtistDAdapter.ArtistViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.artist_d, parent, false)
        return ArtistViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        val audio = audioList[position]
        holder.bind(audio)
    }

    override fun getItemCount(): Int {
        return audioList.size
    }

    inner class ArtistViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val songTitleTextView: TextView = itemView.findViewById(R.id.artistdsongTitle)

        fun bind(audio: AudioFile) {
            songTitleTextView.text = audio.title
            // Bind other data here if needed
        }
    }
}
