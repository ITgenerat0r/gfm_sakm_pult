package com.example.senderinmain.objects

import android.content.Context
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

    fun sendSms(phoneNumber: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
        } catch (e: Exception){
            Log.d(TAG, "Can't send, $e")
        }

    }



}