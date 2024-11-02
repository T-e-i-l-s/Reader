package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.daysInRowView

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mustafin.ebooks.R
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT

private data class DaysInRowFeatureSetting(
    val daysInRow: Int,
    val background: Painter
)

// View с отображением "Дней в ударе" на главном экране
@Composable
fun DaysInRowView(daysInRow: Int) {
    val daysInRowSettings = listOf(
        DaysInRowFeatureSetting(0, painterResource(id = R.drawable.empty_desert)),
        DaysInRowFeatureSetting(1, painterResource(id = R.drawable.small_house)),
        DaysInRowFeatureSetting(3, painterResource(id = R.drawable.small_castle)),
        DaysInRowFeatureSetting(7, painterResource(id = R.drawable.large_castle)),
        DaysInRowFeatureSetting(14, painterResource(id = R.drawable.modern_city)),
        DaysInRowFeatureSetting(21, painterResource(id = R.drawable.future_city)),
        DaysInRowFeatureSetting(35, painterResource(id = R.drawable.multiplanitary_civilization))
    )

    var currentSetting: DaysInRowFeatureSetting? by remember { mutableStateOf(null) }

    LaunchedEffect(daysInRow) {
        daysInRowSettings.forEachIndexed { index, item ->
            if (item.daysInRow > daysInRow) {
                currentSetting = daysInRowSettings[index - 1]
                return@LaunchedEffect
            }
        }
    }

    if (currentSetting != null) {
        Box(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
                .height(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .paint(
                    currentSetting!!.background,
                    contentScale = ContentScale.FillBounds
                )
                .background(Color.Black.copy(alpha = 0.3f))
                .padding(12.dp)
        ) {
            Column(modifier = Modifier.align(Alignment.BottomStart)) {
                Text(
                    text = "$daysInRow ${
                        when {
                            daysInRow % 100 in 11..19 -> "дней"
                            daysInRow % 10 == 1 -> "день"
                            daysInRow % 10 in 2..4 -> "дня"
                            else -> "дней"
                        }
                    } в ударе!",
                    color = colorResource(id = R.color.white),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    fontFamily = APP_DEFAULT_FONT,
                )

                Text(
                    text = stringResource(id = R.string.daysInRowInstruction),
                    color = colorResource(id = R.color.white),
                    fontWeight = FontWeight.Thin,
                    fontSize = 15.sp,
                    fontFamily = APP_DEFAULT_FONT,
                )
            }
        }
    }
}