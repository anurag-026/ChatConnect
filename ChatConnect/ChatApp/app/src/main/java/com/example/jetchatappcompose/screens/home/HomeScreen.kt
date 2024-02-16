package com.example.jetchatappcompose.screens.home

import ChatCard
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.jetchatappcompose.navigation.ChatScreens


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavHostController) {
    Scaffold(
        topBar = {
            ChatAppBar(topAppTitle = "Home", actions = {
                IconButton(onClick = {
                    viewModel.singOutFromFirebase()
                    navController.popBackStack()
                    navController.navigate(ChatScreens.LoginScreen.name)
                }) {
                    Icon(tint = Color.White,
                        modifier = Modifier.size(32.dp),
                        painter = painterResource(id = R.drawable.ic_logout),
                        contentDescription = "logout"
                    )
                }
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = colorResource(id = R.color.blue),
                contentColor = Color.White,
                onClick = { navController.navigate(ChatScreens.AddRoomScreen.name) }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_fba_btn),
                    contentDescription = ""
                )
            }
        }
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
            LazyVerticalStaggeredGrid(
                modifier = Modifier.fillMaxSize(),
                columns = StaggeredGridCells.Fixed(2),
                contentPadding = PaddingValues(24.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalItemSpacing = 24.dp
            ) {
                items(viewModel.roomList.value.size) { itemPosition ->
                    val room = viewModel.roomList.value[itemPosition]
                    ChatCard(item = room, position = itemPosition) {
                        navController.currentBackStackEntry?.savedStateHandle?.apply {
                            set("room", room)
                        }
                        navController.navigate(ChatScreens.ChatRoomScreen.name )

                    }

                }
            }
        }
    }
}


