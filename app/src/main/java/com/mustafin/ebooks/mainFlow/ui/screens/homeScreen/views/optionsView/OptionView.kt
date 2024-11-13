package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.optionsView

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafin.ebooks.R
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT

// View для отображения какой-либо ссылки
@Composable
fun OptionView(option: OptionModel) {
    val context = LocalContext.current

    Row(
        Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                Toast
                    .makeText(context, "Заглушка", Toast.LENGTH_SHORT)
                    .show()
            }
    ) {
        Icon(
            painter = painterResource(option.res),
            contentDescription = null,
            modifier = Modifier.size(27.dp),
            tint = colorResource(id = R.color.additional)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = option.label,
            color = colorResource(id = R.color.text),
            fontSize = 18.sp,
            fontFamily = APP_DEFAULT_FONT,
        )
    }
}