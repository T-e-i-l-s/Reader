package com.mustafin.ebooks.core.data.repositories.lastBookRepository

interface LastBookRepository {
    fun getLastBookId(): Int?
    suspend fun setLastBookId(bookId: Int?)
}