package com.mustafin.ebooks.core.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafin.ebooks.R
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
) {
    var isFocused by remember { mutableStateOf(false) }

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = colorResource(id = R.color.gray),
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                fontFamily = APP_DEFAULT_FONT
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = colorResource(id = R.color.text),
            focusedTextColor = colorResource(id = R.color.text),
            errorTextColor = colorResource(id = R.color.text),
            cursorColor = colorResource(id = R.color.additional),
            focusedContainerColor = colorResource(id = R.color.secondary_background),
            unfocusedContainerColor = colorResource(id = R.color.secondary_background),
            errorContainerColor = colorResource(id = R.color.secondary_background),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent,
            unfocusedPlaceholderColor = colorResource(id = R.color.gray),
            focusedPlaceholderColor = colorResource(id = R.color.gray),
            errorPlaceholderColor = colorResource(id = R.color.gray)
        ),
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { isFocused = it.isFocused },
        textStyle = TextStyle(
            fontSize = 16.sp,
            fontFamily = APP_DEFAULT_FONT,
            fontWeight = FontWeight.Medium
        ),
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done)
    )
}