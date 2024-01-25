package com.example.senderinmain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.senderinmain.objects.Device
import com.example.senderinmain.objects.Storage

class Add_device : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)

        val storage: Storage = Storage(this.baseContext)
        storage.load()

        val edittext_id = findViewById<EditText>(R.id.EditText_id)
        val edittext_phone = findViewById<EditText>(R.id.EditText_phone)
        val edittext_secondphone = findViewById<EditText>(R.id.EditText_additional_phone)
        val edittext_description = findViewById<EditText>(R.id.EditText_description)
        val btn_save = findViewById<Button>(R.id.button_create_device)

        btn_save.setOnClickListener{
            val device: Device = Device(edittext_id.text.toString().toInt())
            device.set_phone(edittext_phone.text.toString())
            device.set_second_phone(edittext_secondphone.text.toString())
            device.set_description(edittext_description.text.toString())
            storage.add_device(device)
            storage.save()
        }
    }
}