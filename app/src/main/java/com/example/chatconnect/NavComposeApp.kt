package com.example.chatconnect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.example.chatconnect.nav.Action
import com.example.chatconnect.nav.Destination.AuthenticationOption
import com.example.chatconnect.nav.Destination.Home
import com.example.chatconnect.nav.Destination.Login
import com.example.chatconnect.nav.Destination.Register
import com.example.chatconnect.ui.theme.FlashChatTheme
import com.example.chatconnect.view.AuthenticationView
import com.example.chatconnect.view.home.HomeView
import com.example.chatconnect.view.login.LoginView
import com.example.chatconnect.view.register.RegisterView


@Composable
fun NavComposeApp() {
    val navController = rememberNavController()
    val actions = remember(navController) { Action(navController) }
    FlashChatTheme {
        NavHost(
            navController = navController,
            startDestination =
            if (FirebaseAuth.getInstance().currentUser != null)
                Home
            else
                AuthenticationOption
        ) {
            composable(AuthenticationOption) {
                AuthenticationView(
                    register = actions.register,
                    login = actions.login
                )
            }
            composable(Register) {
                RegisterView(
                    home = actions.home,
                    back = actions.navigateBack
                )
            }
            composable(Login) {
                LoginView(
                    home = actions.home,
                    back = actions.navigateBack
                )
            }
            composable(Home) {
                HomeView()
            }
        }
    }
}