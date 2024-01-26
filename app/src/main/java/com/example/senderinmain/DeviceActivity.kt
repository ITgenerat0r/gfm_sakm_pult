package com.example.senderinmain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.senderinmain.objects.Device

class DeviceActivity(var device: Device) : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)
    }

}