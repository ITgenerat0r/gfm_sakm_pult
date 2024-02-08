package com.example.senderinmain

import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CalendarView
import android.widget.Switch
import android.widget.TextClock
import android.widget.TextView
import android.widget.TimePicker
import com.example.senderinmain.objects.SMSgateway
import com.example.senderinmain.objects.SharedPreference
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

class CommandTimeSettings : AppCompatActivity() {

    lateinit var sw_time: Switch
    lateinit var calendarView: CalendarView
    lateinit var timeview: TextView
    lateinit var btn_apply: Button



    private val timePickerDialogListener: TimePickerDialog.OnTimeSetListener =
        object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                var h: String = ""
                var m: String = ""
                if (hourOfDay < 10) {
                    h = "0"
                }
                h += hourOfDay.toString()
                if (minute < 10){
                    m = "0"
                }
                m += minute.toString()
                val res: String = "$h:$m"

                timeview.text = res
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_command_time_settings)

        val pref = SharedPreference(context = this.baseContext)
        val cm = pref.get_str("cm_settings")
        val phone = pref.get_str("phonenumber")

        calendarView = findViewById(R.id.calendarView)
        sw_time = findViewById(R.id.switch_time)
        btn_apply = findViewById(R.id.btn_time_apply)
        timeview = findViewById(R.id.textClock)



        sw_time.setOnClickListener {
            if (sw_time.isChecked == true){
                Log.d(TAG, "invisible")
//                calendarView.isEnabled = false
//                calendarView.isActivated = false
                timeview.isEnabled = false

                //to get today date
                val dateTime: Date = Calendar.getInstance().time
//to convert it to Long
                val dateTimeAsLong: Long = dateTime.time
//then make maxdate  and min date as today
//                calendarView.minDate = dateTimeAsLong-1
//                calendarView.maxDate = dateTimeAsLong
            } else {
//                Log.d(TAG, "****")
//                calendarView.minDate = 0
//                Log.d(TAG, "----")
////                calendarView.minDate = Long.MIN_VALUE
//                Log.d(TAG, "----")
//                calendarView.maxDate = Long.MAX_VALUE
//                Log.d(TAG, "++++")
//                calendarView.isEnabled = true
//                calendarView.isActivated = true
                timeview.isEnabled = true
            }
        }

        val calendarInstance = Calendar.getInstance()
        var hour = calendarInstance.get(Calendar.HOUR)
        val minute = calendarInstance.get(Calendar.MINUTE)
        val ampm = if(calendarInstance.get(Calendar.AM_PM)==0) 0 else 12
        hour += ampm
        var h = ""
        var m = ""
        if (hour < 10){
            h = "0"
        }
        h += hour.toString()
        if (minute < 10){
            m = "0"
        }
        m += minute.toString()
        Log.d(TAG, "init time: $h:$m")


        timeview.text = "$h:$m"



//        val btn_selecttime = findViewById<TextView>(R.id.textView_selectTime)
        timeview.setOnClickListener {
            val timePicker: TimePickerDialog = TimePickerDialog(
                this,
                timePickerDialogListener,
                12,
                5,
                false
            )

            timePicker.show()
        }

        btn_apply.setOnClickListener {
            val sms_adapter = SMSgateway(this.baseContext)
            if (sw_time.isChecked == true){
                sms_adapter.send_to_sakm(phone, cm)
            } else {
                val date: Date = Date(calendarView.date)
                val dt = DateFormat.format("yyMMdd", date)
                var tm = timeview.text
                val index_tm = tm.indexOf(":")
                tm = "${tm.removeRange(index_tm, index_tm+1)}00"
//                tm = tm.substring(0, index_tm) + tm.substring(index_tm+1) + "00"
                Log.d(TAG, "time: $dt $tm")
                sms_adapter.send_to_sakm(phone, cm, "$dt$tm")
            }
            this.finish()
        }
    }
}