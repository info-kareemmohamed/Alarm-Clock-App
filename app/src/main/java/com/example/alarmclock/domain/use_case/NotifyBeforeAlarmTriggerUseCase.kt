package com.example.alarmclock.domain.use_case

import android.content.Context
import com.example.alarmclock.core.Constant.NOTIFY_BEFORE_ALARM_TIME
import com.example.alarmclock.data.model.Alarm
import com.example.alarmclock.service.NotifyBeforeAlarmTriggerService.MessageActions
import javax.inject.Inject
import java.util.Calendar

class NotifyBeforeAlarmTriggerUseCase @Inject constructor(private val schedulerAlarmUseCase: SchedulerAlarmUseCase) {
    operator fun invoke(alarm: Alarm, context: Context) {
     schedulerAlarmUseCase.invoke(subtractTwoMinutesFromAlarm(alarm), context,
         MessageActions.Activate.toString())
    }


    private fun subtractTwoMinutesFromAlarm(alarm: Alarm): Alarm {
        val updatedDays = mutableListOf<Int>()
        var updatedHour = alarm.hour
        var updatedMinute = alarm.minute

        // Loop through each day in the alarm's schedule
        alarm.getDaysOfWeek().forEach { day ->
            val calendar = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, alarm.hour)
                set(Calendar.MINUTE, alarm.minute)
                set(Calendar.DAY_OF_WEEK, day)
            }

            // Subtract NOTIFY_BEFORE_ALARM_TIME minutes
            calendar.add(Calendar.MINUTE, -NOTIFY_BEFORE_ALARM_TIME)

            // Get the updated values from the calendar
            updatedHour = calendar.get(Calendar.HOUR_OF_DAY)
            updatedMinute = calendar.get(Calendar.MINUTE)
            updatedDays.add(calendar.get(Calendar.DAY_OF_WEEK))
        }
        // Return a new Alarm object with the updated hour, minute, and days
        return alarm.copy(hour = updatedHour, minute = updatedMinute, days = updatedDays.joinToString(","))
    }


}