package com.play.swarsangam.fregmentnav.musicfregment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.play.swarsangam.MainActivity
import com.play.swarsangam.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.play.swarsangam.fregmentnav.musicfregment.audiofolderfregment.FolderAdapter


class FolderFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_folder, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the RecyclerView from the layout
        val recyclerView: RecyclerView = view.findViewById(R.id.folderRv)

        // Set layout manager
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Set adapter
        val adapter = FolderAdapter(requireContext(), MainActivity.audioFolderList)
        recyclerView.adapter = adapter
    }

}
