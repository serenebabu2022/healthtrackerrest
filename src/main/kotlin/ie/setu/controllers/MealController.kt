package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Meal
import ie.setu.domain.repository.MealDAO
import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context
import mu.KotlinLogging

object MealController {
    private val mealDAO = MealDAO()
    private val userDao = UserDAO()
    private val logger = KotlinLogging.logger {}

    fun getAllMeals(ctx: Context) {
        val mapper = jacksonObjectMapper().registerModule(JodaModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString(mealDAO.getAll()))
    }
    fun getMealsByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val meals = mealDAO.findMealsByUserId(ctx.pathParam("user-id").toInt())
            if (meals.isNotEmpty()) {
                val mapper = jacksonObjectMapper().registerModule(JodaModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(meals))
            }
        }
    }
    fun getAllMealsByMealId(ctx: Context) {
        val mapper = jacksonObjectMapper().registerModule(JodaModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString(mealDAO.getAllMealsOfSameId(ctx.pathParam("meal-id").toInt())))
    }
    fun addMeal(ctx: Context) {
        logger.info { "Inside addmeal" }
        val mapper = jacksonObjectMapper().registerModule(JodaModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val meal = mapper.readValue<Meal>(ctx.body())
        mealDAO.save(meal)
        ctx.json(meal)
    }
    fun deleteMealsByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val meals = mealDAO.findMealsByUserId(ctx.pathParam("user-id").toInt())
            if (meals.isNotEmpty()) {
                mealDAO.deleteMeals(ctx.pathParam("user-id").toInt())
            }
        }
    }
    fun deleteMealByMealId(ctx: Context) {
        val meal = mealDAO.findMealByMealIdAndUserId(ctx.pathParam("meal-id").toInt(), ctx.pathParam("user-id").toInt())
        if (meal != null) {
            mealDAO.deleteMeal(ctx.pathParam("meal-id").toInt(), ctx.pathParam("user-id").toInt())
        }
    }
    fun updateMeal(ctx: Context) {
        val mapper = jacksonObjectMapper().registerModule(JodaModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        val meal = mapper.readValue<Meal>(ctx.body())
        mealDAO.updateMeal(ctx.pathParam("meal-id").toInt(), meal)
    }
}
