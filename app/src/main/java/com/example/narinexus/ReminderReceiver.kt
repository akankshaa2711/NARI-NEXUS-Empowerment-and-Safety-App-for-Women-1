package com.example.narinexus

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.narinexus.R

class ReminderReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {

        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED) {
            return
        }

        val reminderType = intent?.getStringExtra("reminderType")

        // Show custom notification only for self-care, others remain unchanged
        if (reminderType == "selfcare") {
            val builder = NotificationCompat.Builder(context, "reminderChannel")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Nari Nexus - Self Care Reminder")
                .setContentText("Take a moment for yourself 💆‍♀️ You deserve it!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)

            NotificationManagerCompat.from(context).notify(1001, builder.build())
            return
        }

        // For period or other reminders, default handling
        val notificationText = when (reminderType) {
            "period" -> "Your period cycle is approaching. Stay prepared ❤️"
            else -> "Reminder from Nari Nexus"
        }

        val builder = NotificationCompat.Builder(context, "reminderChannel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("Nari Nexus")
            .setContentText(notificationText)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        NotificationManagerCompat.from(context).notify(101, builder.build())
    }
}
