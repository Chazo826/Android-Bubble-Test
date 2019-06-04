package com.chazo.personal.sample.test

import android.app.*
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

const val CHANNEL_ID = "haha"

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager: NotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notificationManager = getSystemService(NotificationManager::class.java)
        setUpNotificationChannels()
        createBubbleNoti()
    }

    fun setUpNotificationChannels() {
        if (notificationManager.getNotificationChannel(CHANNEL_ID) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    "haha",
                    // The importance must be IMPORTANCE_HIGH to show Bubbles.
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "haha"
                }
            )
        }
    }

    private fun createBubbleNoti() {
        val targetIntent = Intent(this, BubblesActivity::class.java)
        val bubbleIntent = PendingIntent.getActivity(this, 0, targetIntent, 0)

        val bubbleData = Notification.BubbleMetadata.Builder()
            .setDesiredHeight(400)
            .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher_round))
            .setIntent(bubbleIntent)
            .build()

        val chatBot = Person.Builder()
            .setBot(true)
            .setName("BubbleBot")
            .setIcon(Icon.createWithResource(this, R.mipmap.ic_launcher_round))
            .setImportant(true)
            .build()

        val builder = Notification.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setBubbleMetadata(bubbleData)
            .addPerson(chatBot)
            .setShowWhen(true)

        with(getSystemService(NotificationManager::class.java)) {
            notify(0, builder.build())
        }
    }
}
