package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.booksView

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafin.ebooks.R
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT
import com.mustafin.ebooks.core.domain.enums.LoadingStatus
import com.mustafin.ebooks.core.ui.components.CustomProgressIndicator
import com.mustafin.ebooks.mainFlow.domain.models.ShortBookModel
import com.mustafin.ebooks.mainFlow.ui.views.bookView.BookInfoView

// View для главного экрана со списком книг
@Composable
fun BooksView(
    loadingStatus: LoadingStatus,
    books: List<ShortBookModel>,
    openAddBookSheet: () -> Unit,
    openReader: (Int) -> Unit,
    openAllBooksScreen: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp)
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.my_books),
            color = colorResource(id = R.color.text),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 24.sp,
            fontFamily = APP_DEFAULT_FONT,
        )

        Icon(
            painter = painterResource(id = R.drawable.plus_icon),
            contentDescription = null,
            tint = colorResource(id = R.color.text),
            modifier = Modifier
                .size(28.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { openAddBookSheet() }
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(254.dp)
            .animateContentSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (loadingStatus == LoadingStatus.LOADED) {
            if (books.isNotEmpty()) {
                val pagerState = rememberPagerState(pageCount = { 5.coerceAtMost(books.size) })
                HorizontalPager(
                    modifier = Modifier.fillMaxWidth(),
                    pageSize = PageSize.Fixed(330.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    pageSpacing = 12.dp,
                    state = pagerState
                ) {
                    BookInfoView(book = books[it], openReader = openReader)
                }
            } else {
                EmptyBooksListView()
            }
        } else if (loadingStatus == LoadingStatus.LOADING) {
            CustomProgressIndicator(
                size = 21.dp,
                color = colorResource(id = R.color.additional),
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(id = R.string.loading_books),
                color = colorResource(id = R.color.text),
                fontSize = 18.sp,
                fontFamily = APP_DEFAULT_FONT,
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.all_books),
            color = colorResource(id = R.color.gray),
            fontSize = 18.sp,
            fontFamily = APP_DEFAULT_FONT,
            modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { openAllBooksScreen() }
        )

        Icon(
            painter = painterResource(id = R.drawable.arrow_right_icon),
            contentDescription = null,
            tint = colorResource(id = R.color.gray),
            modifier = Modifier.size(21.dp)
        )
    }
}