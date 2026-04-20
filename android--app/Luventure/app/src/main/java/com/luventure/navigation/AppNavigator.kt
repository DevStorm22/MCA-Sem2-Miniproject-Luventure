package com.luventure.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.luventure.ui.auth.login.LoginScreen
import com.luventure.app.ui.auth.register.RegisterScreen
import com.luventure.ui.home.HomeScreen
import com.luventure.app.ui.profile.EditProfileScreen
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
                        popUpTo(Routes.Splash.route) {
                            inclusive = true
                        }
                    }
                },
                onGoHome = {
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Splash.route) {
                            inclusive = true
                        }
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
                    navController.navigate(Routes.Home.route) {
                        popUpTo(Routes.Login.route) {
                            inclusive = true
                        }
                    }
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
            HomeScreen(
                onLogout = {
                    navController.navigate(Routes.Login.route) {
                        popUpTo(Routes.Home.route) {
                            inclusive = true
                        }
                    }
                },
                onEditProfile = {
                    navController.navigate(Routes.EditProfile.route)
                }
            )
        }

        composable(Routes.EditProfile.route) {
            EditProfileScreen(
                onSaved = {
                    navController.popBackStack()
                }
            )
        }
    }
}