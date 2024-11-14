package com.mustafin.ebooks.readerFlow.ui.screens.readerScreen.views.wordMeaningView

import androidx.compose.animation.animateContentSize
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
import com.mustafin.ebooks.core.ui.views.AIMarker

// View меню с информацией о книге и найтройками ридера
@Composable
fun WordMeaningView(word: String, context: String) {
    val viewModel: WordMeaningViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.getWordMeaning(word, context)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .animateContentSize()
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp)
            .navigationBarsPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = word,
            color = colorResource(id = R.color.text),
            fontSize = 21.sp,
            fontFamily = APP_DEFAULT_FONT,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(4.dp))

        when (viewModel.loadingStatus) {
            LoadingStatus.LOADED -> {
                when (viewModel.wordMeaning) {
                    null -> {
                        Text(
                            text = stringResource(id = R.string.word_meaning_not_found),
                            color = colorResource(id = R.color.text),
                            fontSize = 18.sp,
                            fontFamily = APP_DEFAULT_FONT,
                            fontWeight = FontWeight.Thin,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    else -> {
                        viewModel.wordMeaning?.let { safeWordMeaning ->
                            Text(
                                text = safeWordMeaning,
                                color = colorResource(id = R.color.text),
                                fontSize = 18.sp,
                                fontFamily = APP_DEFAULT_FONT,
                                fontWeight = FontWeight.Thin,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        
                        AIMarker()
                    }
                }
            }

            LoadingStatus.LOADING -> {
                CustomProgressIndicator(
                    color = colorResource(id = R.color.additional),
                    size = 18.dp
                )
            }

            LoadingStatus.ERROR -> {
                Text(
                    text = stringResource(id = R.string.loading_error),
                    color = colorResource(id = R.color.text),
                    fontSize = 18.sp,
                    fontFamily = APP_DEFAULT_FONT,
                    fontWeight = FontWeight.Thin,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}