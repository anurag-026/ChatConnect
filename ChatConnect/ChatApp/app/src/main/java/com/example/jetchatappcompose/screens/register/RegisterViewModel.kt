package com.example.jetchatappcompose.screens.register

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetchatappcompose.data.model.AppUser
import com.example.jetchatappcompose.data.database.addUserToFireStoreDB
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegisterViewModel : ViewModel() {
    val firstNameState = mutableStateOf("")
    val firstNameError = mutableStateOf("")
    val emailState = mutableStateOf("")
    val emailError = mutableStateOf("")
    val passwordState = mutableStateOf("")
    val passwordError = mutableStateOf("")
    val showLoading = mutableStateOf(false)
    val message = mutableStateOf("")
    val showDialog =  mutableStateOf(false)
     private val auth = Firebase.auth

    private fun validateFields(): Boolean {
        if (firstNameState.value.isEmpty() || firstNameState.value.isBlank()) {
            firstNameError.value = "First name required"
            return false
        } else {
            firstNameError.value = ""
        }
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
            registerUserToAuth()

    }

    private fun registerUserToAuth() {
        auth.createUserWithEmailAndPassword(emailState.value, passwordState.value)
            .addOnCompleteListener {
                showLoading.value = false
                if (it.isSuccessful) {
                    addUserToFireStore(it.result.user?.uid)
                } else {
                    //show Error Dialog
                    message.value = it.exception?.localizedMessage ?: ""
                    showDialog.value = true
                    Log.e("TAG", "registerUserToAuth: ${it.exception?.localizedMessage}")
                }
            }
    }


    private fun addUserToFireStore(uid: String?) {
        showLoading.value = true
        addUserToFireStoreDB(
            AppUser(
                id = uid,
                firstName = firstNameState.value,
                email = emailState.value
            ), onSuccessListener = {
                message.value = "Successful Registration"
                showDialog.value = true
                showLoading.value = false
            }, onFailureListener = {
                showLoading.value = false
                message.value = it.localizedMessage ?: ""
                showDialog.value = true
            })
    }


}