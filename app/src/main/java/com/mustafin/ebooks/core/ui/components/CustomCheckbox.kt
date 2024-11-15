package com.mustafin.ebooks.core.ui.components

import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.mustafin.ebooks.R

@Composable
fun CustomCheckbox(
    checked: Boolean,
    onCheckedChange: ((Boolean) -> Unit)
) {
    Checkbox(
        checked = checked,
        onCheckedChange = onCheckedChange,
        colors = CheckboxDefaults.colors(
            checkedColor = colorResource(id = R.color.additional),
            checkmarkColor = colorResource(id = R.color.white),
            uncheckedColor = colorResource(id = R.color.ternary)
        )
    )
}