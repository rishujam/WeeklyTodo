package com.weekly.todo.data.repo

import com.weekly.todo.data.local.WeekDao
import com.weekly.todo.data.model.Week
import javax.inject.Inject

class WeekRepositoryImpl @Inject constructor(
    private val dao: WeekDao
) : WeekRepository {

    override suspend fun addWeeks(weeks: List<Week>) {
        dao.upsert(weeks)
    }

    override suspend fun addWeek(week: Week) {
        dao.upsert(week)
    }

    override suspend fun getWeeks(): List<Week> {
        return dao.getWeeks()
    }
}