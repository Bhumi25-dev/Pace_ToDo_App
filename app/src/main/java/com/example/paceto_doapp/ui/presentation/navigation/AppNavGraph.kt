package com.example.paceto_doapp.ui.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.paceto_doapp.ui.presentation.screens.HomeScreen
import com.example.paceto_doapp.ui.presentation.screens.TaskFormScreen
import com.example.paceto_doapp.ui.presentation.screens.TaskListScreen
import com.example.paceto_doapp.viewModel.HomeScreenVM
import com.example.paceto_doapp.viewModel.TaskFormScreenVM
import com.example.paceto_doapp.viewModel.TaskListScreenVM


@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.padding(innerPadding)
        ) {

            composable(route = Screen.Home.route) {
                val homeVM: HomeScreenVM = hiltViewModel()

                HomeScreen(
                    viewModel = homeVM,
                    onSeeAllClick = { navController.navigate(Screen.TaskListScreen.route) }
                )
            }

            composable(route = Screen.Calendar.route) {
                // CalendarScreen()
            }

            composable(route = Screen.TaskListScreen.route) {
                val taskListVM: TaskListScreenVM = hiltViewModel()

                TaskListScreen(
                    viewModel = taskListVM,
                    onEditClick = { taskId ->
                        navController.navigate(Screen.TaskFormScreen.passId(taskId))
                    },
                    onNavigateBack = { navController.popBackStack() }
                )
            }

            composable(
                route = "${Screen.TaskFormScreen.route}?taskId={taskId}",
                arguments = listOf(
                    navArgument("taskId") {
                        type = NavType.StringType
                        nullable = true
                        defaultValue = null
                    }
                )
            ) {
                val taskFormVM: TaskFormScreenVM = hiltViewModel()

                TaskFormScreen(
                    viewModel = taskFormVM,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}