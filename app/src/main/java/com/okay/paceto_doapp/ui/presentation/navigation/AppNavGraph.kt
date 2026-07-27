package com.okay.paceto_doapp.ui.presentation.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.okay.paceto_doapp.ui.presentation.screens.FocusListScreen
import com.okay.paceto_doapp.ui.presentation.screens.HomeScreen
import com.okay.paceto_doapp.ui.presentation.screens.PomodoroScreen
import com.okay.paceto_doapp.ui.presentation.screens.ProfileScreen
import com.okay.paceto_doapp.ui.presentation.screens.TaskFormScreen
import com.okay.paceto_doapp.ui.presentation.screens.TaskListScreen
import com.okay.paceto_doapp.viewModel.FocusListVM
import com.okay.paceto_doapp.viewModel.HomeScreenVM
import com.okay.paceto_doapp.viewModel.PomodoroScreenVM
import com.okay.paceto_doapp.viewModel.TaskFormScreenVM
import com.okay.paceto_doapp.viewModel.TaskListScreenVM

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier
) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomBarDestinations = listOf(
        Screen.Home.route, Screen.FocusList.route, Screen.Calendar.route
    )

    Scaffold(
        // CRITICAL: Set contentWindowInsets to zero so the Scaffold does NOT reserve
        // bottom space for the nav bar. This makes the nav bar truly float over content.
        contentWindowInsets = WindowInsets(0.dp),
        bottomBar = {
            if (bottomBarDestinations.contains(currentRoute))
                BottomNavBar(navController = navController)
        }
    ) { innerPadding ->
        // statusBarsPadding() restores the top status-bar clearance that we lost by zeroing
        // contentWindowInsets. We still ignore innerPadding.bottom so content slides
        // under the floating nav bar instead of being pushed above it.
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = modifier.statusBarsPadding().padding(top = innerPadding.calculateTopPadding())
        ) {
            //Home Screen
            composable(route = Screen.Home.route) {
                val homeVM: HomeScreenVM = hiltViewModel()

                HomeScreen(
                    viewModel = homeVM,
                    onSeeAllClick = { navController.navigate(Screen.TaskListScreen.route) }
                )
            }
            //Calendar Screen
            composable(route = Screen.Calendar.route) {
                // CalendarScreen()
            }
            //Task List Screen
            composable(route = Screen.TaskListScreen.route) {
                val taskListVM: TaskListScreenVM = hiltViewModel()

                TaskListScreen(
                    viewModel = taskListVM,
                    onAddTaskClick = { navController.navigate(Screen.TaskFormScreen.route) },
                    onEditClick = { taskId ->
                        navController.navigate(Screen.TaskFormScreen.passId(taskId))
                    },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
            //Task Form Screen
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
            //Focus List Screen
            composable(route = Screen.FocusList.route) {
                val focusListVM: FocusListVM = hiltViewModel()
                FocusListScreen(
                    viewModel = focusListVM,
                    onRitualCardClick = { clickedTask ->
                        navController.navigate(Screen.PomodoroScreen.passId(clickedTask.focusName))
                    },
                )

            }
            //Pomodoro Screen
            composable(
                route = "${Screen.PomodoroScreen.route}/{focusName}",
                arguments = listOf(
                    navArgument("focusName") {
                        type = NavType.StringType
                        nullable = false
                    }
                )
            ) { navBackStackEntry ->
                val pomodoroVM: PomodoroScreenVM = hiltViewModel()
                // PomodoroScreen()
                PomodoroScreen(
                    viewModel = pomodoroVM,
                    onNavigateBack = { navController.popBackStack() },
                )
            }
            //Profile Screen
            composable(route = Screen.ProfileScreen.route) {
                ProfileScreen()
            }
            //Settings Screen
            composable(route = Screen.SettingsScreen.route) {
                // SettingsScreen()
            }
        }
    }
}