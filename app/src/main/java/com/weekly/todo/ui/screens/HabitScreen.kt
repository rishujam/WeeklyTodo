package com.weekly.todo.ui.screens

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Top
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
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
        val graphProgress = mutableListOf<Float>()
        val datesList = mutableListOf<String>()
        weeks.forEachIndexed { index, week ->
            val localHabit = week.habits.find { it.id == habitId }
            if(localHabit != null) {
                habit = localHabit
                datesList.add("Week ${index + 1}")
                totalWeeks++
                graphProgress.add(localHabit.progress.toFloat() / localHabit.maxWeight.toFloat())
                totalProgress += localHabit.progress
            }
        }
        habit?.let { nHabit ->
            val weeklyAvgRatio = (totalProgress.toFloat() / (totalWeeks * nHabit.maxWeight)) * 100
            val weeklyAvg = ((nHabit.maxWeight.toFloat() / 100f) * weeklyAvgRatio)
            val weeklyAvgRounded = (weeklyAvg * 10).roundToInt() / 10f
            val dateCreated = formatMillisToDate(nHabit.dateCreated)
            Column(modifier = modifier.fillMaxSize().background(Background)) {
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
                        modifier = Modifier.padding(bottom = 16.dp, top = 16.dp),
                        text = "Lifetime Report",
                        fontSize = 18.sp,
                        fontFamily = InterFont,
                        color = TextDark,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxHeight(0.45f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color.White)
                ) {
                    BoxWithConstraints {
                        val graphHeight = maxHeight
                        BarGraph(
                            graphBarData = graphProgress,
                            xAxisScaleData = datesList,
                            height = graphHeight
                        )
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
                        text = nHabit.title,
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
                                    text = "${nHabit.maxWeight} Times",
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
                                    text = "$weeklyAvgRounded Times",
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
                                    onEvent(UIEvent.DeleteHabit(nHabit, weeks.last()))
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


@Composable
fun BarGraph(
    graphBarData: List<Float>,
    xAxisScaleData: List<String>,
    height: Dp
) {
    val xAxisScaleHeight = 42.dp
    val barShape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp)

    val listState = rememberLazyListState()

    // Scroll to last item when screen opens
    LaunchedEffect(Unit) {
        listState.scrollToItem(graphBarData.size - 1)
    }

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxWidth()
            .height(height + xAxisScaleHeight)
            .padding(top = 16.dp),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Start
    ) {
        items(graphBarData.size) { index ->
            var animationTriggered by remember {
                mutableStateOf(false)
            }
            val graphBarHeight by animateFloatAsState(
                targetValue = if (animationTriggered) graphBarData[index] else 0f,
                animationSpec = tween(
                    durationMillis = 1000,
                    delayMillis = 0
                )
            )
            LaunchedEffect(key1 = true) {
                animationTriggered = true
            }
            val paddingStart = if(index == 0) 16.dp else 0.dp
            Column(
                modifier = Modifier.fillMaxHeight().padding(start = paddingStart, end = 16.dp),
                verticalArrangement = Top,
                horizontalAlignment = CenterHorizontally
            ) {
                // Each Graph
                Box(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .clip(barShape)
                        .width(20.dp)
                        .weight(1f)
                        .background(Color.Transparent),
                    contentAlignment = BottomCenter
                ) {
                    Box(
                        modifier = Modifier
                            .clip(barShape)
                            .fillMaxWidth()
                            .fillMaxHeight(graphBarHeight)
                            .background(Primary)
                    )
                }
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = xAxisScaleData[index],
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }
    }
}