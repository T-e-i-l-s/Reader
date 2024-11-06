package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.addBookSheet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mustafin.ebooks.R
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT
import com.mustafin.ebooks.core.ui.components.CustomButton
import com.mustafin.ebooks.core.ui.components.CustomProgressIndicator
import com.mustafin.ebooks.mainFlow.domain.models.AddBookViewStatus

// View для импорта книг
@Composable
fun AddBookBottomSheetView(reloadBooksList: () -> Unit) {
    val viewModel: AddBookViewModel = hiltViewModel()

    // Лаунчер для выбора файла
    val selectFileLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->
        uri?.let {
            // Обработка выбранного файла
            viewModel.onFileSelected(uri)
        }
    }

    LaunchedEffect(viewModel.viewStatus == AddBookViewStatus.COMPLETED) {
        reloadBooksList() // TODO: Поменять логику, чтобы список не обновлялся при первой загрузке
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.background))
            .padding(horizontal = 12.dp)
            .padding(bottom = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        when (viewModel.viewStatus) {
            AddBookViewStatus.WAITING -> {
                SelectFileButtonView {
                    selectFileLauncher.launch(arrayOf("application/pdf"))
                }

                if (viewModel.isSelected) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = viewModel.selectedFileName!!,
                        color = colorResource(id = R.color.text),
                        fontSize = 15.sp,
                        fontFamily = APP_DEFAULT_FONT,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                CustomButton(
                    text = stringResource(id = R.string.add_book),
                    background = colorResource(id = R.color.additional),
                    textColor = colorResource(id = R.color.white),
                    enabled = viewModel.isSelected,
                    modifier = Modifier.fillMaxWidth()
                ) { viewModel.precessData() }
            }

            AddBookViewStatus.ERROR -> {
                Icon(
                    painter = painterResource(id = R.drawable.error_icon),
                    contentDescription = null,
                    tint = colorResource(id = R.color.additional),
                    modifier = Modifier.size(25.dp),
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.book_processing_failed),
                    color = colorResource(id = R.color.text),
                    fontSize = 18.sp,
                    fontFamily = APP_DEFAULT_FONT
                )
            }

            AddBookViewStatus.COMPLETED -> {
                Icon(
                    painter = painterResource(id = R.drawable.check_icon),
                    contentDescription = null,
                    tint = colorResource(id = R.color.additional),
                    modifier = Modifier.size(25.dp),
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = stringResource(id = R.string.book_processing_success),
                    color = colorResource(id = R.color.text),
                    fontSize = 18.sp,
                    fontFamily = APP_DEFAULT_FONT
                )
            }

            AddBookViewStatus.REQUEST_BOOK_NAME -> {
                Text(
                    text = viewModel.bookName,
                    color = colorResource(id = R.color.text),
                    fontSize = 18.sp,
                    fontFamily = APP_DEFAULT_FONT
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomButton(
                    text = stringResource(id = R.string.save_book_name),
                    background = colorResource(id = R.color.additional),
                    textColor = colorResource(id = R.color.white),
                    enabled = viewModel.isSelected,
                    modifier = Modifier.fillMaxWidth()
                ) { viewModel.saveBook() }
            }

            else -> {
                CustomProgressIndicator(
                    size = 21.dp,
                    color = colorResource(id = R.color.additional),
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = viewModel.viewStatus.label!!,
                    color = colorResource(id = R.color.text),
                    fontSize = 18.sp,
                    fontFamily = APP_DEFAULT_FONT
                )
            }
        }
    }
}