package com.play.swarsangam.fregmentnav.musicfregment.audiofolderfregment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.play.swarsangam.R
import com.play.swarsangam.fregmentnav.musicfregment.AudioFolder

class FolderAdapter(private val context: Context,private val audioFolderList: ArrayList<AudioFolder>) :
    RecyclerView.Adapter<FolderAdapter.FolderViewHolder>() {

    inner class FolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val folderName: TextView = itemView.findViewById(R.id.folderName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.folder_item, parent, false)
        return FolderViewHolder(view)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        val folder = audioFolderList[position]
        holder.folderName.text = folder.folderName.toString()
    }

    override fun getItemCount(): Int {
        return audioFolderList.size
    }
}
