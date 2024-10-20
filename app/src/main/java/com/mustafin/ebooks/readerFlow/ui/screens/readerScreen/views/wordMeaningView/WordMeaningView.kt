package com.mustafin.ebooks.readerFlow.ui.screens.readerScreen.views.wordMeaningView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustafin.ebooks.R
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT
import com.mustafin.ebooks.core.domain.enums.LoadingStatus
import com.mustafin.ebooks.core.ui.components.CustomProgressIndicator

// View меню с информацией о книге и найтройками ридера
@Composable
fun WordMeaningView(word: String) {
    val viewModel: WordMeaningViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getWordMeaning(word)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = word,
            color = colorResource(id = R.color.text),
            fontSize = 18.sp,
            fontFamily = APP_DEFAULT_FONT,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        when(viewModel.loadingStatus) {
            LoadingStatus.LOADED -> {
                when (viewModel.wordMeaning) {
                    null -> {
                        Text(
                            text = stringResource(id = R.string.word_meaning_not_found),
                            color = colorResource(id = R.color.text),
                            fontSize = 15.sp,
                            fontFamily = APP_DEFAULT_FONT,
                            fontWeight = FontWeight.Thin,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    else -> {
                        Text(
                            text = viewModel.wordMeaning!!.description,
                            color = colorResource(id = R.color.text),
                            fontSize = 15.sp,
                            fontFamily = APP_DEFAULT_FONT,
                            fontWeight = FontWeight.Thin,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
            LoadingStatus.LOADING -> {
                CustomProgressIndicator(color = colorResource(id = R.color.additional), size = 21.dp)
            }
            LoadingStatus.ERROR -> {
                Text(
                    text = stringResource(id = R.string.loading_error),
                    color = colorResource(id = R.color.text),
                    fontSize = 15.sp,
                    fontFamily = APP_DEFAULT_FONT,
                    fontWeight = FontWeight.Thin,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}