package com.example.senderinmain.objects

import androidx.constraintlayout.widget.ConstraintSet

class Command {
    var phone: String = ""
    var command: String = ""
    var description: String = ""
    var layout_: ConstraintSet.Layout? = null

    constructor()
    constructor(cm: String, descr: String = ""){
        this.command = cm
        this.description = descr
    }




}