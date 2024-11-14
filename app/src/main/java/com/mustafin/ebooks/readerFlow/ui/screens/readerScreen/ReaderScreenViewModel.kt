package com.mustafin.ebooks.readerFlow.ui.screens.readerScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mustafin.ebooks.core.data.repositories.booksRepository.BooksRepository
import com.mustafin.ebooks.core.data.repositories.daysInRowRepository.DaysInRowRepository
import com.mustafin.ebooks.core.data.repositories.lastBookRepository.LastBookRepository
import com.mustafin.ebooks.core.domain.enums.LoadingStatus
import com.mustafin.ebooks.readerFlow.data.repositories.readerProgressRepository.ReaderProgressRepository
import com.mustafin.ebooks.readerFlow.domain.models.BookModel
import com.mustafin.ebooks.readerFlow.domain.models.ReaderProgressModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel читалки
@HiltViewModel
class ReaderScreenViewModel @Inject constructor(
    private val booksRepository: BooksRepository,
    private val readerProgressRepository: ReaderProgressRepository,
    private val lastBookRepository: LastBookRepository,
    private val daysInRowRepository: DaysInRowRepository
) : ViewModel() {
    var loadingStatus by mutableStateOf(LoadingStatus.LOADING)

    // Книга, которая открыта в читалке
    lateinit var book: BookModel

    // Прогресс чтения открытой книги
    lateinit var readerProgress: ReaderProgressModel

    // Открыто ли меню
    var showMenu by mutableStateOf(false)

    private var bookId: Int? = null

    fun setBookId(bookId: Int) {
        this.bookId = bookId
        viewModelScope.launch(Dispatchers.IO) {
            lastBookRepository.setLastBookId(bookId)
            daysInRowRepository.updateDaysInRowCount()
            delay(1000)
            loadData()
        }
    }

    // Функция, которая исполняется преед выходом с экрана
    fun onExitScreen() {
        viewModelScope.launch {
            lastBookRepository.setLastBookId(null)
        }
    }

    // Функция полной загрузки данных
    private suspend fun loadData() = viewModelScope.launch {
        loadingStatus = LoadingStatus.LOADING
        bookId?.let { bookId ->
            val bookDeferred = booksRepository.getBookById(bookId)
            val progressDeferred = readerProgressRepository.getProgress(bookId)

            if (bookDeferred == null) {
                loadingStatus = LoadingStatus.ERROR
            } else {
                book = bookDeferred
                readerProgress = progressDeferred
                loadingStatus = LoadingStatus.LOADED
            }
        }
    }

    // Сохранить страницы, которые были открыты
    fun saveRenderedPages(readerProgress: ReaderProgressModel) {
        CoroutineScope(Dispatchers.IO).launch {
            readerProgressRepository.setProgress(book.id, readerProgress)
        }
    }

    // Открыто ли окно со значением слова
    var showWordMeaning by mutableStateOf(false)
        private set

    // Слово и его контекст, значение которого нужно показать
    var selectedWord by mutableStateOf<String?>(null)
        private set
    var selectedContext by mutableStateOf<String?>(null)
        private set

    fun showWordMeaning(word: String, context: String) {
        selectedWord = word
        selectedContext = context
        showWordMeaning = true
    }

    fun resetSelection() {
        showWordMeaning = false
        selectedWord = null
    }
}