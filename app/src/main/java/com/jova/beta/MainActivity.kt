package com.jova.beta

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Қазірге бета нұсқаның қарапайым фоны (layout) көрсетіледі
        // setContentView(R.layout.activity_main) 

        requestNecessaryPermissions()
    }

    private fun requestNecessaryPermissions() {
        // 1. Қолданба үстінен көрсету рұқсаты
        if (!Settings.canDrawOverlays(this)) {
            val overlayIntent = Intent(
                Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName")
            )
            startActivity(overlayIntent)
            Toast.makeText(this, "Жоғарыдан көрсетуге рұқсат беріңіз", Toast.LENGTH_LONG).show()
        }

        // 2. Батареяны шектеусіз пайдалану рұқсаты
        val batteryIntent = Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS)
        batteryIntent.data = Uri.parse("package:$packageName")
        try {
            startActivity(batteryIntent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
