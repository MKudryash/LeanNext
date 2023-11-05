package com.example.leannext.utlis

import java.util.Calendar
import java.util.Date

class CheckWeek {
    fun PreviousNextWeekModay(privioisWeek: Int): Date {
        return Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
            set(
                Calendar.WEEK_OF_YEAR,
                Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) + privioisWeek
            )
            set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        }.time
    }

    fun PreviousNextWeekSunday(privioisWeek: Int): Date {
        return Calendar.getInstance().apply {
            firstDayOfWeek = Calendar.MONDAY
            set(
                Calendar.WEEK_OF_YEAR,
                Calendar.getInstance().get(Calendar.WEEK_OF_YEAR) + privioisWeek
            )
            set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        }.time
    }
}