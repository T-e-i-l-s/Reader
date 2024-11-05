package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafin.ebooks.core.data.repositories.appThemeRepository.AppThemeRepository
import com.mustafin.ebooks.core.data.repositories.booksRepository.BooksRepositoryImpl
import com.mustafin.ebooks.core.data.repositories.daysInRowRepository.DaysInRowRepository
import com.mustafin.ebooks.core.domain.enums.LoadingStatus
import com.mustafin.ebooks.core.domain.enums.Theme
import com.mustafin.ebooks.mainFlow.domain.models.ShortBookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// View Model главного экрана
@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val booksRepository: BooksRepositoryImpl,
    private val daysInRowRepository: DaysInRowRepository,
    private val appThemeRepository: AppThemeRepository
) : ViewModel() {
    var loadingStatus by mutableStateOf(LoadingStatus.LOADING)
        private set

    var books by mutableStateOf<List<ShortBookModel>>(emptyList())
        private set

    var daysInRow by mutableStateOf<Int?>(null)

    var currentTheme by mutableStateOf(Theme.SYSTEM)
    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            currentTheme = appThemeRepository.getTheme()
            daysInRow = daysInRowRepository.getDaysInRowCount()
            books = booksRepository.getBooks()
            loadingStatus = LoadingStatus.LOADED
        }
    }

    // Открыто ли модальное окно импорта книги
    var isAddBookSheetOpened by mutableStateOf(false)
        private set

    fun openAddBookSheet() {
        isAddBookSheetOpened = true
    }

    fun closeAddBookSheet() {
        isAddBookSheetOpened = false
    }

    // Функции и переменные для управления темой приложения
    fun saveNewTheme(theme: Theme) {
        viewModelScope.launch {
            appThemeRepository.setTheme(theme)
            currentTheme = appThemeRepository.getTheme()
        }
    }
}