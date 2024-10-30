package com.mustafin.ebooks.mainFlow.ui.screens.allBooksScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafin.ebooks.core.data.repositories.booksRepository.BooksRepository
import com.mustafin.ebooks.core.domain.enums.LoadingStatus
import com.mustafin.ebooks.mainFlow.domain.models.ShortBookModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

// ViewModel экрана с полным списком книг
@HiltViewModel
class AllBooksScreenViewModel @Inject constructor(
    private val booksRepository: BooksRepository
) : ViewModel() {
    var loadingStatus by mutableStateOf(LoadingStatus.LOADING)
        private set

    var books by mutableStateOf<List<ShortBookModel>>(emptyList())
        private set

    fun loadBooks() {
        CoroutineScope(Dispatchers.IO).launch {
            loadingStatus = LoadingStatus.LOADING
            books = booksRepository.getBooks()
            loadingStatus = LoadingStatus.LOADED
        }
    }

    fun deleteBookById(bookId: Int) {
        viewModelScope.launch {
            books = books.filter { it.id != bookId }
            withContext(Dispatchers.IO) { booksRepository.deleteBookById(bookId) }
        }
    }
}