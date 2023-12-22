package ie.setu.config
import ie.setu.controllers.ActivityController
import ie.setu.controllers.FriendsController
import ie.setu.controllers.MealController
import ie.setu.controllers.UserController
import ie.setu.utils.jsonObjectMapper
import io.javalin.Javalin
import io.javalin.apibuilder.ApiBuilder.*
import io.javalin.json.JavalinJackson
import io.javalin.vue.VueComponent

class JavalinConfig {
    fun startJavalinService(): Javalin {
        val app = Javalin.create {
            // added this jsonMapper for our integration tests - serialise objects to json
            it.jsonMapper(JavalinJackson(jsonObjectMapper()))
            it.staticFiles.enableWebjars()
            it.vue.vueAppName = "app" // only required for Vue 3, is defined in layout.html
        }.apply {
            exception(Exception::class.java) { e, _ -> e.printStackTrace() }
            error(404) { ctx -> ctx.json("404 : Not Found") }
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
                        delete(ActivityController::deleteActivityByUserId)
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
                    get(ActivityController::getActivitiesByActivityId)
                    delete(ActivityController::deleteActivityByActivityId)
                    patch(ActivityController::updateActivity)
                }
            }
            path("/api/meals") {
                get(MealController::getAllMeals)
                post(MealController::addMeal)
                path("{meal-id}") {
                    get(MealController::getAllMealsByMealId)
                }
            }
            path("/api/friends") {
                get(FriendsController::getAllFriends)
                post(FriendsController::addFriend)
                path("{friend-id}") {
                    get(FriendsController::getFriendById)
                    delete(FriendsController::deleteFriends)
                    patch(FriendsController::updateFriend)
                }
            }
            // The @routeComponent that we added in layout.html earlier will be replaced
            // by the String inside the VueComponent. This means a call to / will load
            // the layout and display our <home-page> component.
            get("/", VueComponent("<home-page></home-page>"))
            get("/users", VueComponent("<user-overview></user-overview>"))
            get("/activities", VueComponent("<activities-overview></activities-overview>"))
            get("/users/{user-id}", VueComponent("<user-profile></user-profile>"))
            get("/users/{user-id}/activities", VueComponent("<user-activity-overview></user-activity-overview>"))
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
