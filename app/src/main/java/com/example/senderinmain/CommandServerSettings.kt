package com.example.senderinmain

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.senderinmain.objects.SMSgateway
import com.example.senderinmain.objects.SharedPreference

class CommandServerSettings : AppCompatActivity() {

    var input_first: EditText? = null
    var input_second: EditText? = null
    var input_third: EditText? = null

    var txt_first: TextView? = null
    var txt_second: TextView? = null
    var txt_third: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command_server_settings)


        val pref = SharedPreference(context = this.baseContext)
        val cm = pref.get_str("cm_settings")
        val phone = pref.get_str("phonenumber")

        input_first = findViewById(R.id.EditText_first)
        input_second = findViewById(R.id.EditText_second)
        input_third = findViewById(R.id.EditText_third)

        txt_first = findViewById(R.id.textView_first)
        txt_second = findViewById(R.id.textView_second)
        txt_third = findViewById(R.id.textView_third)

        txt_first?.visibility = View.GONE
        txt_second?.visibility = View.GONE
        txt_third?.visibility = View.GONE

        val btn = findViewById<Button>(R.id.button_apply)

        if(cm == "SRV" || cm == "UPD"){
            input_first?.hint = getString(R.string.server)
            input_second?.hint = getString(R.string.port)
            input_third?.hint = getString(R.string.connection_interval)
        } else  if (cm == "APN"){
            input_first?.hint = getString(R.string.apn)
            input_second?.hint = getString(R.string.login)
            input_third?.hint = getString(R.string.password)
        }

        btn.setOnClickListener {
            val sms_adapter = SMSgateway(this.baseContext)
            if (this.is_valid(cm)){
                var msg: String = "${input_first?.text}"
                if (input_second?.text!!.isNotEmpty() && input_third?.text!!.isNotEmpty()){
                    msg += ":${input_second?.text}:${input_third?.text}"
                }
                sms_adapter.send_to_sakm(phone, cm, msg)
                this.finish()
            }
//            super.onBackPressed()
        }
    }

    fun is_number(t: String):Boolean{
        val alphabet = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        for (c in t){
            if (!alphabet.contains(c)){
                return false
            }
        }
        return true
    }

    fun is_valid(cm: String): Boolean{
        if (cm == "SRV" || cm == "UPD"){
            if (input_first?.text.toString().isEmpty()){
                txt_first?.text = getString(R.string.required_field)
                txt_first?.visibility = View.VISIBLE
                return false
            } else {
                txt_first?.visibility = View.GONE
            }
            if (input_second?.text.toString().isEmpty()){
                txt_second?.text = getString(R.string.required_field)
                txt_second?.visibility = View.VISIBLE
                return false
            } else {
                txt_second?.visibility = View.GONE
            }
            if (input_third?.text.toString().isEmpty()){
                txt_third?.text = getString(R.string.required_field)
                txt_third?.visibility = View.VISIBLE
                return false
            } else {
                txt_third?.visibility = View.GONE
            }
            val serv = input_first?.text.toString().split('.')
            if (serv.size != 4){
                txt_first?.text = getString(R.string.wrong_ip)
                txt_first?.visibility = View.VISIBLE
                return false
            }
            for (n in serv){
                if (!is_number(n)){
                    txt_first?.text = getString(R.string.wrong_ip)
                    txt_first?.visibility = View.VISIBLE
                    return false
                }
            }
            if (!is_number(input_second?.text.toString())){
                txt_second?.text = getString(R.string.wrong_number)
                txt_second?.visibility = View.VISIBLE
                return false
            }
            if (!is_number(input_third?.text.toString())){
                txt_third?.text = getString(R.string.wrong_number)
                txt_third?.visibility = View.VISIBLE
                return false
            }
        } else if (cm == "APN"){
            if (input_first?.text!!.isEmpty()){
                txt_first?.text = getString(R.string.required_field)
                txt_first?.visibility = View.VISIBLE
                return false
            } else {
                txt_first?.visibility = View.GONE
            }
        }
        return true
    }
}