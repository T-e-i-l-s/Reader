package com.mustafin.ebooks.mainFlow.ui.screens.allBooksScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustafin.ebooks.R
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT
import com.mustafin.ebooks.mainFlow.ui.views.bookView.BookInfoView

// View экрана с полным списком книг
@Composable
fun AllBooksScreenView(popBackNavigationStack: () -> Unit, openReader: (bookId: Int) -> Unit) {
    val viewModel: AllBooksScreenViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.loadBooks()
    }

    // Верхняя панель навигации
    val navigationRow = @Composable {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.arrow_left_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(27.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { popBackNavigationStack() },
                tint = colorResource(id = R.color.text)
            )

            Spacer(modifier = Modifier.width(6.dp))

            Text(
                text = stringResource(id = R.string.all_books),
                color = colorResource(id = R.color.text),
                fontSize = 24.sp,
                fontFamily = APP_DEFAULT_FONT,
                fontWeight = FontWeight.ExtraBold
            )
        }
    }

    if (viewModel.books.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background))
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.statusBarsPadding())
                navigationRow()
            }

            items(viewModel.books) {
                BookInfoView(
                    book = it,
                    openReader = openReader,
                    isRemovable = true,
                    deleteBook = { viewModel.deleteBookById(it.id) }
                )
            }

            item {
                Spacer(modifier = Modifier.navigationBarsPadding())
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.background))
                .padding(12.dp)
                .statusBarsPadding()
        ) {
            navigationRow()
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = stringResource(id = R.string.empty_books_list),
                    color = colorResource(id = R.color.gray),
                    fontSize = 18.sp,
                    fontFamily = APP_DEFAULT_FONT,
                    fontWeight = FontWeight.Thin,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}