package com.play.swarsangam


import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.play.swarsangam.databinding.ActivityMainBinding
import com.play.swarsangam.fregmentnav.HomeFragment

import com.play.swarsangam.fregmentnav.SettingsFragment
import com.play.swarsangam.fregmentnav.musicfregment.MusicFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right,0)
            insets
        }

        // Set listener for bottom navigation item clicks
        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    replaceFragment(HomeFragment())
                    true
                }
                R.id.menu_music -> {
                    replaceFragment(MusicFragment())
                    true
                }
                R.id.menu_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }
                else -> false
            }
        }

        // Set default fragment
        binding.bottomNavigation.selectedItemId = R.id.menu_home
    }

    private fun replaceFragment(fragment: androidx.fragment.app.Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
