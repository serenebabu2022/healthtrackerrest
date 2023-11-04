package ie.setu.domain

import org.joda.time.DateTime

data class Meal(var id: Int, var description: String, var calories: Int, var time: DateTime, var userId: Int)
