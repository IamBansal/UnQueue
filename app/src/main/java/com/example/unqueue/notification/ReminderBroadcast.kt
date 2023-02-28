package com.example.unqueue.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.unqueue.R

class ReminderBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val builder = NotificationCompat.Builder(context!!, "notifyMe")
            .setSmallIcon(R.drawable.logo)
            .setContentTitle("Your turn approaching...")
            .setContentText("Your turn is approaching, please be in the institution to avoid any delay.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)

        notificationManager.notify(200, builder.build())

    }

}