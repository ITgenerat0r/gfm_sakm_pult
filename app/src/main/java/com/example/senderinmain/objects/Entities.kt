package com.example.senderinmain.objects

import android.content.Context
import android.provider.Settings.Global.getString
import com.example.senderinmain.R

class Entities (private var context: Context){
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

}