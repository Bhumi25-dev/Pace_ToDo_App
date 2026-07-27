package com.okay.paceto_doapp.ui.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun <T> GenericFilterBar(
    items : List<T>,
    selectedItem : T?,
    onItemSelect : (T?) -> Unit,
    labelProvider : (T?) -> String,
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
) {

    val completeList = remember(items) { listOf(null) + items }

    LazyRow(
        modifier = modifier,
        contentPadding = contentPadding,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items = completeList) { item ->
            FilterChip(
                selected = selectedItem == item,
                onClick = { onItemSelect(item) },
                label = { Text(text = labelProvider(item)) }
            )
        }
    }

}