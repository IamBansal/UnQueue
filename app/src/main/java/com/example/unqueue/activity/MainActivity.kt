package com.example.unqueue.activity

import android.app.NotificationChannel
import android.app.NotificationManager
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unqueue.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createNotificationChannel()

    }

    private fun createNotificationChannel(){
        val name = "NotifyChannel"
        val description = "channel for notification"
        val channel = NotificationChannel("notifyMe", name, NotificationManager.IMPORTANCE_HIGH)
        channel.description = description

        val ringtoneManager = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION).build()

        channel.setSound(ringtoneManager, audioAttributes)

        val notificationManager : NotificationManager = getSystemService(NotificationManager ::class.java)
        notificationManager.createNotificationChannel(channel)

    }
}