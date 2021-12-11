package com.tgorkin.composetestapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
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
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tgorkin.composetestapp.ui.theme.ComposeTestAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestApp()
        }
    }
}

@Composable
fun ComposeTestApp() {
    val navController = rememberNavController()

    ComposeTestAppTheme {

        NavHost(navController = navController, startDestination = "main_menu") {
            composable("main_menu") {
                MainMenu(navController)
            }
            composable("input") {
                BasicInputScreen(navController)
            }
            composable("scrolling_menu") {
                ScrollingMenuScreen(navController)
            }
            composable("scrolling_list") {
                ScrollingListScreen(navController)
            }
            composable("scrolling_grid") {
                ScrollingGridScreen(navController)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainMenu(navController: NavController? = null) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            OutlinedButton(
                onClick = { navController?.navigate("input") },
                Modifier.fillMaxWidth()
            ) {
                Text("Input", fontSize = 16.sp)
            }
            OutlinedButton(
                onClick = { navController?.navigate("scrolling_menu") },
                Modifier.fillMaxWidth()
            ) {
                Text("Scrolling", fontSize = 16.sp)
            }
            val openAlertDialog = remember { mutableStateOf(false) }
            OutlinedButton(
                onClick = { openAlertDialog.value = true },
                Modifier.fillMaxWidth()
            ) {
                Text("Alert Dialog", fontSize = 16.sp)
            }

            if (openAlertDialog.value) {
                TestAlertDialog(onDismiss = { openAlertDialog.value = false })
            }
        }
    }
}

@Composable
fun ScreenTitleWithNav(title: String, navController: NavController?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            modifier = Modifier.weight(2f)
        )
        TextButton(onClick = { navController?.popBackStack() }, modifier = Modifier.weight(1f)) {
            Text("Back")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BasicInputScreen(navController: NavController? = null) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            ScreenTitleWithNav("Basic Input Controls", navController)
            val listState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
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
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScrollingMenuScreen(navController: NavController? = null) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            ScreenTitleWithNav("Scrollable Containers", navController)
            OutlinedButton(
                onClick = { navController?.navigate("scrolling_list") },
                Modifier.fillMaxWidth()
            ) {
                Text("Vertical List", fontSize = 16.sp)
            }
            OutlinedButton(
                onClick = { navController?.navigate("scrolling_grid") },
                Modifier.fillMaxWidth()
            ) {
                Text("Grid", fontSize = 16.sp)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScrollingListScreen(navController: NavController? = null) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            ScreenTitleWithNav("Vertical List", navController)
            val listState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (i in 0..50) {
                    item { Text("Item $i", Modifier.fillMaxWidth()) }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ScrollingGridScreen(navController: NavController? = null) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            ScreenTitleWithNav("Vertical Grid", navController)
            val listState = rememberLazyListState()
            LazyVerticalGrid(
                modifier = Modifier.padding(16.dp),
                state = listState,
                cells = GridCells.Fixed(5)
            ) {
                for (i in 0..100) {
                    item {
                        Text(
                            "Item $i",
                            Modifier
                                .fillMaxWidth()
                                .height(90.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TestAlertDialog(onDismiss: () -> Unit = {}) {
    AlertDialog(
        onDismissRequest = {
            onDismiss()
        },
        buttons = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        onDismiss()
                    }) {
                    Text("Close")
                }
            }
        },
        title = {
            Text("Alert Dialog")
        },
        text = {
            Text("This is an alert dialog.")
        }
    )
}
