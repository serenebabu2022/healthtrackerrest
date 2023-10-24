package ie.setu.controllers

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.joda.JodaModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Activity
import ie.setu.domain.User
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.UserDAO
import io.javalin.http.Context
import mu.KotlinLogging

object HealthTrackerController {
    private val userDao=UserDAO()
    private val logger= KotlinLogging.logger {  }


    fun getAllUsers(ctx:Context){
        ctx.json(userDao.getAll())
    }
    fun getUserByUserId(ctx:Context){
        val user = userDao.findById(ctx.pathParam("user-id").toInt())
        if (user!=null){
            ctx.json(user)
        }
    }
    fun addUser(ctx:Context){
        val mapper= jacksonObjectMapper()
        val user=mapper.readValue<User>(ctx.body())
        userDao.save(user)
        ctx.json(user)
    }
    fun getUserByEmail(ctx: Context){
        val user= userDao.findByEmail(ctx.pathParam("email-id"))
        if (user!=null){
            ctx.json(user)
        }
    }
    fun deleteUser(ctx: Context){
        val user = userDao.delete(ctx.pathParam("user-id").toInt())
            ctx.json(user)
    }
    fun updateUser(ctx: Context){
        val mapper= jacksonObjectMapper()
        val user= mapper.readValue<User>(ctx.body())
        userDao.update(ctx.pathParam("user-id").toInt(),user)
        ctx.json(user)
    }

    //--------------------------------------------------------------
    // ActivityDAO specifics
    //-------------------------------------------------------------

    private val activityDAO=ActivityDAO()
    fun getAllActivities(ctx: Context){
        //mapper handles the deserialisation of Joda date into a string
        val mapper= jacksonObjectMapper().registerModule(JodaModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        ctx.json(mapper.writeValueAsString(activityDAO.getAll()))
    }
    fun getActivitiesByUserId(ctx: Context){
        if(userDao.findById(ctx.pathParam("user-id").toInt())!=null){
            val activities=activityDAO.findByUserId(ctx.pathParam("user-id").toInt())
            if(activities.isNotEmpty()){
                val mapper= jacksonObjectMapper().registerModule(JodaModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                ctx.json(mapper.writeValueAsString(activities))
            }
        }
    }
    fun addActivity(ctx: Context){
        val mapper= jacksonObjectMapper().registerModule(JodaModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false)
        val activity=mapper.readValue<Activity>(ctx.body())
        activityDAO.save(activity)
        ctx.json(activity)
    }
    fun deleteActivitiesByUserId(ctx: Context){
        if (userDao.findById(ctx.pathParam("user-id").toInt())!=null){
            val activities= activityDAO.findByUserId(ctx.pathParam("user-id").toInt())
            logger.info{"Inside func"}

            if (activities.isNotEmpty()){
                logger.info{"Inside func"}
                activityDAO.deleteActivities(ctx.pathParam("user-id").toInt())
//                val mapper= jacksonObjectMapper().registerModule(JodaModule()).configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false)
            }
        }
    }
}