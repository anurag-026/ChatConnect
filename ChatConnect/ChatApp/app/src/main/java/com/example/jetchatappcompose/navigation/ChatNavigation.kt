package com.example.jetchatappcompose.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jetchatappcompose.screens.add_room.AddRoomScreen
import com.example.jetchatappcompose.screens.add_room.AddRoomViewModel
import com.example.jetchatappcompose.screens.chat_room.ChatRoomScreen
import com.example.jetchatappcompose.screens.chat_room.ChatRoomViewModel
import com.example.jetchatappcompose.screens.home.HomeScreen
import com.example.jetchatappcompose.screens.home.HomeViewModel
import com.example.jetchatappcompose.screens.login.LoginScreen
import com.example.jetchatappcompose.screens.login.LoginViewModel
import com.example.jetchatappcompose.screens.register.RegisterScreen
import com.example.jetchatappcompose.screens.register.RegisterViewModel
import com.example.jetchatappcompose.splash.SplashScreen
import com.example.jetchatappcompose.splash.SplashViewModel

@Composable
fun ChatNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ChatScreens.SplashScreen.name) {

        composable(route = ChatScreens.SplashScreen.name) {
            val viewModel: SplashViewModel = viewModel()
            SplashScreen(viewModel = viewModel, navController = navController)
        }

        composable(route = ChatScreens.RegisterScreen.name) {
            val viewModel: RegisterViewModel = viewModel()
            RegisterScreen(viewModel = viewModel, navController = navController)
        }

        composable(route = ChatScreens.LoginScreen.name) {
            val viewModel: LoginViewModel = viewModel()
            LoginScreen(viewModel = viewModel, navController = navController)
        }

        composable(route = ChatScreens.HomeScreen.name) {
            val viewModel: HomeViewModel = viewModel()
            HomeScreen(viewModel = viewModel, navController = navController)
        }

        composable(route = ChatScreens.AddRoomScreen.name){
            val viewModel : AddRoomViewModel = viewModel()
            AddRoomScreen(viewModel = viewModel, navController = navController)
        }

        composable(route = ChatScreens.ChatRoomScreen.name){
            val viewModel : ChatRoomViewModel = viewModel()
            ChatRoomScreen(viewModel,navController)
        }

    }

}