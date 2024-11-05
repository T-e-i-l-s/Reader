package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.addBookSheet

import android.app.Application
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mustafin.ebooks.core.data.repositories.booksRepository.BooksRepositoryImpl
import com.mustafin.ebooks.core.data.source.local.booksDatabase.BookEntity
import com.mustafin.ebooks.core.domain.extensions.getFileName
import com.mustafin.ebooks.core.domain.extensions.toByteArray
import com.mustafin.ebooks.mainFlow.data.repositories.bookInfoRepository.BookInfoRepository
import com.mustafin.ebooks.mainFlow.domain.ContentProcessor
import com.mustafin.ebooks.mainFlow.domain.PdfReader
import com.mustafin.ebooks.mainFlow.domain.models.AddBookViewStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel для View импорта книги
@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val application: Application,
    private val booksRepository: BooksRepositoryImpl,
    private val pdfReader: PdfReader,
    private val bookInfoRepository: BookInfoRepository
) : AndroidViewModel(application) {
    var viewStatus by mutableStateOf(AddBookViewStatus.WAITING)
    var bookName by mutableStateOf("")
    var separatedContent = emptyList<String>()
    lateinit var previewBitmap: Bitmap

    // Функция обработки полученного файла
    fun precessData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Сканируем текст книги
                viewStatus = AddBookViewStatus.SCANNING
                val bookContent = pdfReader.extractTextFromPdf(selectedFileUri!!)

                // Обрабатываем текст книги
                viewStatus = AddBookViewStatus.PROCESSING
                // Раздяеляем текст в List<String>
                separatedContent = ContentProcessor.separateContent(bookContent)
                // Получаем основную информацию книги(название) при помощи ИИ
                var firstFragment = ""
                separatedContent.subList(
                    0, 500.coerceAtMost(separatedContent.size)
                ).forEach { firstFragment += "$it " }
                val bookInfo = bookInfoRepository.getBookInfoByFragment(firstFragment).second
                // Картинка первой страницы
                previewBitmap = pdfReader.extractPreviewFromPdf(selectedFileUri!!)

                // Запрашивем у пользователя название книги
                bookName = bookInfo!!.name
                viewStatus = AddBookViewStatus.REQUEST_BOOK_NAME
            } catch (e: Exception) {
                println(e)
                viewStatus = AddBookViewStatus.ERROR
            }
        }
    }

    fun saveBook() {
        // Добавляем книгу в Room
        viewModelScope.launch {
            viewStatus = AddBookViewStatus.SAVING
            booksRepository.addBook(
                BookEntity(
                    name = bookName,
                    preview = previewBitmap.toByteArray(),
                    content = separatedContent
                )
            )
        }
    }

    var selectedFileName: String? by mutableStateOf(null)
        private set

    private var selectedFileUri: Uri? by mutableStateOf(null)

    var isSelected: Boolean by mutableStateOf(false)
        private set

    fun onFileSelected(uri: Uri) {
        isSelected = true
        selectedFileUri = uri
        selectedFileName = uri.getFileName(application)
    }

    // Метод для сброса состояния
    fun resetState() {
        viewStatus = AddBookViewStatus.WAITING
        selectedFileName = null
        isSelected = false
    }
}