package com.mustafin.ebooks.readerFlow.ui.screens.readerScreen.views.menuView

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafin.ebooks.R
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT
import com.mustafin.ebooks.core.ui.components.SmallButton
import com.mustafin.ebooks.readerFlow.domain.models.BookModel
import java.util.Locale

// View меню с информацией о книге и найтройками ридера
@Composable
fun MenuView(book: BookModel, progress: Float, openHomeScreen: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .navigationBarsPadding()
    ) {
        Text(
            text = book.name,
            color = colorResource(id = R.color.text),
            fontSize = 18.sp,
            fontFamily = APP_DEFAULT_FONT,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp),
            color = colorResource(id = R.color.additional),
            trackColor = colorResource(id = R.color.secondary_background),
            strokeCap = StrokeCap.Round,
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = "${stringResource(id = R.string.have_read)} " +
                    "${String.format(Locale.US, "%.1f", progress * 100)}%",
            color = colorResource(id = R.color.text),
            fontSize = 15.sp,
            fontFamily = APP_DEFAULT_FONT,
            fontWeight = FontWeight.Thin
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row {
//            SmallButton(
//                label = stringResource(id = R.string.show_summarization),
//                background = colorResource(id = R.color.secondary_background),
//                textColor = colorResource(id = R.color.text)
//            ) {}
//
//            Spacer(modifier = Modifier.width(12.dp))

            SmallButton(
                text = stringResource(id = R.string.close_book),
                background = colorResource(id = R.color.additional),
                textColor = colorResource(id = R.color.white),
                onClick = openHomeScreen
            )
        }
    }
}