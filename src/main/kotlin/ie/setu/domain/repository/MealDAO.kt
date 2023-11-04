package ie.setu.domain.repository
import ie.setu.domain.Meal
import ie.setu.domain.db.Meals
import ie.setu.utils.mapToMeal
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class MealDAO {
    // Get all the meals in the database regardless of user id
    fun getAll(): ArrayList<Meal> {
        val mealsList: ArrayList<Meal> = arrayListOf()
        transaction {
            Meals.selectAll().map {
                mealsList.add(mapToMeal(it))
            }
        }
        return mealsList
    }

    // Find a meal by Id
    fun findMealById(id: Int): Meal? {
        return transaction {
            Meals.select() {
                Meals.id eq id
            }.map { mapToMeal(it) }.firstOrNull()
        }
    }

    // Find all meals of a user by userId
    fun findMealsByUserId(id: Int): List<Meal> {
        return transaction {
            Meals.select() {
                Meals.userId eq id
            }.map { mapToMeal(it) }
        }
    }
    fun findMealByMealIdAndUserId(id: Int, userId: Int) {
        return transaction {
            Meals.select() { (Meals.id eq id) and (Meals.userId eq userId) }
                .map { mapToMeal(it) }
                .firstOrNull()
        }
    }

    // Save a meal to the database
    fun save(meal: Meal) {
        transaction {
            Meals.insert {
                it[description] = meal.description
                it[time] = meal.time
                it[calories] = meal.calories
                it[userId] = meal.userId
            }
        }
    }

    // Delete all meals of a user
    fun deleteMeals(id: Int) {
        return transaction {
            Meals.deleteWhere {
                Meals.userId eq id
            }
        }
    }

    // Delete a single meal of a user
    fun deleteMeal(id: Int, userId: Int) {
        return transaction {
            Meals.deleteWhere {
                (Meals.id eq id) and (Meals.userId eq userId)
            }
        }
    }
    fun updateMeal(id: Int, meal: Meal) {
        return transaction {
            Meals.update({
                Meals.id eq id
            }) {
                it[description] = meal.description
                it[time] = meal.time
                it[calories] = meal.calories
            }
        }
    }
}
