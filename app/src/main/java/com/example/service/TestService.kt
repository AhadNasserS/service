package com.example.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service.START_STICKY
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat

const val STOP_SERVICE="stop_service_action"
const val NOTIFICATION_CHANNEL = 102
class TestService : android.app.Service() {
    override fun onBind(intent : Intent?): IBinder? {
        return null
    }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onStartCommand(intent:Intent? , flags: Int , startId:Int): Int {
        if (intent?.action != null && intent.action == STOP_SERVICE){
            stopSelf()
        }
        sendNotification()
        return START_STICKY
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun sendNotification(){
        val notification = Notification.Builder(this, "$NOTIFICATION_CHANNEL")
        notification.setContentTitle("Test Service")
        notification.setSmallIcon(R.drawable.ic_launcher_foreground)
        notification.setContentText("Running")
        notification.setAutoCancel(true)
        notification.setVisibility(Notification.VISIBILITY_PUBLIC)

        val pendingIntent =
            PendingIntent.getActivity(
                this,
                0,
               Intent(this,MainActivity::class.java),
               PendingIntent.FLAG_IMMUTABLE
            )

        notification.setContentIntent(pendingIntent)

//        notification.setPriority() = NotificationCompat.PRIORITY_LOW
        val notificationManager =
             application.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //if(Build.VERSION_CODES.SDK_INT >= Build.VERSION_CODES.O)
        notificationManager.createNotificationChannel(
            NotificationChannel(
                "$NOTIFICATION_CHANNEL",
                "Channel_name",
                NotificationManager.IMPORTANCE_DEFAULT
            )
        )
        notificationManager.notify(NOTIFICATION_CHANNEL, notification.build())
        startForeground(NOTIFICATION_CHANNEL,notification.build())
    }
}