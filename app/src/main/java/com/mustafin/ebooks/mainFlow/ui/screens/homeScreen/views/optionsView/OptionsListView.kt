package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.optionsView

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mustafin.ebooks.R

// View с различными ссылками
@Composable
fun OptionsListView() {
    // Список со всеми ссылками
    val options = listOf(
        OptionModel(
            R.drawable.support_icon,
            stringResource(id = R.string.support),
            Uri.parse("https://t.me/reader_support")
        ),
        OptionModel(
            R.drawable.document_icon,
            stringResource(id = R.string.terms_of_use),
            Uri.parse("http://reader-app.ru/terms.html")
        ),
        OptionModel(
            R.drawable.document_icon,
            stringResource(id = R.string.privacy_policy),
            Uri.parse("http://reader-app.ru/privacy.html")
        )
    )

    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.secondary_background))
    ) {
        options.forEachIndexed { index, option ->
            OptionView(option = option)

            if (index != options.size-1) {
                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = colorResource(id = R.color.ternary)
                )
            }
        }
    }
}