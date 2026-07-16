package com.jova.beta

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val videoView = findViewById<VideoView>(R.id.videoView)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.anim
        videoView.setVideoURI(Uri.parse(videoPath))

        // Видео толық ойнап біткенде іске қосылады
        videoView.setOnCompletionListener {
            requestNecessaryPermissions()
        }

        // Қолданба ашыла сала видеоны бастау
        videoView.start()
    }

    private fun requestNecessaryPermissions() {
        if (!Settings.canDrawOverlays(this)) {
            val overlayIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivity(overlayIntent)
            Toast.makeText(this, "Жоғарыдан көрсетуге рұқсат беріңіз", Toast.LENGTH_LONG).show()
        }

        try {
            val batteryIntent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
            batteryIntent.data = Uri.parse("package:$packageName")
            startActivity(batteryIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
