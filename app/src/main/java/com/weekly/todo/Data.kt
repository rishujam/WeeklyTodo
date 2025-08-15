package com.weekly.todo

object Data {

    fun getData(): List<WeekData> {
        return listOf(
            WeekData(
                weekId = "Week 1",
                weekRange = "Aug 5 - Aug 11",
                habits = listOf(
                    Habit(
                        id = "1",
                        title = "Exercise",
                        maxWeight = 4,
                        progress = 1,
                    ),
                    Habit(
                        id = "2",
                        title = "Content Creation",
                        maxWeight = 1,
                        progress = 0
                    ),
                    Habit(
                        id = "3",
                        title = "Diet",
                        maxWeight = 7,
                        progress = 7
                    ),
                    Habit(
                        id = "4",
                        title = "Travel App",
                        maxWeight = 3,
                        progress = 2
                    )
                )
            ),
            WeekData(
                weekId = "Week 2",
                weekRange = "July 28 - Aug 4",
                habits = listOf(
                    Habit(
                        id = "1",
                        title = "Exercise",
                        maxWeight = 4,
                        progress = 1,
                    ),
                    Habit(
                        id = "2",
                        title = "Content Creation",
                        maxWeight = 1,
                        progress = 0
                    ),
                    Habit(
                        id = "3",
                        title = "Diet",
                        maxWeight = 7,
                        progress = 7
                    ),
                    Habit(
                        id = "4",
                        title = "Travel App",
                        maxWeight = 3,
                        progress = 2
                    )
                )
            ),
            WeekData(
                weekId = "Week 3",
                weekRange = "July 20 - July 27",
                habits = listOf(
                    Habit(
                        id = "1",
                        title = "Exercise",
                        maxWeight = 4,
                        progress = 1,
                    ),
                    Habit(
                        id = "2",
                        title = "Content Creation",
                        maxWeight = 1,
                        progress = 0
                    ),
                    Habit(
                        id = "3",
                        title = "Diet",
                        maxWeight = 7,
                        progress = 7
                    ),
                    Habit(
                        id = "4",
                        title = "Travel App",
                        maxWeight = 3,
                        progress = 2
                    )
                )
            ),
            WeekData(
                weekId = "Week 4",
                weekRange = "July 12 - July 19",
                habits = listOf(
                    Habit(
                        id = "1",
                        title = "Exercise",
                        maxWeight = 4,
                        progress = 1,
                    ),
                    Habit(
                        id = "2",
                        title = "Content Creation",
                        maxWeight = 1,
                        progress = 0
                    ),
                    Habit(
                        id = "3",
                        title = "Diet",
                        maxWeight = 7,
                        progress = 7
                    ),
                    Habit(
                        id = "4",
                        title = "Travel App",
                        maxWeight = 3,
                        progress = 2
                    )
                )
            ),
            WeekData(
                weekId = "Week 5",
                weekRange = "July 4 - July 11",
                habits = listOf(
                    Habit(
                        id = "1",
                        title = "Exercise",
                        maxWeight = 4,
                        progress = 1,
                    ),
                    Habit(
                        id = "2",
                        title = "Content Creation",
                        maxWeight = 1,
                        progress = 0
                    ),
                    Habit(
                        id = "3",
                        title = "Diet",
                        maxWeight = 7,
                        progress = 7
                    ),
                    Habit(
                        id = "4",
                        title = "Travel App",
                        maxWeight = 3,
                        progress = 2
                    )
                )
            )
        )
    }
}

data class WeekData(
    val weekId: String,
    val weekRange: String,
    val habits: List<Habit>
)

data class Habit(
    val id: String,
    val title: String,
    val maxWeight: Int,
    val progress: Int,
    val dateCreated: Long = System.currentTimeMillis()
)