package com.weekly.todo.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.weekly.todo.R
import com.weekly.todo.data.Data
import com.weekly.todo.data.model.Habit
import com.weekly.todo.data.model.Week
import com.weekly.todo.ui.UIEvent
import com.weekly.todo.ui.theme.Background
import com.weekly.todo.ui.theme.InterFont
import com.weekly.todo.ui.theme.Outline
import com.weekly.todo.ui.theme.Primary
import com.weekly.todo.ui.theme.TextDark
import com.weekly.todo.ui.theme.TextLight
import com.weekly.todo.ui.theme.WarningRed
import com.weekly.todo.ui.theme.WeeklyTodoTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

@Composable
fun HabitScreen(
    habitId: Int?,
    weeks: List<Week>,
    modifier: Modifier,
    navHostController: NavHostController,
    onEvent: (UIEvent) -> Unit
) {
    val context = LocalContext.current
    habitId?.let {
        var habit: Habit? = null
        var totalWeeks = 0
        var totalProgress = 0
        val progressList = mutableListOf<Float>()
        for(week in weeks) {
            val localHabit = week.habits.find { it.id == habitId }
            if(localHabit != null) {
                habit = localHabit
                totalWeeks++
                progressList.add(localHabit.progress.toFloat())
                totalProgress += localHabit.progress
            }
        }
        val weeklyAvg = totalWeeks / totalProgress.toFloat()
        val roundedWeeklyAvg = (weeklyAvg * 100).roundToInt() / 100f
        habit?.let {
            val dateCreated = formatMillisToDate(it.dateCreated)
            Column(modifier = modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.45f)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .background(Background)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White)
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                            text = "Lifetime Report",
                            fontSize = 18.sp,
                            fontFamily = InterFont,
                            color = TextDark,
                            fontWeight = FontWeight.SemiBold
                        )
                        BarGraph(progressList, maxValue = habit.maxWeight.toFloat())
                    }
                }
                Column(
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxSize()
                        .background(Background)
                ) {
                    Text(
                        modifier = Modifier.padding(start = 32.dp, end = 32.dp, bottom = 8.dp).fillMaxWidth(),
                        text = habit.title,
                        fontFamily = InterFont,
                        color = TextDark,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .padding(start = 16.dp, end = 16.dp)
                            .background(Outline)
                    )
                    Text(
                        modifier = Modifier.padding(start = 32.dp, top = 8.dp),
                        text = "Date Created: $dateCreated",
                        fontSize = 14.sp,
                        fontFamily = InterFont,
                        color = TextLight
                    )
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.33f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White)
                                    .padding(vertical = 16.dp, horizontal = 8.dp)
                            ) {
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.week_icon),
                                    contentDescription = "week_icon"
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    text = "Weekly Target",
                                    fontSize = 12.sp,
                                    fontFamily = InterFont,
                                    color = TextLight
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    text = "${habit.maxWeight} Times",
                                    fontSize = 12.sp,
                                    fontFamily = InterFont,
                                    color = TextDark,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(Modifier.width(16.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.5f)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White)
                                    .padding(vertical = 16.dp, horizontal = 8.dp)
                            ) {
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.chart_icon),
                                    contentDescription = "week_icon"
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    text = "Weekly Average",
                                    fontSize = 12.sp,
                                    fontFamily = InterFont,
                                    color = TextLight
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    text = "$roundedWeeklyAvg Times",
                                    fontSize = 12.sp,
                                    fontFamily = InterFont,
                                    color = TextDark,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                            Spacer(Modifier.width(16.dp))
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White)
                                    .padding(vertical = 16.dp, horizontal = 8.dp)
                            ) {
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(R.drawable.calendar_icon),
                                    contentDescription = "week_icon"
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    text = "Total Weeks",
                                    fontSize = 12.sp,
                                    fontFamily = InterFont,
                                    color = TextLight
                                )
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    text = "$totalWeeks Weeks",
                                    fontSize = 12.sp,
                                    fontFamily = InterFont,
                                    color = TextDark,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                    Text(
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                        text = "* Deletes the habit from the ongoing week only.",
                        fontSize = 12.sp,
                        color = TextLight,
                        fontFamily = InterFont
                    )
                    Row(modifier = Modifier.padding(start = 16.dp, bottom = 16.dp, end = 16.dp)) {
                        val myId = "inlineContent"
                        val text = buildAnnotatedString {
                            appendInlineContent(myId, "[icon]")
                            append("Delete Habit")
                        }
                        val inlineContent = mapOf(
                            Pair(
                                myId,
                                InlineTextContent(
                                    Placeholder(
                                        width = 24.sp,
                                        height = 24.sp,
                                        placeholderVerticalAlign = PlaceholderVerticalAlign.Center
                                    )
                                ) {
                                    Icon(imageVector = Icons.Outlined.Delete,"", tint = Color.White)
                                }
                            )
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(WarningRed)
                                .padding(vertical = 12.dp)
                                .clickable {
                                    onEvent(UIEvent.DeleteHabit(habit, weeks.last()))
                                    navHostController.navigateUp()
                                },
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            text = text,
                            fontSize = 16.sp,
                            fontFamily = InterFont,
                            inlineContent = inlineContent
                        )
                    }
                }
            }
        } ?: run {
            Toast.makeText(context, "Habit not found", Toast.LENGTH_SHORT).show()
        }
    } ?: run {
        Toast.makeText(context, "Habit not found", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun BarGraph(
    values: List<Float>,
    maxValue: Float = (values.maxOrNull() ?: 0f),
    barWidth: Float = 120f,
    spaceBetweenBars: Float = 40f
) {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .horizontalScroll(scrollState)
        ) {
            Canvas(
                modifier = Modifier
                    .width((values.size * (barWidth + spaceBetweenBars)).dp)
                    .fillMaxHeight()
            ) {
                val canvasHeight = size.height
                values.forEachIndexed { index, value ->
                    val barHeight = (value / maxValue) * canvasHeight
                    val left = index * (barWidth + spaceBetweenBars)
                    val top = canvasHeight - barHeight

                    drawRoundRect(
                        color = Primary,
                        topLeft = Offset(left, top),
                        size = Size(barWidth, barHeight),
                        cornerRadius = CornerRadius(10f, 10f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HabitScreenPreview() {
    WeeklyTodoTheme {
        HabitScreen(1, Data.getData(), navHostController = rememberNavController(), modifier =  Modifier) {}
    }
}

private fun formatMillisToDate(millis: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(Date(millis))
}