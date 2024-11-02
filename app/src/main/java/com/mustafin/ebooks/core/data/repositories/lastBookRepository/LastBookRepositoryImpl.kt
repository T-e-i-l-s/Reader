package com.mustafin.ebooks.core.data.repositories.lastBookRepository

import com.mustafin.ebooks.core.data.source.local.lastBookSource.LastBookSource
import javax.inject.Inject

// Репозиторий для хранения индекса открытой книги
class LastBookRepositoryImpl @Inject constructor(
    private val lastBookSource: LastBookSource
) : LastBookRepository {

    override fun getLastBookId(): Int? {
        return lastBookSource.getLastBookId()
    }

    override suspend fun setLastBookId(bookId: Int?) {
        lastBookSource.setLastBookId(bookId)
    }
}