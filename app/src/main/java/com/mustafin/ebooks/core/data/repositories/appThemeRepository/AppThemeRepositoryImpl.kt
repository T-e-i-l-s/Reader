package com.mustafin.ebooks.core.data.repositories.appThemeRepository

import com.mustafin.ebooks.core.data.source.local.appThemeSource.AppThemeSource
import com.mustafin.ebooks.core.domain.enums.Theme
import javax.inject.Inject

// Репозиторий для работы с темой приложения
class AppThemeRepositoryImpl @Inject constructor(private val appThemeSource: AppThemeSource): AppThemeRepository {
    override fun getTheme(): Theme {
        return  Theme.DARK //appThemeSource.getTheme()
    }

    override suspend fun setTheme(newTheme: Theme) {
        return appThemeSource.setTheme(newTheme)
    }
}