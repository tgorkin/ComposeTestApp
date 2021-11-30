package com.tgorkin.composetestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tgorkin.composetestapp.ui.theme.ComposeTestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    BasicComponents()
                }
            }
        }
    }
}

@Composable
fun BasicComponents() {
    val listState = rememberLazyListState()
    LazyColumn(
        modifier = Modifier.padding(16.dp),
        state = listState,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("Basic Compose UI Elements", fontWeight = FontWeight.Bold)
        }
        item {
            Text("Clickable Text", Modifier.clickable(onClick = {}))
        }
        item {
            SelectionContainer { Text("Selectable Text") }
        }
        item {
            var text by remember { mutableStateOf("") }
            TextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Text Field") }
            )
        }
        item {
            var password by remember { mutableStateOf("") }
            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password Text Field") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
        item {
            var text by remember { mutableStateOf("") }
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("Outlined Text Field") }
            )
        }
        item {
            Button(onClick = { }) {
                Text("Button")
            }
        }
        item {
            TextButton(onClick = { }) {
                Text("Text Button")
            }
        }
        item {
            OutlinedButton(onClick = { }) {
                Text("Outlined Button")
            }
        }
        item {
            Button(onClick = { }, enabled = false) {
                Text("Disabled Button")
            }
        }
        item {
            Row {
                Text("Checkbox")
                Spacer(modifier = Modifier.size(4.dp))
                var checked by remember { mutableStateOf(false) }
                Checkbox(checked = checked, onCheckedChange = { checked = !checked })
            }
        }
        item {
            Row {
                Text("Switch")
                Spacer(modifier = Modifier.size(4.dp))
                var switched by remember { mutableStateOf(false) }
                Switch(checked = switched, onCheckedChange = { switched = !switched })
            }
        }
        item {
            Row {
                Text("Radio Button")
                Spacer(modifier = Modifier.size(4.dp))
                var checked by remember { mutableStateOf(false) }
                RadioButton(selected = checked, onClick = { checked = !checked })
            }
        }

        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("Slider")
                Spacer(modifier = Modifier.size(4.dp))
                var sliderState by remember { mutableStateOf(0f) }
                Slider(value = sliderState, modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                    onValueChange = { newValue ->
                        sliderState = newValue
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeTestAppTheme {
        BasicComponents()
    }
}