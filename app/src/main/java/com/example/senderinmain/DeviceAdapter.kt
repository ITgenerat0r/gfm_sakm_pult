package com.example.senderinmain

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

import com.example.senderinmain.objects.Device
import com.example.senderinmain.objects.SharedPreference
import com.example.senderinmain.objects.Storage

class DeviceAdapter(private var activity: Activity, private var items: ArrayList<Device>):
    BaseAdapter() {

    private class ViewHolder(row: View?){
        var btn_device: Button? = null
        var btn_delete: Button? = null
        var txt_comment: TextView? = null

        init {
            this.btn_device = row?.findViewById(R.id.button_device)
            this.btn_delete = row?.findViewById(R.id.button_delete)
            this.txt_comment = row?.findViewById(R.id.textView_comment)
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
            view = inflater.inflate(R.layout.listitem_device, null)
            viewHolder = ViewHolder(view)
            view?.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        val device = items[position]
        val id = device.get_id()

        viewHolder.btn_device?.text = "${device.get_id()}: ${device.get_phone()}"
        viewHolder.btn_device?.setOnClickListener {
            Log.d(TAG, "onClick(Device $id)")
            val sharedPreference = SharedPreference(activity.baseContext)
            sharedPreference.set_int("device_id", id)

            val intent = Intent(activity.baseContext, DeviceActivity::class.java)
            activity.startActivity(intent)
            // need to run DeviceActivity()
        }

        viewHolder.btn_delete?.setOnClickListener {
            Log.d(TAG, "onClick(Delete $id)")
            val storage = Storage(activity.baseContext)
            storage.load()
            storage.delete_device(id)
            storage.save()
            items = storage.for_adapter()
            this.notifyDataSetChanged()
        }

        viewHolder.txt_comment?.text = device.get_description()

        return view as View
    }


}