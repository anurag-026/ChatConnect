package com.example.jetchatappcompose.data.database

import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.example.jetchatappcompose.data.model.AppUser
import com.example.jetchatappcompose.data.model.Message
import com.example.jetchatappcompose.data.model.Room
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

fun getUserCollection() : CollectionReference{
    val db = Firebase.firestore
    return  db.collection(AppUser.COLLECTION_NAME)
}

fun getRoomCollection() : CollectionReference{
    val db = Firebase.firestore
    return  db.collection(Room.COLLECTION_NAME)
}
fun getMessageCollection(roomId: String): CollectionReference {
    return  getRoomCollection().document(roomId).collection(Message.COLLECTION_NAME)
}

fun addUserToFireStoreDB(
    appUser: AppUser,
    onSuccessListener: OnSuccessListener<Void>,
    onFailureListener: OnFailureListener
) {
    getUserCollection().document(appUser.id!!)
        .set(appUser)
        .addOnSuccessListener(onSuccessListener)
        .addOnFailureListener(onFailureListener)
}

fun getUserFromFireStoreDB(
    uid: String,
onCompleteListener: OnCompleteListener<DocumentSnapshot>
) {
    getUserCollection().document(uid)
        .get()
        .addOnCompleteListener(onCompleteListener)

}


fun addRoomToFireStore(room : Room , onCompleteListener: OnCompleteListener<Void>){
  val roomDoc =  getRoomCollection().document()
    room.id = roomDoc.id
    roomDoc.set(room)
        .addOnCompleteListener(onCompleteListener)
}

fun getRoomFromFireStore(onCompleteListener: OnCompleteListener<QuerySnapshot>){
    getRoomCollection().get().addOnCompleteListener(onCompleteListener)
}


fun addMessageToRoomFireStore(message : Message , onCompleteListener: OnCompleteListener<Void>){
   val messageDoc = getMessageCollection(message.roomId ?: "").document()
    message.id = messageDoc.id
    messageDoc.set(message)
        .addOnCompleteListener(onCompleteListener)
}




fun getMessage(roomId : String , listener: EventListener<QuerySnapshot>)  {
        getMessageCollection(roomId).orderBy("dateTime")
        .limitToLast(100)
        .addSnapshotListener(listener)
}

