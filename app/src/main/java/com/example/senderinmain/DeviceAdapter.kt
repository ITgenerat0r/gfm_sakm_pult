package com.example.senderinmain

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.senderinmain.objects.Device

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

        var device = items[position]
        viewHolder.btn_device?.text = "${device.get_id()}: ${device.get_phone()}"
        viewHolder.txt_comment?.text = device.get_description()

        return view as View
    }

}