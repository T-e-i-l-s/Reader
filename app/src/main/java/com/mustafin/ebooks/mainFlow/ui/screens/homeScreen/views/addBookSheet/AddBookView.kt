package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.addBookSheet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
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
import com.mustafin.ebooks.core.ui.components.CustomTextField
import com.mustafin.ebooks.mainFlow.domain.models.AddBookViewStatus
import com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.acceptRulesView.AcceptRulesView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// View для импорта книг
@OptIn(DelicateCoroutinesApi::class)
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
        reloadBooksList()

        GlobalScope.launch {
            viewModel.loadWereRulesAcceptedBefore()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.background))
            .animateContentSize()
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

                if (viewModel.isFileSelected) {
                    Spacer(modifier = Modifier.height(12.dp))

                    viewModel.selectedFileName?.let {
                        Text(
                            text = it,
                            color = colorResource(id = R.color.text),
                            fontSize = 15.sp,
                            fontFamily = APP_DEFAULT_FONT,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                if (!viewModel.wereRulesAcceptedBefore) {
                    AcceptRulesView(areAccepted = viewModel.areRulesAccepted) {
                        viewModel.areRulesAccepted = it
                    }

                    Spacer(modifier = Modifier.height(12.dp))
                }

                CustomButton(
                    text = stringResource(id = R.string.add_book),
                    background = colorResource(id = R.color.additional),
                    textColor = colorResource(id = R.color.white),
                    enabled = viewModel.isFileSelected && (viewModel.areRulesAccepted || viewModel.wereRulesAcceptedBefore),
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
                CustomTextField(
                    value = viewModel.bookName,
                    onValueChange = { viewModel.bookName = it },
                    placeholder = stringResource(id = R.string.book_name)
                )

                Spacer(modifier = Modifier.height(12.dp))

                CustomButton(
                    text = stringResource(id = R.string.save_book_name),
                    background = colorResource(id = R.color.additional),
                    textColor = colorResource(id = R.color.white),
                    enabled = viewModel.bookName.isNotEmpty(),
                    modifier = Modifier.fillMaxWidth()
                ) { viewModel.saveBook() }
            }

            AddBookViewStatus.WRONG_FORMAT -> {
                Icon(
                    painter = painterResource(id = R.drawable.error_icon),
                    contentDescription = null,
                    tint = colorResource(id = R.color.additional),
                    modifier = Modifier.size(25.dp),
                )

                Spacer(modifier = Modifier.height(4.dp))

                viewModel.viewStatus.label?.let {
                    Text(
                        text = it,
                        color = colorResource(id = R.color.text),
                        fontSize = 18.sp,
                        fontFamily = APP_DEFAULT_FONT
                    )
                }
            }

            else -> {
                CustomProgressIndicator(
                    size = 21.dp,
                    color = colorResource(id = R.color.additional),
                )

                Spacer(modifier = Modifier.height(8.dp))

                viewModel.viewStatus.label?.let {
                    Text(
                        text = it,
                        color = colorResource(id = R.color.text),
                        fontSize = 18.sp,
                        fontFamily = APP_DEFAULT_FONT
                    )
                }
            }
        }
    }
}