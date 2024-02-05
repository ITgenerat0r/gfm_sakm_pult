package com.example.senderinmain.objects

import android.util.Log

class Device(private var id: Int) {
//     phone: String = phone
    private val TAG = "Device"

    private var phone: String? = null
    private var second_phone: String? = null
    private var description: String? = null

    fun get_id(): Int{
        return id
    }

    fun set_id(id: Int){
        this.id = id
    }
    fun set_phone(phone: String){
        if (phone != ""){
            this.phone = phone
        }
    }

    fun set_second_phone(phone: String){
        if (phone != "" && phone != this.phone){
            this.second_phone = phone
        }
    }

    fun set_description(description: String?){
        this.description = description
    }

    fun get_phone(): String{
        return "" + this.phone
    }

    fun get_second_phone(): String{
        return "" + this.second_phone
    }

    fun get_description(): String{
        return "" + this.description
    }

    fun to_storage(): String{
        var res: String = "["
        res += "id:" + this.id + ";"
        res += "phone:" + this.phone + ";"
        res += "second:" + this.second_phone + ";"
        res += "descr:" + this.description + ";"
        res += "]"
        Log.d(TAG, "to_storage() = $res")
        return res
    }

    fun from_storage(row: String){
        Log.d(TAG, "from_storage()")
        val list = row.substring(1, row.length - 1).split(';')
        for (item in list){
            Log.d(TAG, item)
            val index: Int = item.indexOf(':')
            if (index > 0){
                val field = item.substring(0, index)
                var value = ""
                if (index < item.length){
                    value = item.substring(index + 1, item.length)
                }
                if (field == "id"){
                    this.set_id(value.toInt())
                } else if (field == "phone"){
                    this.set_phone(value)
                } else if (field == "second"){
                    this.set_second_phone(value)
                } else if (field == "descr"){
                    this.set_description(value)
                }
                Log.d(TAG, "The $field is $value.")
            }

        }
    }

    override fun toString():String{
        return "$id: $phone, $second_phone ($description)"
    }

}