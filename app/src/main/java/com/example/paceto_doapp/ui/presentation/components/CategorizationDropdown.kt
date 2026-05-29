package com.example.paceto_doapp.ui.presentation.components

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.paceto_doapp.data.local.Category
import com.example.paceto_doapp.ui.icons.category

@Composable
fun CategorizationDropdown(
    selectedCategory: Category,
    onCategorySelected: (Category) -> Unit,
    modifier: Modifier = Modifier
) {
    // The dropdown's open/closed state belongs HERE, not in the ViewModel
    var expanded by remember { mutableStateOf(false) }

    Column(modifier = modifier.padding(16.dp)) {
        // 1. Header
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = category,
                contentDescription = "category icon",
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "CATEGORIZATION",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.5.sp
                ),
                modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
            )
        }

        // 2. The Trigger Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(color = MaterialTheme.colorScheme.surfaceVariant)
                .clickable { expanded = true } // Whole box is clickable now
        ){
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp) // Added padding for inner breathing room
            ) {
                Text(
                    text = selectedCategory.name,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expand"
                )
            }
        }

        // 3. The Menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            // Pro-tip: If Category is an Enum, you can iterate through it!
            Category.entries.forEach { category ->
                DropdownMenuItem(
                    text = {
                        // Formats "FOCUS" to "Focus"
                        Text(category.name.lowercase().replaceFirstChar { it.uppercase() })
                    },
                    onClick = {
                        onCategorySelected(category) // Send the event back up
                        expanded = false // Close the menu
                    }
                )
            }
        }
    }
}