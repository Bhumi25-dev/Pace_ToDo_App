package com.okay.paceto_doapp.ui.presentation.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.ui.graphics.vector.ImageVector
import com.okay.paceto_doapp.data.local.Category
import com.okay.paceto_doapp.ui.icons.admin
import com.okay.paceto_doapp.ui.icons.connection
import com.okay.paceto_doapp.ui.icons.health
import com.okay.test.focus
import com.okay.test.taskCompleted

fun getCategoryIcon(category: Category): ImageVector{

    return when(category) {
        Category.FOCUS -> focus
        Category.HEALTH -> {health}
        Category.CONNECTIONS -> {connection}
        Category.ADMIN -> {admin}
    }

}

fun getIsCompletedIcon(isCompleted: Boolean): ImageVector{
    return when {
        isCompleted -> taskCompleted
        else -> Icons.Outlined.CheckCircle
    }
}