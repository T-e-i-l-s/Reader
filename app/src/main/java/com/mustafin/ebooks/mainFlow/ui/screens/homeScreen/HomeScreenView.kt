package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustafin.ebooks.R
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.addBookSheet.AddBookBottomSheetView
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.addBookSheet.AddBookViewModel
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.booksView.BooksView
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.daysInRowView.DaysInRowView
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.optionsView.OptionsListView

// Главный экран приложения
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenView(
    openReader: (bookId: Int) -> Unit,
    openAllBooksScreen: () -> Unit,
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModel.loadData()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.background)),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Spacer(modifier = Modifier.statusBarsPadding())

            BooksView(
                loadingStatus = viewModel.loadingStatus,
                books = viewModel.books,
                openAddBookSheet = { viewModel.openAddBookSheet() },
                openReader = openReader,
                openAllBooksScreen = openAllBooksScreen
            )


            Column(Modifier.padding(horizontal = 12.dp)) {
                Spacer(modifier = Modifier.height(12.dp))

                DaysInRowView(daysInRow = viewModel.daysInRow)

                Spacer(modifier = Modifier.height(12.dp))

                OptionsListView()

                Spacer(
                    modifier = Modifier
                    .padding(bottom = 12.dp)
                    .navigationBarsPadding()
                )
            }
        }
    }

    if (viewModel.isAddBookSheetOpened) {
        // Всплывающее снизу модальное окно для импорта книги

        // ViewModel этого всплывающего окна
        val addBookViewModel: AddBookViewModel = hiltViewModel()

        ModalBottomSheet(
            onDismissRequest = {
                viewModel.closeAddBookSheet()
                addBookViewModel.resetState()
            },
            containerColor = colorResource(id = R.color.background),
            windowInsets = WindowInsets(
                0,
                0,
                0,
                WindowInsets.navigationBars.getBottom(LocalDensity.current)
            )
        ) {
            AddBookBottomSheetView(reloadBooksList = { viewModel.loadData() })
        }
    }
}