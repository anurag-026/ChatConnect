package com.example.jetchatappcompose.screens.login

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetchatappcompose.data.model.AppUser
import com.example.jetchatappcompose.data.database.getUserFromFireStoreDB
import com.example.jetchatappcompose.session.SessionProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginViewModel : ViewModel() {
    val emailState = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val showLoading = mutableStateOf(false)
    val showDialog =  mutableStateOf(false)
    val message = mutableStateOf("")
    private val auth = Firebase.auth

    private fun validateFields(): Boolean {
        if (emailState.value.isEmpty() || emailState.value.isBlank()) {
            emailError.value = "Email Required"
            return false
        } else
            emailError.value = ""
        if (passwordState.value.isEmpty() || passwordState.value.isBlank()) {
            passwordError.value = "Password Required"
            return false
        } else
            passwordError.value = ""
        return true
    }

    fun sendAuthDataToFirebase() {
        if (!validateFields()) return
            showLoading.value = true
            login()

    }

    private fun login() {
        auth.signInWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener {
                showLoading.value = false
                if (it.isSuccessful) {
                    getUser(it.result.user?.uid)
                } else {
                    //show Error Dialog
                    message.value = it.exception?.localizedMessage ?: ""
                    showDialog.value = true
                    Log.e("TAG", "registerUserToAuth: ${it.exception?.localizedMessage}")
                }
            }
    }

    private fun getUser(uid: String?) {
        showLoading.value = true
        getUserFromFireStoreDB(
            uid!!) { task ->
           if(task.isSuccessful){
               val appUser = task.result.toObject(AppUser::class.java)
               SessionProvider.user = appUser
               message.value = "Login Successfully"
               showDialog.value = true
               showLoading.value = false
           }else{

               showLoading.value = false
               message.value = task.exception?.localizedMessage ?: ""
               showDialog.value = true
           }
        }
    }


}




