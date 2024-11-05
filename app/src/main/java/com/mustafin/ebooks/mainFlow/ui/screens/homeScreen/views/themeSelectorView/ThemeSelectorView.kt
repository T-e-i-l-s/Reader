package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.themeSelectorView

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafin.ebooks.R
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT
import com.mustafin.ebooks.core.domain.enums.Theme

// View выбора темы в меню ридера
@Composable
fun ThemeSelectorView(currentTheme: Theme, onThemeSelected: (Theme) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorResource(id = R.color.secondary_background))
    ) {
        SingleThemeView(
            icon = painterResource(id = R.drawable.sun_icon),
            title = stringResource(id = R.string.light_theme),
            isSelected = currentTheme == Theme.LIGHT,
            modifier = Modifier.weight(1f),
            onCLick = { onThemeSelected(Theme.LIGHT) }
        )
        SingleThemeView(
            icon = painterResource(id = R.drawable.moon_icon),
            title = stringResource(id = R.string.dark_theme),
            isSelected = currentTheme == Theme.DARK,
            modifier = Modifier.weight(1f),
            onCLick = { onThemeSelected(Theme.DARK) }
        )
        SingleThemeView(
            icon = painterResource(id = R.drawable.settings_icon),
            title = stringResource(id = R.string.system_theme),
            isSelected = currentTheme == Theme.SYSTEM,
            modifier = Modifier.weight(1f),
            onCLick = { onThemeSelected(Theme.SYSTEM) }
        )
    }
}

@Composable
private fun SingleThemeView(
    icon: Painter,
    title: String,
    isSelected: Boolean,
    modifier: Modifier,
    onCLick: () -> Unit
) {
    val contentColor =
        if (isSelected) colorResource(id = R.color.white) else colorResource(id = R.color.text)

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (isSelected) colorResource(id = R.color.additional)
                else Color.Transparent
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onCLick() }
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            modifier = Modifier.size(21.dp),
            tint = contentColor
        )

        Text(
            text = title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = APP_DEFAULT_FONT,
            color = contentColor
        )
    }
}