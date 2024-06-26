package com.example.alarmclock.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.alarmclock.data.alarm.AlarmDatabase
import com.example.alarmclock.repository.AlarmRepository
import com.example.alarmclock.data.alarm.entity.Alarm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class AlarmViewModel(application: Application) : AndroidViewModel(application) {
    private val alarmRepository: AlarmRepository

    init {
        alarmRepository =
            AlarmRepository(alarmDao = AlarmDatabase.getDatabace(application).alarmDao())
    }

    fun setAlarm(alarm: Alarm): Long {

        return runBlocking {
            withContext(Dispatchers.IO) {
                alarmRepository.setAlarm(alarm)
            }
        }

    }

    fun deleteAlarm(alarm: Alarm) {

        viewModelScope.launch(Dispatchers.IO) {
            alarmRepository.deleteAlarm(alarm)
        }
    }


    fun updateAlarm(alarm: Alarm): Int {
        return runBlocking {
            withContext(Dispatchers.IO) {
                alarmRepository.updateAlarm(alarm)
            }
        }
    }

    fun getAlarm(): LiveData<List<Alarm>> = alarmRepository.getAlarm()

    fun getActiveAlarm(): List<Alarm> = alarmRepository.getActiveAlarm()
    fun getNotActiveAlarm(): LiveData<List<Alarm>> = alarmRepository.getNotActiveAlarm()
    fun getLastAlarm(): LiveData<Alarm?> = liveData(Dispatchers.IO) {
        emit(alarmRepository.getLastAlarm())
    }

    fun getAlarm(id: Int): Alarm {
        return runBlocking {
            withContext(Dispatchers.IO) {
                alarmRepository.getAlarm(id)
            }
        }
    }

}