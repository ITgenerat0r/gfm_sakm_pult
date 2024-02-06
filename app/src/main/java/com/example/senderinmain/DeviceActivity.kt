package com.example.senderinmain

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.senderinmain.objects.Device
import com.example.senderinmain.objects.SharedPreference
import com.example.senderinmain.objects.Storage

import android.provider.Telephony
import android.telephony.SmsManager
import android.widget.ListView
import com.example.senderinmain.objects.Command
import com.example.senderinmain.objects.Entities
import com.example.senderinmain.objects.SMSgateway


class DeviceActivity : AppCompatActivity() {

//    var txt_id: TextView? = null
//    var txt_phone: TextView? = null
//    var txt_description: TextView? = null

    var storage: Storage? = null
    var device: Device? = null
    var sharedPreference: SharedPreference? = null
    var smsGateway: SMSgateway? = null
    var listview_commands: ListView? = null

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG = "DeviceActivity"
        Log.d(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device)


        val txt_id = findViewById<TextView>(R.id.textView_head)
        val txt_phone = findViewById<TextView>(R.id.textView_phone)
        val txt_description = findViewById<TextView>(R.id.textView_description)

        val out_txt = findViewById<TextView>(R.id.editText_output)

        val input_command = findViewById<EditText>(R.id.editText_command)
        val btn_send = findViewById<Button>(R.id.btn_send)

        storage = Storage(this)
        storage!!.load()
        sharedPreference = SharedPreference(this)
        smsGateway = SMSgateway(this)
        smsGateway!!.checkSMSPermission()

        listview_commands = findViewById<ListView>(R.id.listview_commands)
//        val empty_rows = ArrayList<Command>()
//        for (i in 1..10){
//            val cm = Command("number $i")
//            empty_rows.add(cm)
//        }
//        Log.d(TAG, "size rows: ${empty_rows.size}")


        Log.d(TAG, "Getting device...")
        val id: Int = sharedPreference!!.get_int("device_id")
        if (id > 0) {
            Log.d(TAG, "id > 0")
            device = storage!!.get_device(id)
            if (device != null) {
                txt_id.setText(getString(R.string.id) + ": " + device!!.get_id().toString())
                txt_phone.text = "" + getString(R.string.phone) + ": " + device!!.get_phone()
                txt_description.text =
                    "" + getString(R.string.description) + ": " + device!!.get_description()
                val intent = Intent(this, DeviceActivity::class.java)
                val adapter = DeviceCommandsAdapter(this, device!!.get_phone())
                adapter.init_items()
                listview_commands?.adapter = adapter
                adapter.notifyDataSetChanged()
            }
        }

        Log.d(TAG, "Set onClickListener...")
        btn_send.setOnClickListener {
            var phone_number = device?.get_phone()
            var message = "" + input_command.editableText
            if (phone_number.equals("")) {
                Log.d(TAG, "Phone is empty")
                return@setOnClickListener
            }
            if (message.equals("")) {
                message = "SAKM:STAT"
            } else {
                message = "SAKM:${message.uppercase()}"
            }

            Log.d(TAG, "Send $message to $phone_number.")

            val permission = smsGateway!!.checkSMSPermission()

            if( permission){
                out_txt.setText(out_txt.text.toString() + "\r\n Send: \r\n $message")
                smsGateway!!.sendSms(phone_number!!, message)
            } else {
                Log.d(TAG, "Where is permission?")
                out_txt.setText(out_txt.text.toString() + "\r\n Can't send, permission denied.\r\n")
            }
            input_command.setText("")
        }

        Log.d(TAG, "Set up BroadcastReceiver for SMS...")
        val br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                for (sms in Telephony.Sms.Intents.getMessagesFromIntent(
                    p1
                )) {
                    val smsSender = sms.originatingAddress
                    val smsMessageBody = sms.displayMessageBody
                    Log.d(TAG, "From $smsSender received $smsMessageBody")
                    if (smsSender == device?.get_phone() || smsSender == device?.get_second_phone()) {
                        val res: String = out_txt.text.toString() + "\r\n Receive: \r\n" + smsMessageBody
                        out_txt.setText(res)
                        val rows = smsMessageBody.split("\r\n")
                        for (row in rows){
                            Log.d(TAG, "ROW: $row")
                            val ind = row.indexOf(':')
                            if (ind >= 0){
                                Log.d(TAG, "parse")
                                val key = row.substring(0, ind)
                                val value = row.substring(ind+1)
                                Log.d(TAG, "key: $key")
                                Log.d(TAG, "value: $value")
                                if (key == "ID"){
                                    device?.set_id(value.toInt())
                                }
                                val sensor = Entities(applicationContext)
                            } else {
                                Log.d(TAG, "another")
                            }
                        }
                        break
                    }
                }
            }
        }

        registerReceiver(
            br,
            IntentFilter("android.provider.Telephony.SMS_RECEIVED"),
            RECEIVER_EXPORTED
        )
        Log.d(TAG, "Successful run.")
    }

}