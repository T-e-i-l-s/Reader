package com.mustafin.ebooks.core.data.repositories.appThemeRepository

import com.mustafin.ebooks.core.domain.enums.Theme

interface AppThemeRepository {
    fun getTheme(): Theme
    suspend fun setTheme(newTheme: Theme)
}