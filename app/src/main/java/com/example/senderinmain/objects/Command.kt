package com.example.senderinmain.objects

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.constraintlayout.widget.ConstraintSet
import com.example.senderinmain.Add_device
import com.example.senderinmain.CommandServerSettings
import com.example.senderinmain.R

class Command(val context: Context) {
//    var phone: String = ""
    var command: String = ""
    var description: String = ""
//    var layout_: ConstraintSet.Layout? = null
    val activity: Int = R.layout.listitem_command
    var redirect_activity: Int = 0
    var intent = Intent(this.context, CommandServerSettings::class.java)

    constructor(context: Context, cm: String, descr: String = "") : this(context) {
        this.command = cm
        this.description = descr
    }

//    fun set_activity(activity: Int){
//        this.activity = activity
//    }




}