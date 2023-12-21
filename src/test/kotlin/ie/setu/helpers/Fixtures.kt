package ie.setu.helpers

import ie.setu.domain.Activity
import ie.setu.domain.Friend
import ie.setu.domain.Meal
import ie.setu.domain.User
import ie.setu.domain.db.Activities
import ie.setu.domain.db.Users
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.UserDAO
import org.jetbrains.exposed.sql.SchemaUtils
import org.joda.time.DateTime

val nonExistingEmail = "gvregergre@berber.erg"
val validName = "Test user 1"
val validEmail = "testuser1@test.com"
val updatedName = "Updated Name"
val updatedEmail = "Updated Email"

val updatedDescription = "Updated Description"
val updatedDuration = 30.0
val updatedCalories = 945
val updatedStarted = DateTime.parse("2020-06-11T05:59:27.258Z")

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

val friends = arrayListOf<Friend>(
    Friend(name = "Alice wonderland", goals = "fitness", id = 1),
    Friend(name = "Doja Cat", goals = "fitness", id = 2),
    Friend(name = "Taylor Swift", goals = "menatal health", id = 3),
)
fun populateUserTable(): UserDAO {
    SchemaUtils.create(Users)
    val userDAO = UserDAO()
    userDAO.save(users[0])
    userDAO.save(users[1])
    userDAO.save(users[2])
    return userDAO
}
fun populateActivityTable(): ActivityDAO {
    SchemaUtils.create(Activities)
    val activityDAO = ActivityDAO()
    activityDAO.save(activities[0])
    activityDAO.save(activities[1])
    activityDAO.save(activities[2])
    return activityDAO
}
