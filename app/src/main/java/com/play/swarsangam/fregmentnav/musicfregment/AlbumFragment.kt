package com.play.swarsangam.fregmentnav.musicfregment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.play.swarsangam.MainActivity
import com.play.swarsangam.R
import com.play.swarsangam.fregmentnav.musicfregment.albumfregmentadapter.AlbumAdapter


class AlbumFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the RecyclerView from the layout
        val recyclerView: RecyclerView = view.findViewById(R.id.albumRv)

        // Set layout manager
        recyclerView.layoutManager = GridLayoutManager(requireContext(),2,GridLayoutManager.VERTICAL,false)

        // Set adapter
        val adapter = AlbumAdapter(requireContext(), MainActivity.albumList)
        recyclerView.adapter = adapter
    }
}
