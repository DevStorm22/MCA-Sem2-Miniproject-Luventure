package com.luventure.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luventure.data.local.SessionManager
import com.luventure.ui.auth.login.LoginScreen
import com.luventure.app.ui.auth.register.RegisterScreen
import com.luventure.ui.chat.ChatListScreen
import com.luventure.ui.chat.ChatRoomScreen
import com.luventure.ui.discover.DiscoverScreen
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
                    navController.navigate(
                        Routes.EditProfile.route
                    )
                },

                onOpenChats = {
                    navController.navigate("chats") {
                        launchSingleTop = true
                    }
                },

                onOpenDiscover = {
                    navController.navigate(
                        Routes.Discover.route
                    ) {
                        launchSingleTop = true
                    }
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

        composable("chats") {
            ChatListScreen(
                onOpenChat = { id ->
                    navController.navigate("chatRoom/$id")
                }
            )
        }

        composable(Routes.Discover.route) {
            DiscoverScreen(
                onOpenChat = { id ->
                    navController.navigate("chatRoom/$id")
                }
            )
        }

        composable("chatRoom/{id}") { backStack ->

            val id =
                backStack.arguments
                    ?.getString("id") ?: ""

            val context = LocalContext.current
            val session = SessionManager(context)

            ChatRoomScreen(
                conversationId = id,
                currentUserId =
                    session.getUserId() ?: "",

                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}