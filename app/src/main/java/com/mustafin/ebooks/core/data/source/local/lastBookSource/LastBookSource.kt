package com.mustafin.ebooks.core.data.source.local.lastBookSource

import android.content.SharedPreferences
import javax.inject.Inject

// Класс для работы с кешем индекса последней открытой книги
class LastBookSource @Inject constructor(private val sharedPreferences: SharedPreferences) {
    private val cellName = "last_book_id"

    fun getLastBookId(): Int? {
        return if (sharedPreferences.contains(cellName)) {
            sharedPreferences.getInt(cellName, -1)
        } else {
            null
        }
    }

    fun setLastBookId(bookId: Int?) {
        sharedPreferences.edit().apply {
            if (bookId != null) {
                putInt(cellName, bookId)
            } else {
                remove(cellName)
            }
            apply()
        }
    }
}