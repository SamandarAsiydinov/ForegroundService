package com.example.foregoundservice.service

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.foregoundservice.R
import com.example.foregoundservice.util.Constants.CODE_ACHIEVE_INTENT
import com.example.foregoundservice.util.Constants.CODE_FOREGROUND_SERVICE
import com.example.foregoundservice.util.Constants.CODE_REPLY_INTENT
import com.example.foregoundservice.util.Constants.INTENT_COMMAND
import com.example.foregoundservice.util.Constants.INTENT_COMMAND_ACHIEVE
import com.example.foregoundservice.util.Constants.INTENT_COMMAND_EXIT
import com.example.foregoundservice.util.Constants.INTENT_COMMAND_REPLY
import com.example.foregoundservice.util.Constants.NOTIFICATION_CHANNEL_GENERAL
import java.lang.Exception

class MyForegroundService : Service() {

    override fun onBind(intent: Intent?): IBinder? = null

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val command = intent.getStringExtra(INTENT_COMMAND)
        if (command == INTENT_COMMAND_EXIT) {
            stopService()
            return START_NOT_STICKY
        }
        showNotification()

        if (command == INTENT_COMMAND_REPLY) {
            Toast.makeText(this, "Notification Clicked", Toast.LENGTH_SHORT).show()
        }
        return START_STICKY
    }

    private fun stopService() {
        stopForeground(true)
        stopSelf()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("LaunchActivityFromNotification")
    private fun showNotification() {
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val replyIntent = Intent(this, MyForegroundService::class.java).apply {
            putExtra(INTENT_COMMAND, INTENT_COMMAND_REPLY)
        }
        val achieveIntent = Intent(this, MyForegroundService::class.java).apply {
            putExtra(INTENT_COMMAND, INTENT_COMMAND_ACHIEVE)
        }
        val replyPendingIntent = PendingIntent.getService(
            this, CODE_REPLY_INTENT, replyIntent, PendingIntent.FLAG_MUTABLE
        )
        val achievePendingIntent = PendingIntent.getService(
            this, CODE_ACHIEVE_INTENT, achieveIntent,  PendingIntent.FLAG_MUTABLE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                with(
                    NotificationChannel(
                        NOTIFICATION_CHANNEL_GENERAL,
                        "Jetpack Compose",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                ) {
                    enableLights(false)
                    setShowBadge(false)
                    enableVibration(false)
                    setSound(null, null)
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                    manager.createNotificationChannel(this)
                }
            } catch (e: Exception) {
                Log.d("Error", "${e.localizedMessage} ")
            }
        }
        with(
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_GENERAL)
        ) {
            setTicker(null)
            setContentTitle("Android Developer")
            setContentText("Jetpack Compose")
            setAutoCancel(false)
            setOngoing(true)
            setWhen(System.currentTimeMillis())
            setSmallIcon(R.drawable.ic_launcher_background)
            priority = Notification.PRIORITY_MAX
            setContentIntent(replyPendingIntent)
            addAction(
                0, "REPLY", replyPendingIntent
            )
            addAction(
                0, "ACHIEVE", replyPendingIntent
            )
            startForeground(CODE_FOREGROUND_SERVICE, build())
        }
    }
}