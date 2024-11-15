package com.mustafin.ebooks.readerFlow.ui.screens.readerScreen.views.bookContentView

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mustafin.ebooks.readerFlow.domain.models.BookModel
import com.mustafin.ebooks.readerFlow.domain.models.ReaderProgressModel

// View блока с текстом книги
@Composable
fun BookContentView(
    book: BookModel,
    readerProgress: ReaderProgressModel,
    modifier: Modifier,
    setReadingProgress: (Float, ReaderProgressModel) -> Unit,
    onSelectWord: (String, String) -> Unit,
) {
    val viewModel: BookContentViewModel = viewModel(
        factory = BookContentViewModelFactory(book)
    )

    val pagerState = rememberPagerState { viewModel.pages.size }

    LaunchedEffect(Unit) {
        if (!viewModel.restored && readerProgress.rendered.size > 1) {
            viewModel.restoreProgress(readerProgress)
            pagerState.scrollToPage(viewModel.pages.size + 1)
        } else {
            viewModel.isPagerReady = true
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        var wordsSum = 0
        for (i in 0..<pagerState.currentPage) {
            wordsSum += viewModel.pages[i].size
        }
        wordsSum -= pagerState.currentPage
        setReadingProgress(
            wordsSum.toFloat() / book.content.size,
            ReaderProgressModel(
                viewModel.pages.subList(0, pagerState.currentPage + 1),
                wordsSum
            )
        )
    }

    HorizontalPager(
        modifier = modifier.alpha(if (!viewModel.isPagerReady) 0f else 1f),
        state = pagerState,
        beyondViewportPageCount = 1,
        flingBehavior = PagerDefaults.flingBehavior(
            state = pagerState,
            snapPositionalThreshold = Spring.DampingRatioMediumBouncy
        )
    ) {
        ContentFlowRow(
            currentPageContent = viewModel.pages[it],
            setLastWordIndex = { index ->
                if (it == viewModel.pages.size - 1) {
                    viewModel.setLastWordIndex(index)
                }
            },
            onSelectWord = onSelectWord
        )
    }
}