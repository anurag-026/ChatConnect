package com.example.jetchatappcompose.screens.chat_room

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.jetchatappcompose.data.database.addMessageToRoomFireStore
import com.example.jetchatappcompose.data.database.getMessage
import com.example.jetchatappcompose.data.database.getMessageCollection
import com.example.jetchatappcompose.data.model.Message
import com.example.jetchatappcompose.data.model.Room
import com.example.jetchatappcompose.session.SessionProvider
import com.google.firebase.firestore.DocumentChange

class ChatRoomViewModel : ViewModel() {
    val messageFieldState = mutableStateOf("")
    val newMessageState = mutableStateOf<List<Message>>(emptyList())


    var room: Room? = null
    fun addMessageToFireStore() {
        if(messageFieldState.value.isEmpty() || messageFieldState.value.isBlank()) return
        val message = Message(
            content = messageFieldState.value,
            senderId = SessionProvider.user?.id,
            senderName = SessionProvider.user?.firstName,
            roomId = room?.id ?: ""
        )
        addMessageToRoomFireStore(message) {
            if (it.isSuccessful) {
                messageFieldState.value = ""

                return@addMessageToRoomFireStore
            }
                Log.e("message", "cant Send Message")

        }
    }


    fun listenForMessageInRoom(){
      getMessage(roomId = room?.id ?: "") {snapShot, error ->
          val newMessage = mutableListOf<Message>()
           snapShot?.documentChanges?.forEach { docChange ->
               when (docChange.type) {
                   DocumentChange.Type.ADDED -> {
                       val message = docChange.document.toObject(Message::class.java)
                       newMessage.add(message)
                   }
                   DocumentChange.Type.MODIFIED -> {

                   }
                   DocumentChange.Type.REMOVED -> {

                   }
               }
           }
          val newList = mutableListOf<Message>()
          newList.addAll(newMessage) // add new messages
          newList.addAll(newMessageState.value) // add old messages
          newMessageState.value = newList

          }

    }
}