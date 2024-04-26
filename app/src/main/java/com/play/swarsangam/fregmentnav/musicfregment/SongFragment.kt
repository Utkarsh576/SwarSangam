package com.play.swarsangam.fregmentnav.musicfregment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.play.swarsangam.MainActivity
import com.play.swarsangam.R
import com.play.swarsangam.fregmentnav.musicfregment.songsfregment.SongAdapter

class SongFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the RecyclerView from the layout
        val recyclerView: RecyclerView = view.findViewById(R.id.SongRv)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Set adapter
        val adapter = SongAdapter(requireContext(), MainActivity.audioList)
        recyclerView.adapter = adapter
    }



}
