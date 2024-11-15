package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.addBookSheet

import android.app.Application
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mustafin.ebooks.core.data.repositories.booksRepository.BooksRepository
import com.mustafin.ebooks.core.data.source.local.booksDatabase.BookEntity
import com.mustafin.ebooks.core.domain.extensions.getFileName
import com.mustafin.ebooks.core.domain.extensions.toByteArray
import com.mustafin.ebooks.mainFlow.data.repositories.bookInfoRepository.BookInfoRepository
import com.mustafin.ebooks.mainFlow.data.repositories.rulesRepository.RulesRepository
import com.mustafin.ebooks.mainFlow.domain.ContentProcessor
import com.mustafin.ebooks.mainFlow.domain.PdfReader
import com.mustafin.ebooks.mainFlow.domain.models.AddBookViewStatus
import com.mustafin.ebooks.mainFlow.domain.models.BookInfoModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.appmetrica.analytics.AppMetrica
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

// ViewModel для View импорта книги
@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val application: Application,
    private val booksRepository: BooksRepository,
    private val pdfReader: PdfReader,
    private val bookInfoRepository: BookInfoRepository,
    private val rulesRepository: RulesRepository
) : AndroidViewModel(application) {
    var viewStatus by mutableStateOf(AddBookViewStatus.WAITING)

    // Данные о книге, необходимые на этапе обработки и сохранения
    var bookName by mutableStateOf("")
    private var separatedContent = emptyList<String>()

    // Были ли приняты услови использования до
    var wereRulesAcceptedBefore by mutableStateOf(false)

    // Приняты ли условия использования
    var areRulesAccepted: Boolean by mutableStateOf(false)

    fun loadWereRulesAcceptedBefore() {
        viewModelScope.launch {
            wereRulesAcceptedBefore = rulesRepository.getAreAccepted()
        }
    }

    // Базовый празрачный bitmap для превью книги
    private var previewBitmap: Bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888).apply {
        eraseColor(Color.TRANSPARENT)
    }

    // Функция обработки полученного файла
    fun precessData() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Сохраняем данные о пользовательском соглашении
                viewStatus = AddBookViewStatus.LOADING
                rulesRepository.setAreAccepted(true)

                // Сканируем текст книги
                viewStatus = AddBookViewStatus.SCANNING
                val bookContent = pdfReader.extractTextFromPdf(selectedFileUri!!)

                // Обрабатываем текст книги
                viewStatus = AddBookViewStatus.PROCESSING

                // Раздяеляем текст в List<String>
                separatedContent = ContentProcessor.separateContent(bookContent)

                // Получаем основную информацию книги(название) при помощи ИИ
                val bookInfo = try {
                    var firstFragment = ""
                    separatedContent.subList(
                        0, 500.coerceAtMost(separatedContent.size)
                    ).forEach { firstFragment += "$it " }
                    bookInfoRepository.getBookInfoByFragment(firstFragment).second!!
                } catch (e: Exception) {
                    BookInfoModel("")
                }

                AppMetrica.reportEvent(
                    "generated_book_name",
                    "{\"generated_name\":\"${bookInfo.name}\"}"
                )

                // Картинка первой страницы
                selectedFileUri?.let { fileUrl ->
                    pdfReader.extractPreviewFromPdf(fileUrl)?.let { preview ->
                        previewBitmap = preview
                    }
                }

                // Запрашивем у пользователя название книги
                bookName = bookInfo.name
                viewStatus = AddBookViewStatus.REQUEST_BOOK_NAME
            } catch (e: Exception) {
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
            viewStatus = AddBookViewStatus.COMPLETED
        }
    }

    var selectedFileName: String? by mutableStateOf(null)
        private set

    private var selectedFileUri: Uri? by mutableStateOf(null)

    var isFileSelected: Boolean by mutableStateOf(false)
        private set

    fun onFileSelected(uri: Uri) {
        isFileSelected = true
        selectedFileUri = uri
        selectedFileName = uri.getFileName(application)
    }

    // Метод для сброса состояния
    fun resetState() {
        viewStatus = AddBookViewStatus.WAITING
        selectedFileName = null
        isFileSelected = false
        areRulesAccepted = false
    }
}