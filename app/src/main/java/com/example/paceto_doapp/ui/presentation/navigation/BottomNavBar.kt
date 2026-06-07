package com.example.paceto_doapp.ui.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
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
        BottomNavItem(Screen.Home.route,"Home",Icons.Default.Home),
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
                    // Prevent navigating if we are already explicitly on that route
                    if (currentRoute != item.route) {
                        navController.navigate(route = item.route) {
                            // Pop everything up to the start destination to clear the stack
                            // Using the string route is safer in Compose than using the integer ID
                            val startRoute = navController.graph.findStartDestination().route ?: Screen.Home.route
                            popUpTo(startRoute)
                            // Prevents opening the same screen multiple times
                            launchSingleTop = true
                        // Note: saveState and restoreState are intentionally removed!
                        }
                    } else {
                        // Optional: If they click Home WHILE on Home, you can pop the stack
                        // to clear any child screens (like All Tasks) that might be open
                        navController.popBackStack(Screen.Home.route, inclusive = false)
                    }
                }
            )
        }
    }
}