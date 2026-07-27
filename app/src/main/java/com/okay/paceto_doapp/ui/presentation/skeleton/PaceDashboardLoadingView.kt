package com.okay.paceto_doapp.ui.presentation.skeleton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PaceDashboardLoadingView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 1. Structural Metric Blocks
        DashboardOverviewSkeleton()

        Spacer(modifier = Modifier.height(8.  dp))

        // 2. Tab filtering rows
        FilterTabsSkeleton()

        Spacer(modifier = Modifier.height(12.dp))

        // 3. Sequential Task List Elements
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            repeat(5) {
                ComplexTaskCardSkeleton()
            }
        }
    }
}