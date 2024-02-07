package com.example.senderinmain.objects

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.telephony.SmsManager
import android.util.Log
import com.example.senderinmain.TAG

class SMSgateway (private var context: Context?) {

    fun checkSMSPermission(): Boolean {
        if (context?.applicationContext?.checkSelfPermission(android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED
            || context?.checkSelfPermission(android.Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED
            || context?.checkSelfPermission(android.Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED
        ) {
//            requestPermissions(
//                arrayOf(android.Manifest.permission.RECEIVE_SMS,
//                    android.Manifest.permission.SEND_SMS,
//                    android.Manifest.permission.READ_SMS), PackageManager.PERMISSION_GRANTED
//            )
            return false;
        }
        return true;
    }

    fun send_to_sakm(phoneNumber: String, command: String, parameters: String = ""){
        val pass = "SAKM"
        var params: String = ""
        if (parameters.isNotEmpty()){
            params = ":$parameters"
        }
        if (phoneNumber.equals("")) {
            Log.d(TAG, "Phone is empty")
            return
        }
        var message: String = command
//        checkSMSPermission()
        if (message.equals("")) {
            message = "$pass:STAT"
        } else {
            message = "$pass:${command.uppercase()}$params"
        }
        sendSms(phoneNumber, message)
    }

    fun sendSms(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
//            val intent = Intent(SENT)
//            val sentIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
            Log.d(TAG, "Send $message to $phoneNumber.")
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        } catch (e: Exception){
            Log.d(TAG, "Can't send, $e")
        }

    }



}