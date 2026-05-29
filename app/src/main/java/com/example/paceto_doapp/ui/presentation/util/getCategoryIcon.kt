package com.example.paceto_doapp.ui.presentation.util

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.paceto_doapp.data.local.Category
import com.example.paceto_doapp.ui.icons.admin
import com.example.paceto_doapp.ui.icons.connection
import com.example.paceto_doapp.ui.icons.health
import com.example.test.focus

fun getCategoryIcon(category: Category): ImageVector{

    return when(category) {
        Category.FOCUS -> focus
        Category.HEALTH -> {health}
        Category.CONNECTIONS -> {connection}
        Category.ADMIN -> {admin}
    }

}