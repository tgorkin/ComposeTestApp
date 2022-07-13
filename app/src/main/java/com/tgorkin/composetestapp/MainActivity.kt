// Copyright 2021 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.tgorkin.composetestapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
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
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

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
            composable("basic_input") {
                BasicInputScreen(navController)
            }
            composable("text") {
                TextScreen(navController)
            }
            composable("lists_menu") {
                ListsMenuScreen(navController)
            }
            composable("list_column") {
                ScrollingListScreen(navController)
            }
            composable("list_grid") {
                ScrollingGridScreen(navController)
            }
            composable("date_time_pickers") {
                DateTimePickerScreen(navController)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainMenu(navController: NavController? = null) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            // Basic Input
            OutlinedButton(
                onClick = { navController?.navigate("basic_input") },
                Modifier.fillMaxWidth()
            ) {
                Text("Basic Input", fontSize = 16.sp)
            }

            // Text
            OutlinedButton(
                onClick = { navController?.navigate("text") },
                Modifier.fillMaxWidth()
            ) {
                Text("Text", fontSize = 16.sp)
            }

            // Lists and Grids
            OutlinedButton(
                onClick = { navController?.navigate("lists_menu") },
                Modifier.fillMaxWidth()
            ) {
                Text("Lists and Grids", fontSize = 16.sp)
            }

            // Alert Dialog
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

            // Date Picker
            OutlinedButton(
                onClick = { navController?.navigate("date_time_pickers") },
                Modifier.fillMaxWidth()
            ) {
                Text("Date & Time Pickers", fontSize = 16.sp)
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
            ScreenTitleWithNav("Basic Input", navController)
            val listState = rememberLazyListState()
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                state = listState,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
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
fun TextScreen(navController: NavController? = null) {
    Surface(color = MaterialTheme.colors.background) {
        Column (
            modifier = Modifier
                .padding(8.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp),

        ){
            val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, " +
                    "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua." +
                    " Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris" +
                    " nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in " +
                    "reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla " +
                    "pariatur. Excepteur sint occaecat cupidatat non proident, sunt in " +
                    "culpa qui officia deserunt mollit anim id est laborum.\n\n"

            ScreenTitleWithNav("Text", navController)
            Text("Basic Text", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            Text(loremIpsum)
            Text("Clickable Text", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            ClickableText(text = AnnotatedString(loremIpsum), onClick = {})
            Text("Selectable Text", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            SelectionContainer { Text(loremIpsum.repeat(7)) }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ListsMenuScreen(navController: NavController? = null) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            ScreenTitleWithNav("Lists and Grids", navController)
            OutlinedButton(
                onClick = { navController?.navigate("list_column") },
                Modifier.fillMaxWidth()
            ) {
                Text("Column", fontSize = 16.sp)
            }
            OutlinedButton(
                onClick = { navController?.navigate("list_grid") },
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
            ScreenTitleWithNav("Column", navController)
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
fun ScrollingGridScreen(navController: NavController? = null) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            ScreenTitleWithNav("Vertical Grid", navController)
            val listState = rememberLazyGridState()
            LazyVerticalGrid(
                modifier = Modifier.padding(16.dp),
                state = listState,
                columns = GridCells.Fixed(5)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DateTimePickerScreen(navController: NavController? = null) {
    Surface(color = MaterialTheme.colors.background) {
        Column {
            ScreenTitleWithNav("Date & Time Pickers", navController)

            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    "Jetpack Compose currently does not have built in support for " +
                            "Date & Time Picker components.  The following pickers are implemented " +
                            "using Android Views widgets.", fontStyle = FontStyle.Italic
                )
                Divider(modifier = Modifier.padding(6.dp))

                // Time Picker
                val timePickerData = remember { mutableStateOf(LocalTime.of(12, 0)) }
                val timePickerDialog = TimePickerDialog(
                    LocalContext.current,
                    { _, hour: Int, minute: Int ->
                        timePickerData.value = LocalTime.of(hour, minute)
                    }, timePickerData.value.hour, timePickerData.value.minute, false
                )

                Text("Time Picker", fontSize = 16.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        timePickerData.value.format(DateTimeFormatter.ofPattern("hh:mm a")),
                        Modifier.defaultMinSize(minWidth = 40.dp)
                    )
                    IconButton(onClick = { timePickerDialog.show() }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Time Picker")
                    }
                }

                // Date Picker
                val datePickerData = remember { mutableStateOf(LocalDate.of(1970, 1, 1)) }
                val datePickerDialog = DatePickerDialog(
                    LocalContext.current,
                    { _, year: Int, month: Int, day: Int ->
                        datePickerData.value = LocalDate.of(year, month + 1, day)
                    },
                    datePickerData.value.year,
                    datePickerData.value.month.value - 1,
                    datePickerData.value.dayOfMonth
                )

                Text("Date Picker", fontSize = 16.sp)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        datePickerData.value.format(DateTimeFormatter.ISO_DATE),
                        Modifier.defaultMinSize(minWidth = 40.dp)
                    )
                    IconButton(onClick = { datePickerDialog.show() }) {
                        Icon(Icons.Default.DateRange, contentDescription = "Date Picker")
                    }
                }
            }
        }
    }
}
