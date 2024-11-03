package com.mustafin.ebooks.core.data.source.local.appThemeSource

import android.content.SharedPreferences
import com.mustafin.ebooks.core.domain.enums.Theme
import javax.inject.Inject

// Класс для работы с кешем темы приложения
class AppThemeSource @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val cellName = "theme"

    fun getTheme(): Theme {
        return Theme.valueOf(sharedPreferences.getString(cellName, Theme.SYSTEM.name)!!)
    }

    fun setTheme(newTheme: Theme) {
        sharedPreferences.edit().apply {
            putString(cellName, newTheme.name)
            apply()
        }
    }
}