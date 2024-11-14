package com.mustafin.ebooks.core.data.repositories.booksRepository

import com.mustafin.ebooks.core.data.source.local.booksDatabase.BookEntity
import com.mustafin.ebooks.mainFlow.domain.models.ShortBookModel
import com.mustafin.ebooks.readerFlow.domain.models.BookModel

interface BooksRepository {
    suspend fun getBooks(): List<ShortBookModel>

    suspend fun getBookById(bookId: Int): BookModel?

    suspend fun addBook(book: BookEntity)

    suspend fun deleteBookById(bookId: Int)
}