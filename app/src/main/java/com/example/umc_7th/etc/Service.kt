package com.example.umc_7th.etc

import android.app.Service
import android.content.Intent
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.IBinder
import android.os.Build
import androidx.core.app.NotificationCompat
import android.graphics.BitmapFactory

import com.example.umc_7th.home.MainActivity

class MusicPlayerService : Service() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startForeground(1, createNotification())
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotification(): Notification {
        val notificationChannelId = "MUSIC_PLAYER_CHANNEL"
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                "Music Player",
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 클릭 시 앱의 MainActivity를 여는 Intent와 PendingIntent
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // FLAG_IMMUTABLE은 API 23 이상 필요
        )

        return NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Now Playing")
            .setContentText("Your favorite song is playing")
            .setSmallIcon(R.drawable.icon_browse_arrow_right) // 작은 아이콘
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.img_album_exp)) // 큰 아이콘
            .setContentIntent(pendingIntent) // 클릭 시 MainActivity로 이동
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(true) // 사용자가 수동으로만 제거할 수 있도록 설정
            .build()
    }
}
fun startMusicService(context: Context) {
    val intent = Intent(context, MusicPlayerService::class.java)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        context.startForegroundService(intent)
    } else {
        context.startService(intent)
    }
}