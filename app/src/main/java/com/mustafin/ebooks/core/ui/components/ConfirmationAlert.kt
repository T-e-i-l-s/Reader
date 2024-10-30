package com.mustafin.ebooks.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mustafin.ebooks.R
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT

// View с кастомным Alert с двумя кнопками
@Composable
fun ConfirmationAlert(
    title: String,
    text: String,
    confirmButtonText: String,
    denyButtonText: String,
    onConfirm: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(dismissOnClickOutside = true)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(colorResource(id = R.color.background))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                color = colorResource(id = R.color.text),
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                fontFamily = APP_DEFAULT_FONT,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(3.dp))

            Text(
                text = text,
                color = colorResource(id = R.color.gray),
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                fontFamily = APP_DEFAULT_FONT,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            CustomButton(
                text = confirmButtonText,
                background = colorResource(id = R.color.additional),
                textColor = colorResource(id = R.color.white),
                modifier = Modifier.fillMaxWidth()
            ) { onConfirm() }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = denyButtonText,
                color = colorResource(id = R.color.additional),
                fontSize = 18.sp,
                fontFamily = APP_DEFAULT_FONT,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) { onDismissRequest() }
            )
        }
    }
}
