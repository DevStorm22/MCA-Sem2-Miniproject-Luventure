package com.luventure.navigation

sealed class Routes(val route: String) {
    object Splash : Routes("splash")
    object Login : Routes("login")
    object Register : Routes("register")
    object Home : Routes("home")
    object EditProfile : Routes("edit_profile")
    object Discover : Routes("discover")
}