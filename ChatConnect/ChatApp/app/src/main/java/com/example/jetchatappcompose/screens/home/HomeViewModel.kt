package com.example.jetchatappcompose.screens.home

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetchatappcompose.data.database.getRoomFromFireStore
import com.example.jetchatappcompose.data.model.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeViewModel : ViewModel() {
 var roomList = mutableStateOf<List<Room>>(emptyList())

    init {
        getRoomFromFireStoreByViewModel()
    }
    private fun getRoomFromFireStoreByViewModel(){
        getRoomFromFireStore{
            if(it.isSuccessful){
                val roomListFromFireStore = it.result.toObjects(Room::class.java)
                roomList.value = roomListFromFireStore
            }else{
             Log.d("error" , it.exception?.localizedMessage ?: "Something Went  Wrong")
            }
        }
    }

    fun singOutFromFirebase(){
        Firebase.auth.signOut()
    }
}