package com.example.foregoundservice.util

import android.content.Context
import android.content.Intent
import android.os.Build
import com.example.foregoundservice.service.MyForegroundService
import com.example.foregoundservice.util.Constants.INTENT_COMMAND

object Constants {
    const val INTENT_COMMAND = "Command"
    const val INTENT_COMMAND_EXIT = "Exit"
    const val INTENT_COMMAND_REPLY = "Reply"
    const val INTENT_COMMAND_ACHIEVE = "Achieve"

    const val NOTIFICATION_CHANNEL_GENERAL = "Checking"
    const val CODE_FOREGROUND_SERVICE = 1
    const val CODE_REPLY_INTENT = 2
    const val CODE_ACHIEVE_INTENT = 3
}

fun Context.foregroundStartService(command: String) {
    val intent = Intent(this, MyForegroundService::class.java)
    if (command == "Start") {
        intent.putExtra(INTENT_COMMAND, command)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(intent)
        } else {
            this.startService(intent)
        }
    } else if (command == "Exit") {
        intent.putExtra(INTENT_COMMAND, command)
        this.stopService(intent)
    }
}