package com.mustafin.ebooks.core.data.repositories.lastBookRepository

interface LastBookRepository {
    fun getLastBookId(): Int?
    fun setLastBookId(bookId: Int?)
}