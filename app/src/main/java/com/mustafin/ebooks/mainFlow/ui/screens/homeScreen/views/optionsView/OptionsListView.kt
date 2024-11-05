package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.optionsView

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
    Column(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.secondary_background))
    ) {
        OptionView(
            option = OptionModel(
                R.drawable.support_icon,
                stringResource(id = R.string.support)
            )
        )
        
        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = colorResource(id = R.color.ternary)
        )
        
        OptionView(
            option = OptionModel(
                R.drawable.star_icon,
                stringResource(id = R.string.estimate)
            )
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = colorResource(id = R.color.ternary)
        )

        OptionView(
            option = OptionModel(
                R.drawable.donate_icon,
                stringResource(id = R.string.donate)
            )
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = colorResource(id = R.color.ternary)
        )

        OptionView(
            option = OptionModel(
                R.drawable.document_icon,
                stringResource(id = R.string.terms_of_use)
            )
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = colorResource(id = R.color.ternary)
        )

        OptionView(
            option = OptionModel(
                R.drawable.document_icon,
                stringResource(id = R.string.privacy_policy)
            )
        )

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            thickness = 1.dp,
            color = colorResource(id = R.color.ternary)
        )

        OptionView(
            option = OptionModel(
                R.drawable.about_icon,
                stringResource(id = R.string.about_project)
            )
        )
    }
}