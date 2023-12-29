package ie.setu.repository

import ie.setu.domain.Meal
import ie.setu.domain.User
import ie.setu.domain.db.Meals
import ie.setu.domain.db.Users
import ie.setu.domain.repository.MealDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.helpers.meals
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

val meal1 = meals[0]
val meal2 = meals[0]
val meal3 = meals[0]
class MealDAOTest {
    companion object {
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }
    internal fun populateMealsTable(): MealDAO {
        SchemaUtils.create(Meals)
        SchemaUtils.create(Users)
        val mealDAO = MealDAO()
        val userDAO = UserDAO()
        val user1 = User(name = "Alice wonderland", email = "alice@wonderland.com", id = 1)
        userDAO.save(user1)
        mealDAO.save(meal1)
        mealDAO.save(meal2)
        mealDAO.save(meal3)
        return mealDAO
    }

    @Nested
    inner class CreateMeals {
        @Test
        fun `multiple meals added to the table can be retrieved successfully`() {
            transaction {
                val mealDAO = populateMealsTable()

                // Act and Insert
                assertEquals(3, mealDAO.getAll().size)
                assertEquals(meal1, mealDAO.findMealById(meal1.id))
                assertEquals(meal2, mealDAO.findMealById(meal2.id))
                assertEquals(meal3, mealDAO.findMealById(meal3.id))
            }
        }
    }

    @Nested
    inner class ReadMeals {
        @Test
        fun `getting all meals of a user by user id results in all meals of a user being returned`() {
            transaction {
                val mealDAO = populateMealsTable()

                assertEquals(3, mealDAO.findMealsByUserId(1).size)
            }
        }
    }

    @Nested
    inner class DeleteMeals {
        @Test
        fun `delete meals of a user by id results in record being deleted`() {
            transaction {
                val mealDAO = populateMealsTable()

                assertEquals(3, mealDAO.getAll().size)
                mealDAO.deleteMeals(1)
                assertEquals(0, mealDAO.getAll().size)
            }
        }

        @Test
        fun `delete a specific meal of a user by user id and mealId deleted the record`() {
            transaction {
                val mealDAO = populateMealsTable()

                assertEquals(3, mealDAO.getAll().size)
                mealDAO.deleteMeal(1)
                assertEquals(2, mealDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateMeals {
        @Test
        fun `updating a meal results in new values`() {
            transaction {
                val mealDAO = populateMealsTable()
                val meal1Updated = Meal(description = "Fries", calories = 101, time = DateTime.now(), userId = 1, id = 1)
                mealDAO.updateMeal(meal1Updated.id, meal1Updated)
                assertEquals(meal1Updated, mealDAO.findMealById(1))
            }
        }
    }
}
