package com.example.androidprojcectcollectly.broadcastRecievers

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.androidprojcectcollectly.MainActivity
import com.example.androidprojcectcollectly.R

class NetworkBroadcastReciever : BroadcastReceiver() {
    /**
     * Get the wifi status of the intent and look if the wifi is disabled
     * When it is disabled  create a notification and send it to the user
     *
     */
    override fun onReceive(context: Context, intent: Intent) {

        var extraIntent =
            intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN)

        if (extraIntent == WifiManager.WIFI_STATE_DISABLED) {
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

            //Create or notification with logo and text and also the intent
            var builder = NotificationCompat.Builder(context, "1")
                .setSmallIcon(R.drawable.logo_200x200)
                .setContentTitle("Wifi is turned off!!!")
                .setContentText("For some functionalities you need internet connection!!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            //Sending the notification on channel 1
            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                notify(1, builder.build())
            }

        }

    }
}