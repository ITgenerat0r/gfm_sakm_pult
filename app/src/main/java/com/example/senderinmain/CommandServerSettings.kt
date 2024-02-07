package com.example.senderinmain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.senderinmain.objects.SMSgateway
import com.example.senderinmain.objects.SharedPreference

class CommandServerSettings : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command_server_settings)


        val pref = SharedPreference(context = this.baseContext)
        val cm = pref.get_str("cm_settings")
        val phone = pref.get_str("phonenumber")

        val input_first = findViewById<EditText>(R.id.EditText_first)
        val input_second = findViewById<EditText>(R.id.EditText_second)
        val input_third = findViewById<EditText>(R.id.EditText_third)

        val btn = findViewById<Button>(R.id.button_apply)

        if(cm == "SRV" || cm == "UPD"){
            input_first.hint = getString(R.string.server)
            input_second.hint = getString(R.string.port)
            input_third.hint = getString(R.string.connection_interval)
        } else  if (cm == "APN"){
            input_first.hint = getString(R.string.apn)
            input_second.hint = getString(R.string.login)
            input_third.hint = getString(R.string.password)
        }

        btn.setOnClickListener {
            val sms_adapter = SMSgateway(this.baseContext)
            if (input_first.text.isNotEmpty()){
                var msg: String = "${input_first.text}"
                if (input_second.text.isNotEmpty() && input_third.text.isNotEmpty()){
                    msg += ":${input_second.text}:${input_third.text}"
                }
                sms_adapter.send_to_sakm(phone, cm, msg)
            }
        }
    }
}