package com.mustafin.ebooks.mainFlow.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import java.io.InputStream

// Класс для чтения данных из pdf файла
class PdfReader(private val context: Context) {
    // Функция получения изображения первой страницы pdf файла
    fun extractPreviewFromPdf(uri: Uri): Bitmap? {
        context.contentResolver.openFileDescriptor(uri, "r")?.use { fileDescriptor ->
            val pdfRender = PdfRenderer(fileDescriptor)
            // Открываем первую страницу PDF (страницы нумеруются с 0)
            val page = pdfRender.openPage(0)

            // Создаем пустой Bitmap, куда будет рендериться страница
            val width = page.width
            val height = page.height
            val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
            // Рендерим страницу в Bitmap
            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)

            page.close()
            pdfRender.close()

            return bitmap
        }

        return null
    }

    // Функция получения всего текста в pdf файле
    fun extractTextFromPdf(uri: Uri): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
        val reader = PdfReader(inputStream!!)

        val textBuilder = StringBuilder()
        for (i in 1..reader.numberOfPages) {
            val text = PdfTextExtractor.getTextFromPage(reader, i)
            textBuilder.append(text).append("\n")
        }

        return textBuilder.toString().trim()
    }
}