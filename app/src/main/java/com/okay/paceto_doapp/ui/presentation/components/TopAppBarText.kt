package com.okay.paceto_doapp.ui.presentation.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun TopAppBarText(
    introText: String,
    endText: String?
) {

    val isUserName: Boolean = endText == null

    // Two-tone Welcome Text
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.ExtraBold
                )
            ) {
                append(introText)
            }
            withStyle(
                style = SpanStyle(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.ExtraBold
                )
            ) {
                if (isUserName) {
                    CircularProgressIndicator()
                } else {
                    append(endText)
                }
            }
        },
        fontSize = 20.sp
    )


}