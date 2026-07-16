package com.jova.beta

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val videoView = findViewById<VideoView>(R.id.videoView)
        videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/" + R.raw.anim))
        
        // Видео толық экранда толтырылуы үшін (onPrepared)
        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            val videoRatio = mp.videoWidth.toFloat() / mp.videoHeight.toFloat()
            val screenRatio = videoView.width.toFloat() / videoView.height.toFloat()
            val scale = videoRatio / screenRatio
            if (scale >= 1f) {
                videoView.scaleX = scale
            } else {
                videoView.scaleY = 1f / scale
            }
        }
        
        videoView.setOnCompletionListener { showPermissionDialog() }
        videoView.start()
    }

    private fun showPermissionDialog() {
        AlertDialog.Builder(this)
            .setTitle("Жүйеге кіру")
            .setMessage("Толық функционалды қолдану үшін рұқсаттарды бекітіңіз.")
            .setPositiveButton("Рұқсат беру") { _, _ ->
                val overlayIntent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
                startActivity(overlayIntent)
            }
            .setCancelable(false)
            .show()
    }
}
