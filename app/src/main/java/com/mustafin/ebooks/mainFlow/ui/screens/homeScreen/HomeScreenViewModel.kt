package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mustafin.ebooks.core.data.repositories.booksRepository.BooksRepositoryImpl
import com.mustafin.ebooks.core.data.repositories.daysInRowRepository.DaysInRowRepository
import com.mustafin.ebooks.core.domain.enums.LoadingStatus
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
    private val daysInRowRepository: DaysInRowRepository
) : ViewModel() {
    var loadingStatus by mutableStateOf(LoadingStatus.LOADING)
        private set

    var books by mutableStateOf<List<ShortBookModel>>(emptyList())
        private set

    var daysInRow by mutableStateOf<Int?>(null)

    fun loadData() {
        CoroutineScope(Dispatchers.IO).launch {
            daysInRow = daysInRowRepository.getDaysInRowCount()
            books = booksRepository.getBooks()
            println("0 " + books)
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
}