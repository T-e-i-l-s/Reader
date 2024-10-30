package com.mustafin.ebooks.core.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT

@Composable
fun SmallButton(
    text: String,
    background: Color,
    textColor: Color,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = background,
            disabledContainerColor = background
        ),
        shape = RoundedCornerShape(50),
        modifier = modifier.alpha(if (!enabled) 0.4f else 1f),
        enabled = enabled,
        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 1.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 15.sp,
            fontFamily = APP_DEFAULT_FONT,
            fontWeight = FontWeight.Thin
        )
    }
}