package com.example.jetchatappcompose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetchatappcompose.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatAuthTextField(
    state: MutableState<String>,
    label: String,
    errorState: MutableState<String>,
    isPassword: Boolean = false
) {
    TextField(
        value = state.value, onValueChange = { newValue ->
            state.value = newValue
        }, colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
        label = {
            Text(
                text = label,
                style = TextStyle(
                    color = colorResource(id = R.color.grey),
                    fontSize = 14.sp
                )
            )
        },
        isError = errorState.value.isNotEmpty(),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = if (isPassword) KeyboardOptions(keyboardType = KeyboardType.Password) else KeyboardOptions(),
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .fillMaxWidth()

    )
    if (errorState.value.isNotEmpty()) {
        Text(
            text = errorState.value,
            style = TextStyle(color = Color.Red, fontSize = 13.sp),
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 1.dp)
        )
    }

}