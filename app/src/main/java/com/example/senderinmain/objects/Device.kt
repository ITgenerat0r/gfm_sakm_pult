package com.example.senderinmain.objects

import android.util.Log

class Device(private val id: Int) {
//     phone: String = phone
    private val TAG = "Storage"

    private var phone: String? = null
    private var second_phone: String? = null
    private var description: String? = null

    fun get_id(): Int{
        return id
    }
    public fun set_phone(phone: String){
        if (phone != ""){
            this.phone = phone
        }
    }

    public fun set_second_phone(phone: String){
        if (phone != "" && phone != this.phone){
            this.second_phone = phone
        }
    }

    public fun set_description(description: String?){
        this.description = description
    }

    public fun get_phone(): String{
        return "" + this.phone
    }

    public fun get_second_phone(): String{
        return "" + this.second_phone
    }

    public fun get_description(): String{
        return "" + this.description
    }

    public fun to_storage(): String{
        var res: String = "["
        res += "phone:" + this.phone + ";"
        res += "second:" + this.second_phone + ";"
        res += "descr:" + this.description + ";"
        res += "]"
        Log.d(TAG, "to_storage() = $res")
        return res
    }

    public fun from_storage(row: String){
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
                Log.d(TAG, "The $field is $value.")
            }

        }
    }

}