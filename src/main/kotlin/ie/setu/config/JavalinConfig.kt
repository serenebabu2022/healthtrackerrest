package ie.setu.config
import ie.setu.controllers.ActivityController
import ie.setu.controllers.MealController
import ie.setu.controllers.UserController
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
class JavalinConfig {
    fun startJavalinService(): Javalin {
        val app = Javalin.create().apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 - Not Found") }
        }.start(getRemoteAssignedPort())

        registerRoutes(app)
        return app
    }

    private fun registerRoutes(app: Javalin) {
        app.routes {
            path("/api/users") {
                get(UserController::getAllUsers)
                post(UserController::addUser)
                path("{user-id}") {
                    get(UserController::getUserByUserId)
                    delete(UserController::deleteUser)
                    patch(UserController::updateUser)
                    path("activities") {
                        get(ActivityController::getActivitiesByUserId)
                        delete(ActivityController::deleteActivitiesByUserId)
                        path("{activity-id}") {
                            delete(ActivityController::deleteActivityByActivityId)
                            patch(ActivityController::updateActivity)
                        }
                    }
                    path("meals") {
                        get(MealController::getMealsByUserId)
                        delete(MealController::deleteMealsByUserId)
                        path("{meal-id}") {
                            delete(MealController::deleteMealByMealId)
                            patch(MealController::updateMeal)
                        }
                    }
                }
                path("email/{email-id}") {
                    get(UserController::getUserByEmail)
                }
            }
            path("/api/activities") {
                get(ActivityController::getAllActivities)
                post(ActivityController::addActivity)
                path("{activity-id}") {
                    get(ActivityController::getAllActivitiesByActivityId)
                }
            }
            path("/api/meals") {
                get(MealController::getAllMeals)
                post(MealController::addMeal)
                path("{meal-id}") {
                    get(MealController::getAllMealsByMealId)
                }
            }
        }
    }
    private fun getRemoteAssignedPort(): Int {
        val remotePort = System.getenv("PORT")
        return if (remotePort != null) {
            Integer.parseInt(remotePort)
        } else {
            7000
        }
    }
}
