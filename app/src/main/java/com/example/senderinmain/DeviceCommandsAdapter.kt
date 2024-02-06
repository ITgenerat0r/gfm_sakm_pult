package com.example.senderinmain

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.senderinmain.objects.Command
import com.example.senderinmain.objects.Device
import com.example.senderinmain.objects.SharedPreference
import com.example.senderinmain.objects.Storage

class DeviceCommandsAdapter(private var activity: Activity, val phone: String):
    BaseAdapter()  {

    val TAG = "DeviceCommandsAdapter"

    private var items = ArrayList<Command>()
    fun init_items(){
        items.add(Command("RST", activity.getString(R.string.rst)))
        items.add(Command("NWELL", activity.getString(R.string.nwell)))
        items.add(Command("SRV", activity.getString(R.string.srv)))
        items.add(Command("UPD", activity.getString(R.string.upd)))
        items.add(Command("APN", activity.getString(R.string.apn)))
        items.add(Command("GPS", activity.getString(R.string.gps)))
        items.add(Command("CLR", activity.getString(R.string.clr)))
        items.add(Command("STAT", activity.getString(R.string.stat)))
        items.add(Command("VAL", activity.getString(R.string.val_cm)))
        items.add(Command("SHOWCFG", activity.getString(R.string.showcfg)))
        items.add(Command("TIME", activity.getString(R.string.time_cm)))
        items.add(Command("REQDATA", activity.getString(R.string.reqdata)))
        items.add(Command("REQUPD", activity.getString(R.string.requpd)))

    }

    private class ViewHolder(row: View?){

        var txt_test: TextView? = null
        init {
            Log.d(TAG, "ViewHolder")
            this.txt_test = row?.findViewById(R.id.txt_listitem_test)
        }
    }
    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null){
            val inflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.listitem_command, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val command = items[position]

//        val id = device.get_id()
//
//        viewHolder.btn_device?.text = "${device.get_id()}: ${device.get_phone()}"
//        viewHolder.btn_device?.setOnClickListener {
//            Log.d(com.example.senderinmain.TAG, "onClick(Device $id)")
//            val sharedPreference = SharedPreference(activity.baseContext)
//            sharedPreference.set_int("device_id", id)
//
//            val intent = Intent(activity.baseContext, DeviceActivity::class.java)
//            activity.startActivity(intent)
//            // need to run DeviceActivity()
//        }
//
//        viewHolder.btn_delete?.setOnClickListener {
//            Log.d(com.example.senderinmain.TAG, "onClick(Delete $id)")
//            val storage = Storage(activity.baseContext)
//            storage.load()
//            storage.delete_device(id)
//            storage.save()
//            items = storage.for_adapter()
//            this.notifyDataSetChanged()
//        }

        viewHolder.txt_test?.text = "$position: ${command.command}, ${command.description}"

        return view as View
    }
}