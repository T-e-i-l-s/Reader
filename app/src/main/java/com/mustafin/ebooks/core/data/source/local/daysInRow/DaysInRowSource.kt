package com.mustafin.ebooks.core.data.source.local.daysInRow

import android.content.SharedPreferences
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import javax.inject.Inject

// Класс для работы с кешем функции "Дни в ударе"
class DaysInRowSource @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val daysInRowCellName = "days_in_row_count"
    private val previousDateCellName = "previous_date"

    fun getDaysInRowCount(): Int {
        return sharedPreferences.getInt(daysInRowCellName, 0)
    }

    fun getPreviousDate(): LocalDate? {
        return if (sharedPreferences.contains(previousDateCellName)) {
            val dateString = sharedPreferences.getString(previousDateCellName, "")
            LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        } else {
            null
        }
    }

    fun updateDaysInRowCount(newDaysInRowCount: Int) {
        sharedPreferences.edit().apply {
            putInt(daysInRowCellName, newDaysInRowCount)
            putString(previousDateCellName, LocalDate.now().toString())
            apply()
        }
    }
}