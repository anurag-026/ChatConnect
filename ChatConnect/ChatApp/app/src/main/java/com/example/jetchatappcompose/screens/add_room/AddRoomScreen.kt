package com.example.jetchatappcompose.screens.add_room

import ChatAlertDialog
import LoadingDialog
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.jetchatappcompose.R
import com.example.jetchatappcompose.components.ChatAppBar
import com.example.jetchatappcompose.components.ChatAuthTextField
import com.example.jetchatappcompose.components.ExposedDropdownMenu
import com.example.jetchatappcompose.navigation.ChatScreens

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AddRoomScreen(navController: NavHostController, viewModel: AddRoomViewModel) {
    Scaffold(
        topBar = {
            ChatAppBar(topAppTitle = "CreateRoom"){
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
            Modifier
                .fillMaxWidth()
                .paint(
                    painter = painterResource(id = R.drawable.background_pattern),
                    contentScale = ContentScale.FillBounds
                ),

            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.fillMaxHeight(.10f))
            Card(
                elevation = CardDefaults.cardElevation(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(modifier = Modifier.padding(vertical = 16.dp),
                        text = "Create New Room",
                        style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center,
                    )
                    Image(
                        modifier = Modifier
                            .padding(16.dp)
                            .width(172.dp),
                        painter = painterResource(id = R.drawable.crete_room_pic),
                        contentDescription = "",
                        contentScale = ContentScale.FillWidth
                    )
                    ChatAuthTextField(
                        state = viewModel.roomTitleState,
                        label = "Room Title",
                        errorState = viewModel.roomTitleStateError
                    )
                    ExposedDropdownMenu(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp , top = 24.dp ),
                        menuList = viewModel.categories.value,
                        selectedItem = viewModel.selectedItem
                    )
                    ChatAuthTextField(
                        state = viewModel.roomDescriptionState,
                        label = "Room Description",
                        errorState = viewModel.roomDescriptionStateError
                    )
                    Button(modifier = Modifier.padding(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.blue)),
                        onClick = { viewModel.addRoomToFireStoreByViewModel() }) {
                        Text(
                            text = "Create  Room",
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }

                    if (viewModel.isLoading.value) LoadingDialog()
                    if (viewModel.message.value.isNotEmpty() && viewModel.showDialog.value)
                        ChatAlertDialog(
                            onDismissRequest = { viewModel.showDialog.value = false },
                            alertMessage = {
                                Text(
                                    text = viewModel.message.value,
                                    textAlign = TextAlign.Center,
                                    style = TextStyle(
                                        fontSize = 18.sp,
                                        color = Color.DarkGray,
                                    )
                                )
                            }, dismissButton = {
                                TextButton(onClick = {
                                    viewModel.showDialog.value = false
                                }) { Text(text = "Cancel") }
                            }) {
                            TextButton(onClick = {
                                if (viewModel.message.value == "Room Created Successfully")
                                    navController.navigate(ChatScreens.HomeScreen.name)
                                viewModel.showDialog.value = false
                            }) { Text(text = "OK") }
                        }
                }
            }
        }
    }
}