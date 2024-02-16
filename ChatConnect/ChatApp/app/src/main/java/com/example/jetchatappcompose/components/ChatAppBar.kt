package com.example.jetchatappcompose.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAppBar(
    modifier: Modifier = Modifier,
    topAppTitle : String,
    actions: @Composable() (RowScope.() -> Unit) = {},
    navigationIcon : @Composable () -> Unit = {},
) {
    CenterAlignedTopAppBar(modifier = Modifier.padding(top = 16.dp),
        title = {
            Text(
                text = topAppTitle,
                modifier = modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                style = TextStyle(fontSize = 32.sp, color = Color.White ,fontWeight = FontWeight.Bold),
                textAlign = TextAlign.Center,

                )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
        navigationIcon = navigationIcon,
        actions = actions
    )
}