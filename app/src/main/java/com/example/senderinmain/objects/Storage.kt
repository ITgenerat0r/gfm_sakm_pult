package com.example.senderinmain.objects


import android.R.string
import android.content.Context
import android.provider.Telephony.Mms.Part.FILENAME
import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream



class Storage (private var context: Context?) {
    val TAG = "Storage"
//    interface MutableList<Device>: List<Device>, MutableCollection<Device>
//    var devices = mutableListOf<Device>()
    val devices = mutableMapOf<Int, Device>()

    val filename = "devices_storage"


    fun count_devices(): Int{
        return devices.size
    }

    fun add_device(device: Device){
        devices.put(device.get_id(), device)
    }

    fun get_device(key: Int):Device? {
        return devices[key]
    }


    fun delete_device(key: Int){
        devices.remove(key)
    }

    fun save(){
        Log.d(TAG, "save()")
        var data = ""
        var k = false
        for (item in devices){
            if (k){
                data += "\r\n"
            } else {
                k = true
            }
            data += item.value.to_storage()
        }
        Log.d(TAG, data)
        this.write(data)
    }

    fun load(){
        Log.d(TAG, "load()")
        val data = this.read()
        val list = data.split("\r\n")
        for (item in list){
            if (item.length > 0){
                val device: Device = Device(0)
                device.from_storage(item)
                this.add_device(device)
            }
        }
    }

    fun write(data: String?){
        Log.d(TAG, "write($data)")
        try {
//            val file = File(filename)
            val path = context?.getFilesDir()
            val letDirectory = File(path, "LET")
            letDirectory.mkdirs()
            val file = File(letDirectory, filename)
            val os = FileOutputStream(file)
            os.write(data?.toByteArray())
            os.close()


        } catch (e: Exception){
            Log.d(TAG, "write exception: $e")
        }
    }

    fun read(): String {
        Log.d(TAG, "read()")
//        val file = File(context.filesDir, filename)
        try {
//            val file = File(filename)
            val path = context?.getFilesDir()
            val letDirectory = File(path, "LET")
            letDirectory.mkdirs()
            val file = File(letDirectory, filename)
            val fis = FileInputStream(file)
            val data = ByteArray(file.length().toInt())
            fis.read(data)
            fis.close()
            return "" + String(data)
        } catch (e: Exception){
            Log.d(TAG, "read exception: $e")
        }
        return ""
    }

    fun getStorage():MutableMap<Int, Device>{
        return devices
    }

    override fun toString():String{
        var res: String = ""
        var k = false
        for (row in devices){
            if (k) {
                res += "\r\n"
            } else {
                k = true
            }
            res += row.value.toString()
        }
        return res
    }

}