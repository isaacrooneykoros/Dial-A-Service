package com.example.reviewed.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.reviewed.home.HomeScreen
import com.example.reviewed.profile.ProfileScreen
import com.example.reviewed.profile.ProfileViewModel
import com.example.reviewed.splash.SplashScreen
import com.example.reviewed.splash.SplashViewModel


@Composable
fun AppNavHost(navController: NavHostController, startDestination: String = ROUT_SPLASH) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUT_SPLASH) {
            val splashViewModel: SplashViewModel = viewModel()
            SplashScreen(navController, viewModel = splashViewModel)
        }

        composable(ROUT_HOME) {
            HomeScreen(navController)
        }

        composable(ROUT_PROFILE) {
            val profileViewModel: ProfileViewModel = viewModel()
            ProfileScreen(viewModel = profileViewModel)
        }


    }
}