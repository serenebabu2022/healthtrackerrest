package ie.setu.utils

import ie.setu.domain.*
import ie.setu.domain.db.*
import org.jetbrains.exposed.sql.ResultRow

fun mapToUser(it: ResultRow) = User(
    id = it[Users.id],
    name = it[Users.name],
    email = it[Users.email],
)
fun mapToActivity(it: ResultRow) = Activity(
    id = it[Activities.id],
    description = it[Activities.description],
    duration = it[Activities.duration],
    started = it[Activities.started],
    calories = it[Activities.calories],
    userId = it[Activities.userId],
)
fun mapToMeal(it: ResultRow) = Meal(
    id = it[Meals.id],
    description = it[Meals.description],
    calories = it[Meals.calories],
    time = it[Meals.time],
    userId = it[Meals.userId],
)
