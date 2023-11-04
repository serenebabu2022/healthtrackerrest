package ie.setu.helpers

import ie.setu.domain.Activity
import ie.setu.domain.Meal
import ie.setu.domain.User
import org.joda.time.DateTime

val nonExistingEmail = "gvregergre@berber.erg"
val validName = "Test user 1"
val validEmail = "testuser1@test.com"

val users = arrayListOf<User>(
    User(name = "Alice wonderland", email = "alice@wonderland.com", id = 1),
    User(name = "Bob Cat", email = "bob@cat.com", id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com", id = 3),
)
val activities = arrayListOf<Activity>(
    Activity(description = "Walking", duration = 30.0, calories = 101, started = DateTime.now(), userId = 1, id = 1),
    Activity(description = "Running", duration = 30.0, calories = 120, started = DateTime.now(), userId = 1, id = 2),
    Activity(description = "Jogging", duration = 30.0, calories = 101, started = DateTime.now(), userId = 1, id = 3),
)
val meals = arrayListOf<Meal>(
    Meal(description = "Rice", calories = 101, time = DateTime.now(), userId = 1, id = 1),
    Meal(description = "Potato", calories = 101, time = DateTime.now(), userId = 1, id = 1),
    Meal(description = "Veggies", calories = 101, time = DateTime.now(), userId = 1, id = 1),
)
