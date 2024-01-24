package com.example.senderinmain


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.Telephony
import android.telephony.SmsManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

val TAG = "MainActivity"


class MainActivity : AppCompatActivity() {

    fun checkPermission(): Boolean {
        if (checkSelfPermission(android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(android.Manifest.permission.RECEIVE_SMS,
                    android.Manifest.permission.SEND_SMS,
                    android.Manifest.permission.READ_SMS), PackageManager.PERMISSION_GRANTED
            )
            return false;
        }
        return true;
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val br = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                for (sms in Telephony.Sms.Intents.getMessagesFromIntent(
                    p1
                )) {
                    val smsSender = sms.originatingAddress
                    val smsMessageBody = sms.displayMessageBody
                    Log.d(TAG, "From $smsSender received $smsMessageBody")
                    if (smsSender == "+79648455648") {
//                            var binding.textView.text = smsMessageBody
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

        val btn_send = findViewById<Button>(R.id.btn_send)
        val input_phone = findViewById<EditText>(R.id.EditText_phone)
        val input_message = findViewById<EditText>(R.id.EditText_message)

        btn_send.setOnClickListener {
            var phone_number = "" + input_phone.text
            var message = "" + input_message.editableText
            if (phone_number.equals("")) {
                phone_number = "+79648455648"
            }
            if (message.equals("")) {
                message = "Empty message."
            }

            Log.d(TAG, "Send $message to $phone_number.")

            val permission = checkPermission()

            if( permission){
                sendSms(phone_number, message)
            }

        }

        val btn_create_device = findViewById<Button>(R.id.btn_add_device)

        btn_create_device.setOnClickListener{
            val intent = Intent(this, Add_device::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.d(TAG, "onCreateOptionsMenu()")

//        menuInflater.inflate(R.menu.list_devices_menu, menu)

        val inflater = menuInflater
        inflater.inflate(R.menu.list_devices_menu, menu)


//        val menubtn_add_device = menu?.findItem(R.id.id_add_device)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.id_add_device){
            // Launch add device activity
            val intent = Intent(this, Add_device::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }


    fun sendSms(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        } catch (e: Exception){
            Log.d(TAG, "Can't send, $e")
        }

    }
}