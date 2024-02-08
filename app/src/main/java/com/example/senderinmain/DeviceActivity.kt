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

        lateinit var adapter: DeviceCommandsAdapter

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
                adapter = DeviceCommandsAdapter(this, device!!.get_phone())
                adapter.init_items()
                listview_commands?.adapter = adapter
                Log.d(TAG, "Before setonclick")
                listview_commands?.setOnItemClickListener { parent, view, position, id ->
                    Log.d(TAG, "pos: $position")
                    val cm = adapter.getItem(position)
                    if (cm.redirect_activity > 0) {
                        val pref = SharedPreference(this.baseContext)
                        pref.set_str("cm_settings", cm.command)
                        pref.set_str("phonenumber", adapter.phone)
                        startActivity(cm.intent)
                    } else {
                        val permission = smsGateway!!.checkSMSPermission()
                        if (permission) {
                            out_txt.setText(out_txt.text.toString() + "\r\n Send: \r\n SAKM:${cm.command.uppercase()}")
                            smsGateway!!.send_to_sakm(adapter.phone, cm.command)
                        } else {
                            Log.d(TAG, "Where is permission?")
                            out_txt.setText(out_txt.text.toString() + "\r\n Can't send, permission denied.\r\n")
                        }
                    }

                }
                Log.d(TAG, "After setonclick")
                adapter.notifyDataSetChanged()
            }


            Log.d(TAG, "Set onClickListener...")
            btn_send.setOnClickListener {
                val phone_number = device?.get_phone()
                val message = "" + input_command.editableText
                val permission = smsGateway!!.checkSMSPermission()
                if (permission) {
                    out_txt.setText(out_txt.text.toString() + "\r\n Send: \r\n $message")
                    smsGateway!!.send_to_sakm(phone_number!!, message)
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
                            val res: String =
                                out_txt.text.toString() + "\r\n Receive: \n" + smsMessageBody
                            out_txt.setText(res)
                            val sensor = Entities(applicationContext)
                            val ind = smsMessageBody.indexOf(':')
                            if (ind >= 0) {
                                Log.d(TAG, "parse")
                                val key = smsMessageBody.substring(0, ind)
                                val val_ind = smsMessageBody.indexOf("\n")
                                var value: String = device!!.get_id().toString()
                                if (val_ind >= 0){
                                    value = smsMessageBody.substring(ind + 1, val_ind)
                                }
                                Log.d(TAG, "key: $key")
                                Log.d(TAG, "value: $value")
                                if (key == "ID") {
                                    storage!!.get_device(device!!.get_id())?.set_id(value.toInt())
                                    storage!!.save()
                                    device?.set_id(value.toInt())
                                    txt_id.setText(getString(R.string.id) + ": " + device!!.get_id().toString())
                                    adapter.set_text_by_command("STAT", sensor.parse_stat(smsMessageBody))
                                    break
                                } else if (key == "SRV"){
                                    adapter.set_text_by_command("SHOWCFG", sensor.parse_stat(smsMessageBody))
                                    break
                                } else {
                                    adapter.set_text_by_command("VAL", sensor.parse_sensor_value(smsMessageBody))
                                }
                                Log.d(TAG, "settle")
                            } else {
                                Log.d(TAG, "another")
                                adapter.set_text_by_command("GPS", "${getString(R.string.coords)}: ${smsMessageBody}")
                            }
                            adapter.notifyDataSetChanged()
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

}