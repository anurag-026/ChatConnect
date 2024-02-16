package com.example.jetchatappcompose.splash

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.example.jetchatappcompose.data.database.getUserFromFireStoreDB
import com.example.jetchatappcompose.data.model.AppUser
import com.example.jetchatappcompose.navigation.ChatScreens
import com.example.jetchatappcompose.session.SessionProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SplashViewModel : ViewModel() {

    fun redirect(navController: NavHostController) {
        if(Firebase.auth.currentUser == null){
            navController.navigate(ChatScreens.LoginScreen.name)
            return
        }

        getUserFromFireStoreDB(
Firebase.auth.currentUser?.uid ?:""
        ){task ->
          if(!task.isSuccessful){
              navController.navigate(ChatScreens.LoginScreen.name)
          }
            val user = task.result.toObject(AppUser::class.java)
            SessionProvider.user = user
            navController.navigate(ChatScreens.HomeScreen.name)
        }
    }
}