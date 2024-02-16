package com.example.jetchatappcompose.screens.chat_room

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jetchatappcompose.R
import com.example.jetchatappcompose.components.ChatAppBar
import com.example.jetchatappcompose.data.model.Message
import com.example.jetchatappcompose.data.model.Room
import com.example.jetchatappcompose.session.SessionProvider
import java.nio.file.WatchEvent


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ChatRoomScreen(
    viewModel: ChatRoomViewModel,
    navController: NavHostController,
) {
    val room = navController.previousBackStackEntry?.savedStateHandle?.get<Room>("room")
    viewModel.room = room


    Scaffold(
        topBar = {
            ChatAppBar(topAppTitle = room?.title ?: "Chat Room", actions = {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(24.dp),
                    )
                }
            }) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        tint = Color.White,
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = R.drawable.ic_back),
                        contentDescription = ""
                    )
                }
            }
        },

        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paint(
                    painterResource(id = R.drawable.background_pattern),
                    contentScale = ContentScale.FillBounds
                )
                .padding(top = it.calculateTopPadding()) // lazem el tarteb
        ) {

            Card(
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(8.dp), modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {

                    ChatLazyColumn(viewModel)

                SendMessageBox(viewModel = viewModel)

            }
        }
    }


}

@Composable
fun ChatLazyColumn(viewModel: ChatRoomViewModel) {
    val messageList = remember {
        viewModel.listenForMessageInRoom()
        derivedStateOf { viewModel.newMessageState.value }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.85f)
            .padding(8.dp),
        reverseLayout = true
    ) {

        items(messageList.value) { message ->

            MessageWidget(message)
        }

    }
}


@Composable
fun MessageWidget(message: Message) {
    val isSenderOrReceiver = message.senderId == SessionProvider.user?.id

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = if (isSenderOrReceiver) Arrangement.End else Arrangement.Start
    ) {
        Surface(
            Modifier,
            color = if (isSenderOrReceiver) colorResource(id = R.color.blue) else Color.Gray,
            shape = if (isSenderOrReceiver) {
                RoundedCornerShape(

                    topEnd = 16.dp,
                    topStart = 16.dp,
                    bottomStart = 16.dp,

                    )
            } else {
                RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp, bottomEnd = 16.dp)
            }
        ) {
            Column(
                horizontalAlignment = if (isSenderOrReceiver) Alignment.End else Alignment.Start,
                modifier = Modifier.padding(4.dp)
            ) {
                if (!isSenderOrReceiver) Text(
                    modifier = Modifier.padding(
                        start = 4.dp,
                        end = 8.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    ),
                    fontSize = 20.sp,
                    text = message.senderName ?: "",
                    fontWeight = FontWeight.Bold
                ) else Box {}
                Text(
                    modifier = Modifier.padding(start = 4.dp, end = 8.dp),
                    text = message.content ?: "",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Text(
                    modifier = Modifier.padding(
                        start = 4.dp,
                        end = 8.dp,
                        top = 4.dp,
                        bottom = 4.dp
                    ), text = message.formatTime()
                )

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SendMessageBox(viewModel: ChatRoomViewModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
    ) {
        Box(modifier = Modifier.fillMaxWidth(.75f).fillMaxHeight(.8f)) {
            OutlinedTextField(
                value = viewModel.messageFieldState.value,
                onValueChange = { viewModel.messageFieldState.value = it },
                label = { Text(text = "Message") },
                shape = RoundedCornerShape(topEnd = 16.dp),
            )
        }

        Spacer(modifier = Modifier.width(5.dp))
        Button(
            onClick = {
                viewModel.addMessageToFireStore()
            },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
            shape = CircleShape,
            modifier = Modifier

                .align(Alignment.Bottom)
        ) {

            Icon(imageVector = Icons.Default.Send, contentDescription = "Send Icon", modifier = Modifier.fillMaxSize(.8f))

        }
    }
}


