package com.example.senderinmain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.senderinmain.objects.Device
import com.example.senderinmain.objects.SharedPreference
import com.example.senderinmain.objects.Storage

class DeviceActivity : AppCompatActivity() {

    var storage: Storage? = null
    var sharedPreference: SharedPreference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG = "DeviceActivity"
        Log.d(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)

        val txt_head = findViewById<TextView>(R.id.textView_head)

        storage = Storage(this)
        storage!!.load()
        sharedPreference = SharedPreference(this)
        Log.d(TAG, "pref")

        val id: Int = sharedPreference!!.get_int("device_id")
        if (id > 0){
            Log.d(TAG,"id > 0")
            val device = storage!!.get_device(id)
            if (device != null){
                txt_head.setText(device.toString())
                val intent = Intent(this, DeviceActivity::class.java)
            }
        }
    }

}