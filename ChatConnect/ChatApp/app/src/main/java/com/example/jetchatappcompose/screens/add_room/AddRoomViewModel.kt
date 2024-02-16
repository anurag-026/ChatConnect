package com.example.jetchatappcompose.screens.add_room

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetchatappcompose.data.database.addRoomToFireStore
import com.example.jetchatappcompose.data.model.Category
import com.example.jetchatappcompose.data.model.Room
import com.example.jetchatappcompose.session.SessionProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AddRoomViewModel : ViewModel() {
    val roomTitleState = mutableStateOf("")
    val roomDescriptionState = mutableStateOf("")
    val roomTitleStateError = mutableStateOf("")
    val roomDescriptionStateError = mutableStateOf("")
    val categories = mutableStateOf(Category.getCategories())
    val selectedItem = mutableStateOf(Category.getCategories()[0])
    val message = mutableStateOf("")
    val isLoading = mutableStateOf(false)
    val showDialog =  mutableStateOf(false)

    fun validateForm() : Boolean{
        if (roomTitleState.value.isEmpty() || roomTitleState.value.isBlank()) {
            roomTitleStateError.value = "Room Title Required"
            return false
        } else
            roomTitleStateError.value = ""
        if (roomDescriptionState.value.isEmpty() || roomDescriptionState.value.isBlank()) {
            roomDescriptionStateError.value = "Room Description Required"
            return false
        } else
            roomDescriptionStateError.value = ""
        return true
    }


    fun addRoomToFireStoreByViewModel(){
        if(!validateForm()) return
        isLoading.value = true
        val room = Room(
            title = roomTitleState.value,
            description = roomDescriptionState.value,
            ownerId = Firebase.auth.currentUser?.uid,
            categoryId = selectedItem.value.id

        )
        addRoomToFireStore(room){
            if(it.isSuccessful){
                isLoading.value = false
                message.value = "Room Created Successfully"
                showDialog.value = true
            }else{
                isLoading.value = false
                message.value = it.exception?.localizedMessage ?: "SomeThing went wrong"
                showDialog.value = true
            }
        }
    }

}
