package com.mustafin.ebooks.mainFlow.ui.screens.homeScreen.views.acceptRulesView

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.mustafin.ebooks.R
import com.mustafin.ebooks.core.domain.APP_DEFAULT_FONT
import com.mustafin.ebooks.core.ui.components.CustomCheckbox

// View принятия условий использования и политики конфиденциальности
@Composable
fun AcceptRulesView(areAccepted: Boolean, onAreAcceptedChange: (Boolean) -> Unit) {
    val additionalColor = colorResource(id = R.color.additional)

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomCheckbox(checked = areAccepted, onCheckedChange = onAreAcceptedChange)

        // Создаем строку с гиперссылками внутри
        val annotatedString: AnnotatedString = remember {
            buildAnnotatedString {
                val styleCenter = SpanStyle(
                    color = additionalColor,
                    textDecoration = TextDecoration.Underline
                )

                append("Я принимаю ")

                withLink(LinkAnnotation.Url(url = "https://github.com")) {
                    withStyle(
                        style = styleCenter
                    ) {
                        append("условия использования")
                    }
                }

                append(" и ")


                withLink(LinkAnnotation.Url(url = "https://github.com")) {
                    withStyle(style = styleCenter) {
                        append("политику конфиденциальности")
                    }
                }
            }
        }

        Text(
            text = annotatedString,
            color = colorResource(id = R.color.text),
            fontSize = 15.sp,
            fontFamily = APP_DEFAULT_FONT,
        )
    }
}