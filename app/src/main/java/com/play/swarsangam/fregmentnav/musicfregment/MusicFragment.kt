package com.play.swarsangam.fregmentnav.musicfregment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.play.swarsangam.R

class MusicFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_music, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tabs = listOf("Songs", "Albums", "Artists", "Folders", "Playlists")
        val fragments = listOf(
            SongFragment(),
            AlbumFragment(),
            ArtistFragment(),
            FolderFragment(),
            PlaylistFragment()
        )

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)

        tabs.forEachIndexed { index, title ->
            tabLayout.addTab(tabLayout.newTab().setText(title))
        }

        parentFragmentManager.beginTransaction().apply {
            add(R.id.fragment_container, fragments[0])
            commit()
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.let {
                    replaceFragment(fragments[it.position])
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            addToBackStack(null)
            commit()
        }
    }
}
