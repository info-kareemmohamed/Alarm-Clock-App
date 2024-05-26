package com.example.alarmclock.ui

import android.app.Activity
import android.content.Intent
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.alarmclock.R
import com.example.alarmclock.alarmmanager.AndroidAlarmScheduler
import com.example.alarmclock.data.entity.Alarm
import com.example.alarmclock.databinding.ActivityAlarmBinding
import com.example.alarmclock.viewmodel.AlarmViewModel

class AlarmActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlarmBinding
    private lateinit var viewModel: AlarmViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlarmBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]
        setOnClickListenerToBttnSave()

    }


    private fun setOnClickListenerToBttnSave() {
        binding.AlarmAppCompatButtonSave.setOnClickListener {
            val hour: Int
            val minute: Int

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hour = binding.AlarmTimePicker.hour
                minute = binding.AlarmTimePicker.minute

            } else {
                hour = binding.AlarmTimePicker.currentHour
                minute = binding.AlarmTimePicker.currentMinute
            }


            addAlarm(hour, minute, binding.AlarmMessage.text.toString())

            setDataToAlarmScheduler()
            finish()
        }

    }

    private fun setDataToAlarmScheduler() {
        viewModel.getLastAlarm().observe(this) {
            AndroidAlarmScheduler(context = applicationContext).scheduler(
                it
            )
        }


    }

    private fun addAlarm(hour: Int, minute: Int, message: String) {

        viewModel.setAlarm(
            Alarm(
                hour = hour.toString(),
                message = message,
                minute = minute.toString(),
                active = true,
                timePeriod = getTimeSuffix(hour),
                days = "${Calendar.SATURDAY},${Calendar.SUNDAY},${Calendar.MONDAY}",
                modeIcon = getIcon(hour),
                id = 0
            )
        )
    }


    private fun getTimeSuffix(hourOfDay: Int): String {
        return if (hourOfDay < 12) "AM" else "PM"
    }

    private fun getIcon(hourOfDay: Int): Int {
        return if (hourOfDay < 12) R.drawable.ic_sunny else R.drawable.ic_nightlight
    }
}