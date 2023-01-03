package com.example.androidprojcectcollectly

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.androidprojcectcollectly.broadcastRecievers.NetworkBroadcastReciever
import com.example.androidprojcectcollectly.databinding.ActivityMainBinding
import com.example.androidprojcectcollectly.ui.profile.ProfileFragment

class MainActivity : AppCompatActivity() {
    private val networkBroadcastReciever: BroadcastReceiver = NetworkBroadcastReciever()
    private lateinit var binding: ActivityMainBinding
    val CHANNEL_ID = "1"

    /**
     * Create intentFilter when wifi is changed to of and on
     * Register the reciever that will send notifications
     *
     */
    override fun onStart() {
        super.onStart()
        var intenFilter = IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION)

        registerReceiver(networkBroadcastReciever, intenFilter)
    }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        actionBar?.setDisplayShowHomeEnabled(true)
        setContentView(binding.root)
        createNotificationChannel()


        /**
         * Checking if nightmode was on before the app was closed
         *
         */
        var checked =
            this.getSharedPreferences("save", Context.MODE_PRIVATE)
                ?.getBoolean(ProfileFragment().KEY_IS_ENABLED, false)

        if (checked == true
        ) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }

        /**
         * Add the buttom navigation with his controller
         */
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

    /**
     * Unregister the reciever that will send notifications when the application is destroyed
     *
     */
    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkBroadcastReciever)
    }

    /**
     * Function to call when we want to navigate to the certain fragment.
     * Id must been given
     */
    fun navigate(navigationId: Int) {

        findNavController(R.id.nav_host_fragment_activity_nav).navigate(navigationId)
    }

    /**
     * Function to call when we want to navigate to the certain fragment with a bundel of data.
     * Id must been given
     */
    fun navigate(navigationId: Int, bundle: Bundle?) {
        findNavController(R.id.nav_host_fragment_activity_nav).navigate(navigationId, bundle)
    }

    /**
     * Function to call when we press an icon of the navigation bar.
     *
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * The function will create the notification channel where notification of the wifi disabling will be send
     *
     */
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "notification_channel"
            val descriptionText = "Reminder"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}