package com.mustafin.ebooks.core.data.repositories.booksRepository

import com.mustafin.ebooks.core.data.source.local.booksDatabase.BookEntity
import com.mustafin.ebooks.core.data.source.local.booksDatabase.BooksDatabase
import com.mustafin.ebooks.core.data.source.local.readerProgressDatabase.ReaderProgressDatabase
import com.mustafin.ebooks.core.domain.extensions.toBitmap
import com.mustafin.ebooks.mainFlow.domain.models.ShortBookModel
import com.mustafin.ebooks.readerFlow.domain.models.BookModel
import javax.inject.Inject

// Репозиторий для работы с книгами
class BooksRepositoryImpl @Inject constructor(
    private val booksDatabase: BooksDatabase,
    private val readerProgressDatabase: ReaderProgressDatabase
) : BooksRepository {
    override suspend fun getBooks(): List<ShortBookModel> {
        val books = booksDatabase.booksDao().getBooks()
        return books.map {
            val progress = readerProgressDatabase.booksDao().getProgress(it.id)
            ShortBookModel(
                it.id,
                it.name,
                it.preview.toBitmap(),
                (progress?.lastPageFirstWordIndex ?: 0).toFloat() / it.content.size
            )
        }
    }

    override suspend fun getBookById(bookId: Int): BookModel {
        return booksDatabase.booksDao().getBookById(bookId)!!.toBookModel()
    }

    override suspend fun addBook(book: BookEntity) {
        booksDatabase.booksDao().addBook(book)
    }

    override suspend fun deleteBookById(bookId: Int) {
        booksDatabase.booksDao().deleteBookById(bookId)
    }
}