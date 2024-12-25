package com.mustafin.ebooks.mainFlow.domain.models

enum class AddBookViewStatus(val label: String? = null) {
    WAITING,
    LOADING("Готовим книгу"),
    SCANNING("Сканируем книгу"),
    PROCESSING("Обрабатываем текст книги"),
    REQUEST_BOOK_NAME,
    SAVING("Сохраняем данные"),
    WRONG_FORMAT("Содержимое файла недоступно"),
    COMPLETED, ERROR
}