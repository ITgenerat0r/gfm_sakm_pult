package com.example.senderinmain

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.senderinmain.objects.Device
import com.example.senderinmain.objects.Storage

class Add_device : AppCompatActivity() {
    val error_text_color: Int = 0xFFDF0000.toInt()
    val common_text_color: Int = -3488560
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_device)

        val storage: Storage = Storage(this.baseContext)
        storage.load()

//        val edittext_id = findViewById<EditText>(R.id.EditText_id)
//        edittext_id.visibility = View.GONE
        val phone_textView = findViewById<TextView>(R.id.textView_phone)
        val edittext_phone = findViewById<EditText>(R.id.EditText_phone)

        val edittext_secondphone = findViewById<EditText>(R.id.EditText_additional_phone)
        edittext_secondphone.visibility = View.GONE
        val secondphone_textView = findViewById<TextView>(R.id.textView_additional_phone)
        secondphone_textView.visibility = View.GONE

        val edittext_description = findViewById<EditText>(R.id.EditText_description)
        val btn_save = findViewById<Button>(R.id.button_create_device)

        btn_save.setOnClickListener{
//            val id: String = edittext_id.text.toString()
            val phone: String = edittext_phone.text.toString()

            Log.d(TAG, "Storage: \r\n $storage")

//            val id_textview = findViewById<TextView>(R.id.textView_id)
//            if (id == ""){
//                id_textview.setText("ID - " + getString(R.string.required_field))
//                id_textview.setTextColor(error_text_color)
//                return@setOnClickListener
//            }else if (storage.get_device(id.toInt()) != null){
//                id_textview.setText(R.string.double_device_id)
//                id_textview.setTextColor(error_text_color)
//                return@setOnClickListener
//            } else if (id_textview.currentTextColor == error_text_color){
//                id_textview.setText("ID")
//                id_textview.setTextColor(common_text_color)
//            }
            if (phone == ""){
                phone_textView.setText("${getString(R.string.phone)} - ${getString(R.string.required_field)}")
                phone_textView.setTextColor(error_text_color)
                return@setOnClickListener
            } else if (phone_textView.currentTextColor == error_text_color){
                phone_textView.setText(R.string.phone)
                phone_textView.setTextColor(common_text_color)
            }
            val device: Device = Device(storage.get_new_id())
            device.set_phone(phone)
            device.set_second_phone(edittext_secondphone.text.toString())
            device.set_description(edittext_description.text.toString())
            storage.add_device(device)
            storage.save()


            this.finish()
//            super.onBackPressed()
        }
    }
}