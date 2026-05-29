package com.example.paceto_doapp.ui.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavBar(navController: NavController) {

    val items = listOf(
        BottomNavItem(Screen.TaskListScreen.route,"All Tasks",Icons.Default.List),
        BottomNavItem(Screen.TaskFormScreen.route,"Create Task",Icons.Default.Add)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { item.title },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(route = item.route){
                        // CRITICAL: Prevents a massive backstack of duplicate screens
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true // Prevents opening the same screen 5 times
                        restoreState = true // Remembers where you were on that tab
                    }
                }
            )
        }
    }
}