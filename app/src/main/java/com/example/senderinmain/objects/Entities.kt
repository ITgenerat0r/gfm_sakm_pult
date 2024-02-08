package com.example.senderinmain.objects

import android.content.Context
import android.provider.Settings.Global.getString
import android.util.Log
import com.example.senderinmain.R

class Entities (private var context: Context){
    val TAG = "Entities"
    val device_loc =  mutableMapOf(
        0 to context.getString(R.string.device_location_unknown),
        1 to context.getString(R.string.device_location_internal),
        2 to context.getString(R.string.device_location_external),
        3 to context.getString(R.string.device_location_flare),
        4 to context.getString(R.string.device_location_prouver),
        5 to context.getString(R.string.device_location_discharge_line),
        6 to context.getString(R.string.device_location_annular_line)
    )

    val device_type = mutableMapOf(
        0 to context.getString(R.string.device_type_test),
        1 to context.getString(R.string.device_type_temp),
        2 to context.getString(R.string.device_type_pressure),
        3 to context.getString(R.string.device_type_humidity),
        4 to context.getString(R.string.device_type_voltmeter),
        5 to context.getString(R.string.device_type_ammeter)
    )

    val keys = mutableMapOf(
        "SRV" to context.getString(R.string.key_srv),
        "UPD" to context.getString(R.string.key_upd),
        "UPTIME" to context.getString(R.string.key_uptime),
        "LIVETIME" to context.getString(R.string.key_livetime),
        "FLASH" to context.getString(R.string.key_flash),
        "LINK" to context.getString(R.string.key_link),
        "UP" to context.getString(R.string.key_up),
        "DOWN" to context.getString(R.string.key_down)
    )

    fun is_number(t: String):Boolean{
        val alphabet = charArrayOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
        for (c in t){
            if (!alphabet.contains(c)){
                return false
            }
        }
        return true
    }

    fun parse_sensor_value(str: String):String{
        val rows = str.split("\n")
        var res: String = ""
        for (row in rows){
            val ind = row.indexOf(':')
            if (ind >= 0) {
                Log.d(TAG, "parse")
                val key = row.substring(0, ind)
                val data = row.substring(ind + 1)
                val values = data.split(',')

                res += "${context.getString(R.string.sensor)} ...$key:\n"
                val loc = device_loc.get(values.get(0).toInt())
                val type = device_type.get(values.get(1).toInt())
                res += " ${context.getString(R.string.location)}: $loc\n"
                res += " ${context.getString(R.string.type)}: $type\n"
                res += " ${context.getString(R.string.status)}: ${values.get(2)}\n"
                res += " ${context.getString(R.string.value)}: ${values.get(3)}\n\n"
            }
        }
        Log.d(TAG, "Result: $res")
        return res
    }

    fun parse_stat(str: String):String{
        val rows = str.split("\n")
        var res: String = ""
        for (row in rows){
            val ind = row.indexOf(':')
            if (ind >= 0) {
                Log.d(TAG, "parse")
                val key = row.substring(0, ind)
                var value = row.substring(ind + 1)
                if (keys.get(key) != null){
                    if (keys.get(value) != null){
                        value = "${keys[value]}"
                    }
                    res += "${keys[key]}: $value\n"
                } else {
                    res += "$row\n"
                }
            }
        }
        Log.d(TAG, "Result: $res")
        return res
    }





}