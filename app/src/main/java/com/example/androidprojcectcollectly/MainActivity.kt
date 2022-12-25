package com.example.androidprojcectcollectly

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.androidprojcectcollectly.databinding.ActivityMainBinding
import com.example.androidprojcectcollectly.ui.profile.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)




        binding = ActivityMainBinding.inflate(layoutInflater)
        actionBar?.setDisplayShowHomeEnabled(true)
        setContentView(binding.root)
        var checked =
            this.getSharedPreferences("save", Context.MODE_PRIVATE)?.getBoolean(ProfileFragment().KEY_IS_ENABLED, false)

        if (checked== true
        ) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_nav)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun navigate(navigationId: Int) {

        findNavController(R.id.nav_host_fragment_activity_nav).navigate(navigationId)
    }

    fun navigate(navigationId: Int, bundle: Bundle?) {
        findNavController(R.id.nav_host_fragment_activity_nav).navigate(navigationId, bundle)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }
}