package com.example.alarmclock.ui

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarmclock.R
import com.example.alarmclock.alarmmanager.AndriodAlarmScheduler
import com.example.alarmclock.databinding.ActivityMainBinding
import com.example.alarmclock.data.entity.Alarm
import com.example.alarmclock.recyclerview.RecyclerAdapter
import com.example.alarmclock.viewmodel.AlarmViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: AlarmViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialRecyclerView()
        setOnClickListenerToFloatingActionButton()
        setupViewModel()
        setStatusBarIcon(true)


    }


    private fun setupViewModel() {
        viewModel = ViewModelProvider(this)[AlarmViewModel::class.java]


        viewModel.getAlarm().observe(this) { alarms ->
            adapter.setList(alarms)
        }


    }


    private fun setDataToAlarmScheduler() {

        AndriodAlarmScheduler(context = applicationContext).scheduler(
            Alarm(
                1,
                "",
                "Monday",
                "03",
                "05",
                true,
                R.drawable.ic_nightlight,
                "AM"
            )
        )


        AndriodAlarmScheduler(context = applicationContext).scheduler(
            Alarm(
                1,
                "",
                "Monday",
                "03",
                "05",
                true,
                R.drawable.ic_nightlight,
                "AM"
            )
        )


    }

    private fun setStatusBarIcon(enabled: Boolean) {
        val alarmChanged = Intent("android.intent.action.ALARM_CHANGED")
        alarmChanged.putExtra("alarmSet", enabled)
        sendBroadcast(alarmChanged)
    }




    fun setOnClickListenerToFloatingActionButton() {
        binding.MainFloatingActionButton.setOnClickListener {
            val intent: Intent = Intent(this, AlarmActivity::class.java)
            startActivity(intent)
        }

    }

    fun initialRecyclerView() {
        binding.MainBottomNavigation.background = null
        binding.MainRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.MainRecyclerView.setHasFixedSize(true)
        adapter = RecyclerAdapter(arrayListOf())
        binding.MainRecyclerView.adapter = adapter
    }
}