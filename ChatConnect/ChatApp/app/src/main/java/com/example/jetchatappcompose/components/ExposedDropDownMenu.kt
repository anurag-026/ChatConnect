package com.example.jetchatappcompose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.jetchatappcompose.data.model.Category
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import java.lang.reflect.Type

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenu(

    modifier: Modifier = Modifier,
    menuList: List<Category>,
    selectedItem: MutableState<Category>
) {

    var expanded by remember { mutableStateOf(false) }

    // We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        modifier = Modifier.fillMaxWidth(),
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            // The `menuAnchor` modifier must be passed to the text field for correctness.
            modifier = modifier.menuAnchor().fillMaxWidth(),
            readOnly = true,
            value = selectedItem.value.title,
            onValueChange = {},
            label = { Text("Room Category") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            leadingIcon = {
                Image(
                    modifier = Modifier.size(40.dp),
                    painter = painterResource(id = selectedItem.value.imageResId),
                    contentDescription = ""
                )
            }
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            menuList.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {

                        Row() {
                            Image(
                                modifier = Modifier.size(40.dp),
                                painter = painterResource(id = selectionOption.imageResId),
                                contentDescription = ""
                            )
                            Text(
                                modifier = Modifier.padding(top = 14.dp, start = 16.dp),
                               text = selectionOption.title,
                                textAlign = TextAlign.Center
                            )
                        }


                    },
                    onClick = {
                        selectedItem.value = selectionOption
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}