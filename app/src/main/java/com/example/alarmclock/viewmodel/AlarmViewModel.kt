package com.example.alarmclock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.alarmclock.data.AlarmDatabase
import com.example.alarmclock.data.AlarmRepository
import com.example.alarmclock.data.entity.Alarm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlarmViewModel(application: Application) : AndroidViewModel(application) {
    private val alarmRepository: AlarmRepository

    init {
        alarmRepository =
            AlarmRepository(alarmDao = AlarmDatabase.getDatabace(application).alarmDao())
    }

    fun setAlarm(alarm: Alarm) {
        viewModelScope.launch(Dispatchers.IO) {
            alarmRepository.setAlarm(alarm)

        }

    }

    fun getAlarm(): LiveData<List<Alarm>> = alarmRepository.getAlarm()
    fun getActiveAlarm(): LiveData<List<Alarm>> = alarmRepository.getActiveAlarm()
    fun getNotActiveAlarm(): LiveData<List<Alarm>> = alarmRepository.getNotActiveAlarm()
    fun getAlarm(id: Int): Alarm = alarmRepository.getAlarm(id)

}