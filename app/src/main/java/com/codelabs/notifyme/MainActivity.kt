package com.codelabs.notifyme

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.Builder
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val PRIMARY_CHANNEL_ID = "primary_notification_channel"

    private var mNotifyManager: NotificationManager? = null


    private val NOTIFICATION_ID = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notify.setOnClickListener {
            sendNotification()
        }

        update.setOnClickListener {
            updateNotification()
        }

        cancel.setOnClickListener {
            cancelNotification()
        }

        createNotificationChannel()

        getNotificationBuilder()

        setNotificationButtonState(true, false, false)

    }

    private fun cancelNotification() {
        mNotifyManager?.cancel(NOTIFICATION_ID)
        setNotificationButtonState(true, false, false)
    }

    private fun updateNotification() {
        val androidImage = BitmapFactory
            .decodeResource(resources, R.drawable.mascot_1)

        val notifyBuilder = getNotificationBuilder()

        notifyBuilder.setStyle(
            NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("Notification Updated!")
        )

        mNotifyManager?.notify(NOTIFICATION_ID, notifyBuilder.build())

        setNotificationButtonState(false, true, true)

    }

    private fun getNotificationBuilder(): Builder {
        return Builder(this, PRIMARY_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setContentTitle("You've been notified!")
            .setContentText("This is your notification text.")
            .setSmallIcon(R.drawable.ic_android)
    }

    private fun sendNotification() {

        val notifyBuilder: Builder = getNotificationBuilder()
        mNotifyManager?.notify(NOTIFICATION_ID, notifyBuilder.build())
        setNotificationButtonState(false, true, true)

    }

    fun createNotificationChannel() {
        mNotifyManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Mascot Notification", NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Notification from Mascot"
            mNotifyManager?.createNotificationChannel(notificationChannel)
        }
    }

    private fun setNotificationButtonState(
        isNotifyEnabled: Boolean,
        isUpdateEnabled: Boolean,
        isCancelEnabled: Boolean
    ) {
        notify.isEnabled = isNotifyEnabled
        update.isEnabled = isUpdateEnabled
        cancel.isEnabled = isCancelEnabled
    }

}
