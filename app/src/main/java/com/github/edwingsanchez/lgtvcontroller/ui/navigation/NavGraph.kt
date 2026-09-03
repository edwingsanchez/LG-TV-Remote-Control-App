package com.github.edwingsanchez.lgtvcontroller.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.github.edwingsanchez.lgtvcontroller.ui.controller.ControllerScreen
import com.github.edwingsanchez.lgtvcontroller.ui.devicelist.DeviceListScreen
import com.github.edwingsanchez.lgtvcontroller.ui.editor.TvEditScreen
import com.github.edwingsanchez.lgtvcontroller.ui.home.HomeScreen

@Composable
fun ControllerNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = NavDest.DeviceList.route,
        modifier = modifier
    ) {
        composable(NavDest.DeviceList.route) {
            DeviceListScreen(
                navigateToController = {
                    navController.navigate(NavDest.Home.route) {
                        popUpTo(NavDest.DeviceList.route) {
                            inclusive = false
                        }

                        launchSingleTop = true
                    }
                }
            )
        }

        composable(NavDest.Home.route) {
            HomeScreen(
                navigateToDeviceList = {
                    navController.navigate(NavDest.DeviceList.route) {
                        popUpTo(NavDest.Home.route) {
                            inclusive = true
                        }

                        launchSingleTop = true
                    }
                },
                navigateToController = {
                    navController.navigate(NavDest.Controller.route)
                },
                navigateToEditDevice = {
                    navController.navigate("${NavDest.EditDevice.route}/$it")
                },
            )
        }

        composable(NavDest.Controller.route) {
            ControllerScreen(
                navigateUp = {
                    navController.navigate(NavDest.Home.route)
                },
            )
        }

        composable(
            route = NavDest.EditDevice.routeWithArgs,
            arguments = listOf(navArgument(NavDest.EditDevice.tvIdArg) {
                type = NavType.StringType
            }),
        ) {
            TvEditScreen(
                navigateBack = {
                    navController.popBackStack()
                },
            )
        }
    }
}