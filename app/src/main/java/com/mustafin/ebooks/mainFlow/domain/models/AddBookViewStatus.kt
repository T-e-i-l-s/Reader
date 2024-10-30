package com.mustafin.ebooks.mainFlow.domain.models

enum class AddBookViewStatus(val label: String? = null) {
    WAITING,
    SCANNING("Сканируем книгу"),
    SAVING("Сохраняем данные"),
    COMPLETED, ERROR
}