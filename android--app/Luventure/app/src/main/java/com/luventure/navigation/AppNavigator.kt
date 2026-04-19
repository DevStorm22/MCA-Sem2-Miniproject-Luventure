package com.luventure.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.luventure.app.ui.auth.login.LoginScreen
import com.luventure.app.ui.auth.register.RegisterScreen
import com.luventure.app.ui.home.HomeScreen
import com.luventure.app.ui.splash.SplashScreen

@Composable
fun AppNavigator() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Splash.route
    ) {

        composable(Routes.Splash.route) {
            SplashScreen(
                onGoLogin = {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(0)
                    }
                },
                onGoHome = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(0)
                    }
                }
            )
        }

        composable(Routes.Login.route) {
            LoginScreen(
                onRegisterClick = {
                    navController.navigate(Routes.Register.route)
                },
                onLoginSuccess = {
                    navController.navigate(Routes.Home.route)
                }
            )
        }

        composable(Routes.Register.route) {
            RegisterScreen(
                onBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.Home.route) {
            HomeScreen()
        }
    }
}