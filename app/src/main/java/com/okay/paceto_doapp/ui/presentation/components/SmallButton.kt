package com.okay.paceto_doapp.ui.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun SmallButton(
    icon: ImageVector,
    iconTitle: String,
    onClick: () -> Unit
) {
    Row (
        modifier = Modifier.clickable(
            onClick = { onClick() }
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ){
        Icon(
            imageVector = icon,
            contentDescription = iconTitle,
        )
        Text(
            text = iconTitle,
        )
    }
}