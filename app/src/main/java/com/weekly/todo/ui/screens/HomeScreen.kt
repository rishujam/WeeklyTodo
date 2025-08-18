package com.weekly.todo.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weekly.todo.data.Data
import com.weekly.todo.data.model.Week
import com.weekly.todo.ui.theme.Background
import com.weekly.todo.ui.theme.InterFont
import com.weekly.todo.ui.theme.Outline
import com.weekly.todo.ui.theme.Primary
import com.weekly.todo.ui.theme.TextDark
import com.weekly.todo.ui.theme.TextLight
import com.weekly.todo.ui.theme.WeeklyTodoTheme
import kotlin.math.roundToInt
import com.weekly.todo.data.model.Habit

@Composable
fun HomeScreen(modifier: Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        val data = Data.getData()
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 24.dp),
            text = "Habit Tracker",
            fontSize = 24.sp,
            fontFamily = InterFont,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column {
            val state = rememberLazyListState()
            val flingBehavior = rememberSnapFlingBehavior(state)
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                state = state,
                flingBehavior = flingBehavior
            ) {
                itemsIndexed(data) { index, item ->
                    WeekComposable(item, index == data.size - 1)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                FloatingActionButton(
                    containerColor = Primary,
                    contentColor = Color.White,
                    modifier = Modifier.size(48.dp),
                    shape = CircleShape,
                    onClick = {}) {
                    Icon(Icons.Outlined.Add, contentDescription = "")
                }
                Spacer(modifier = Modifier.width(16.dp))
            }
            Spacer(modifier = Modifier.height(24.dp))
        }

    }
}

@Composable
fun WeekComposable(week: Week, isLastItem: Boolean) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val paddingEnd = if (isLastItem) 16.dp else 0.dp
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(screenWidth - 32.dp)
            .padding(start = 16.dp, end = paddingEnd)
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 20.dp),
            text = "Week ${week.weekNo}",
            fontSize = 20.sp,
            fontFamily = InterFont,
            fontWeight = FontWeight.Bold,
            color = TextDark
        )
        Text(
            modifier = Modifier.padding(start = 16.dp, top = 4.dp),
            text = week.weekRange,
            fontSize = 14.sp,
            fontFamily = InterFont,
            color = TextLight
        )
        Spacer(modifier = Modifier.height(20.dp))
        Spacer(
            modifier = Modifier
                .background(Outline)
                .fillMaxWidth()
                .height(1.dp)
        )
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(week.habits) { index, habit ->
                HabitComposable(habit) {

                }
            }
        }
    }
}

@Composable
fun HabitComposable(
    habit: Habit,
    onProgressChange: (Int) -> Unit
) {
    var dragProgress by remember { mutableFloatStateOf(habit.progress.toFloat()) } // float for smooth drag
    var totalWidthPx by remember { mutableFloatStateOf(0f) }
    val maxSteps = habit.maxWeight
    Box(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .border(width = 1.dp, color = Outline)
            .background(Background)
            .height(64.dp)
            .pointerInput(maxSteps) {
                detectDragGestures(
                    onDragStart = { offset ->
                        dragProgress = ((offset.x / totalWidthPx) * maxSteps.toFloat())
                            .coerceIn(0f, maxSteps.toFloat())
                    },
                    onDrag = { change, _ ->
                        dragProgress = ((change.position.x / totalWidthPx) * maxSteps.toFloat())
                            .coerceIn(0f, maxSteps.toFloat())
                    },
                    onDragEnd = {
                        val snapped = dragProgress.roundToInt()
                        dragProgress = snapped.toFloat()
                        onProgressChange(snapped)
                    }
                )
            }
            .onGloballyPositioned { coordinates ->
                totalWidthPx = coordinates.size.width.toFloat()
            }
    ) {
        val progressFraction = dragProgress / maxSteps
        Box(
            modifier = Modifier
                .fillMaxWidth(progressFraction)
                .fillMaxHeight()
                .background(Primary)
        )
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f),
                text = habit.title,
                fontSize = 16.sp,
                fontFamily = InterFont,
                color = TextDark,
            )
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = habit.progress.toString() + " / " + habit.maxWeight,
                fontSize = 14.sp,
                fontFamily = InterFont,
                color = TextDark
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    WeeklyTodoTheme {
        HomeScreen(Modifier)
    }
}