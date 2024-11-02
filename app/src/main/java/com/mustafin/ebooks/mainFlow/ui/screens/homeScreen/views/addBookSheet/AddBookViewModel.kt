package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.addBookSheet

import android.app.Application
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
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
                val separatedContent = ContentProcessor.separateContent(bookContent)
                // Получаем название книги при помощи ИИ
                var firstFragment = ""
                separatedContent.subList(
                    0, 500.coerceAtMost(separatedContent.size)
                ).forEach { firstFragment += "$it " }
                val bookName = bookInfoRepository.getBookInfoByFragment(firstFragment).second

                // Картинка первой страницы
                val previewBitmap = pdfReader.extractPreviewFromPdf(selectedFileUri!!)

                // Добавляем книгу в Room
                viewStatus = AddBookViewStatus.SAVING
                booksRepository.addBook(
                    BookEntity(
                        name = bookName!!.name,
                        preview = previewBitmap.toByteArray(),
                        content = separatedContent
                    )
                )

                viewStatus = AddBookViewStatus.COMPLETED
            } catch (e: Exception) {
                viewStatus = AddBookViewStatus.ERROR
            }
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