package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.optionsView

import android.net.Uri

// Модель ссылки на что-либо
data class OptionModel(
    val res: Int,
    val label: String,
    val link: Uri
)
