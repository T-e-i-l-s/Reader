package com.mustafin.ebooks.readerFlow.ui.screens.readerScreen.views.bookContentView

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.mustafin.ebooks.readerFlow.domain.models.BookModel
import com.mustafin.ebooks.readerFlow.domain.models.ReaderProgressModel

// ViewModel блока с текстом книги
class BookContentViewModel(private val book: BookModel) : ViewModel() {
    var isPagerReady by mutableStateOf(false)

    // Индексы слов в массиве, которые сейчас открыты
    private var firstWordIndex by mutableIntStateOf(0)
    private var lastWordIndex by mutableStateOf<Int?>(null)

    // Сеттер для индекса последнего слова на странице в массиве
    fun setLastWordIndex(index: Int) {
        lastWordIndex = index
        pages[pages.size - 1] = book.content.subList(
            firstWordIndex,
            firstWordIndex + index + 1
        )
        generateNextPage()
    }

    // Контент каждой из страниц
    var pages = mutableStateListOf<List<String>>()
        private set

    init {
        // При создании ViewModel загружаем страницу
        loadContent()
    }

    // Функция загрузки контента страницы
    private fun loadContent() {
        pages.add(
            book.content.subList(
                firstWordIndex,
                (firstWordIndex + 1000).coerceAtMost(book.content.size)
            )
        )
    }

    // Перелистывание на следующую страницу
    private fun generateNextPage() {
        lastWordIndex?.let { safeLastWordIndex ->
            if (firstWordIndex + safeLastWordIndex < book.content.size - 1) {
                // Устанавливаем новые индексы
                firstWordIndex += safeLastWordIndex
                lastWordIndex = null
                // Обновляем контент на странице
                loadContent()
            }
        }
    }

    var restored by mutableStateOf(false)
        private set

    // Функция восстановления страницы
    fun restoreProgress(readerProgressModel: ReaderProgressModel) {
        firstWordIndex = readerProgressModel.lastPageFirstWordIndex
        lastWordIndex = null
        pages.clear()
        pages.addAll(readerProgressModel.rendered)
        restored = true
        isPagerReady = true
    }
}