package com.weekly.todo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weekly.todo.data.model.Habit
import com.weekly.todo.ui.composables.UnderlinedTextField
import com.weekly.todo.ui.theme.Background
import com.weekly.todo.ui.theme.InterFont
import com.weekly.todo.ui.theme.Primary
import com.weekly.todo.ui.theme.TextDark
import com.weekly.todo.ui.theme.TextLight
import com.weekly.todo.ui.theme.WeeklyTodoTheme

@Composable
fun HabitCreationScreen(
    modifier: Modifier,
    navHostController: NavHostController,
    onSaveClick: (habit: Habit) -> Unit
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        var habitTitle by remember { mutableStateOf("") }
        var habitWeight by remember { mutableStateOf("") }
        Row(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        navHostController.navigateUp()
                    },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Create Habit",
                fontSize = 18.sp,
                fontFamily = InterFont,
                color = TextDark,
                fontWeight = FontWeight.SemiBold
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 32.dp)
                .background(Background),
            verticalArrangement = Arrangement.Center
        ) {
            UnderlinedTextField(
                hint = "Habit Name",
                value = habitTitle,
                onValueChange = {
                    habitTitle = it
                }
            )
            Spacer(Modifier
                .fillMaxWidth()
                .height(16.dp))
            UnderlinedTextField(
                hint = "Weight",
                value = habitWeight,
                onValueChange = { habitWeight = it },
                keyboardType = KeyboardType.Number
            )
            Text(
                modifier = Modifier.padding(top = 2.dp),
                text = "* No of times you will be doing this in a week.", color = TextLight,
                fontSize = 10.sp
            )
        }
        Row(modifier = Modifier.padding(16.dp)) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Primary)
                    .padding(vertical = 12.dp)
                    .clickable {
                        if (
                            habitTitle.isNotBlank() &&
                            habitTitle.isNotEmpty() &&
                            habitWeight.isNotBlank() &&
                            habitWeight.isNotEmpty() &&
                            habitWeight.toInt() >= 0
                        ) {
                            onSaveClick(
                                Habit(
                                    id = 0, // Id is created in viewModel
                                    title = habitTitle,
                                    maxWeight = habitWeight.toInt(),
                                )
                            )
                            navHostController.navigateUp()
                        } else {
                            Toast.makeText(context, "Invalid Inputs", Toast.LENGTH_SHORT).show()
                        }
                    },
                textAlign = TextAlign.Center,
                color = Color.White,
                text = "Save",
                fontSize = 16.sp,
                fontFamily = InterFont,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HabitCreationScreenPreview() {
    WeeklyTodoTheme {
        HabitCreationScreen(Modifier, rememberNavController()) {}
    }
}