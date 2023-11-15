package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import ie.setu.domain.Activity
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context
import mu.KotlinLogging

object ActivityController {
    private val activityDAO = ActivityDAO()
    private val userDao = UserDAO()
    private val logger = KotlinLogging.logger {}

    fun getAllActivities(ctx: Context) {
        // mapper handles the deserialisation of Joda date into a string
        val mapper = jacksonObjectMapper().registerModule(JodaModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString(activityDAO.getAll()))
    }
    fun getActivitiesByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val activities = activityDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (activities.isNotEmpty()) {
                val mapper = jacksonObjectMapper().registerModule(JodaModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(activities))
            }
        }
    }
    fun getAllActivitiesByActivityId(ctx: Context) {
        val mapper = jacksonObjectMapper().registerModule(JodaModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString(activityDAO.getAllActivitiesOfSameId(ctx.pathParam("activity-id").toInt())))
    }
    fun addActivity(ctx: Context) {
        val activity: Activity = jsonToObject(ctx.body())
        activityDAO.save(activity)
        ctx.json(activity)
    }
    fun deleteActivitiesByUserId(ctx: Context) {
        if (userDao.findById(ctx.pathParam("user-id").toInt()) != null) {
            val activities = activityDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if (activities.isNotEmpty()) {
                activityDAO.deleteActivities(ctx.pathParam("user-id").toInt())
            }
        }
    }

    fun deleteActivityByActivityId(ctx: Context) {
        activityDAO.deleteActivity(ctx.pathParam("activity-id").toInt(), ctx.pathParam("user-id").toInt())
    }
    fun updateActivity(ctx: Context) {
        val activity: Activity = jsonToObject(ctx.body())
        activityDAO.updateActivity(ctx.pathParam("activity-id").toInt(), activity)
    }
}
