package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen

import android.app.UiModeManager
import android.content.Context
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustafin.ebooks.R
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT
import com.mustafin.ebooks.core.domain.enums.Theme
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.addBookSheet.AddBookBottomSheetView
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.addBookSheet.AddBookViewModel
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.booksView.BooksView
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.daysInRowView.DaysInRowView
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.optionsView.OptionsListView
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.themeSelectorView.ThemeSelectorView

// Главный экран приложения
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenView(
    openReader: (bookId: Int) -> Unit,
    openAllBooksScreen: () -> Unit,
) {
    val viewModel: HomeScreenViewModel = hiltViewModel()

    val context = LocalContext.current
    val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager

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

                Text(
                    text = stringResource(id = R.string.theme_selector_title),
                    fontSize = 21.sp,
                    fontWeight = FontWeight.SemiBold,
                    fontFamily = APP_DEFAULT_FONT,
                    color = colorResource(id = R.color.text)
                )

                Spacer(modifier = Modifier.height(6.dp))

                ThemeSelectorView(currentTheme = viewModel.currentTheme) { selectedTheme ->
                    viewModel.saveNewTheme(selectedTheme)
                    when (selectedTheme) {
                        Theme.LIGHT -> uiModeManager.nightMode = UiModeManager.MODE_NIGHT_NO
                        Theme.DARK -> uiModeManager.nightMode = UiModeManager.MODE_NIGHT_YES
                        Theme.SYSTEM -> uiModeManager.nightMode = UiModeManager.MODE_NIGHT_AUTO
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                OptionsListView()

                Spacer(modifier = Modifier.navigationBarsPadding())
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