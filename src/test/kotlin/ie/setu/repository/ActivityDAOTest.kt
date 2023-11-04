package ie.setu.repository

import ie.setu.domain.Activity
import ie.setu.domain.User
import ie.setu.domain.db.Activities
import ie.setu.domain.db.Users
import ie.setu.domain.repository.ActivityDAO
import ie.setu.domain.repository.UserDAO
import ie.setu.helpers.activities
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

val activity1 = activities[0]
val activity2 = activities[1]
val activity3 = activities[2]
class ActivityDAOTest {
    companion object {
        // Make a connection to a local, in memory H2 database
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }
    internal fun populateActivityTable(): ActivityDAO {
        SchemaUtils.create(Activities)
        SchemaUtils.create(Users)
        val activityDAO = ActivityDAO()
        val userDAO = UserDAO()
        val user1 = User(name = "Alice wonderland", email = "alice@wonderland.com", id = 1)
        userDAO.save(user1)
        activityDAO.save(activity1)
        activityDAO.save(activity2)
        activityDAO.save(activity3)
        return activityDAO
    }

    @Nested
    inner class CreateActivities {
        @Test
        fun `multiple activities added to the table can be retrieved successfully`() {
            transaction {
                val activityDAO = populateActivityTable()

                // Act and Insert
                assertEquals(3, activityDAO.getAll().size)
                assertEquals(activity1, activityDAO.findByActivityId(activity1.id))
                assertEquals(activity2, activityDAO.findByActivityId(activity2.id))
                assertEquals(activity3, activityDAO.findByActivityId(activity3.id))
            }
        }
    }

    @Nested
    inner class ReadActivities {
        @Test
        fun `getting all activities from a populated table returns all rows`() {
            transaction {
                val activityDAO = populateActivityTable()
                assertEquals(3, activityDAO.getAll().size)
            }
        }

        @Test
        fun `get activity by id that doesnt exist, returns no activity`() {
            transaction {
                val activityDAO = populateActivityTable()
                assertEquals(null, activityDAO.findByActivityId(4))
            }
        }

        @Test
        fun `get all activities over empty table returns none`() {
            transaction {
                SchemaUtils.create(Activities)
                val activityDAO = ActivityDAO()

                assertEquals(0, activityDAO.getAll().size)
            }
        }

        @Test
        fun `get activities by a specific user by userid`() {
            transaction {
                val activityDAO = populateActivityTable()
                assertEquals(3, activityDAO.findByUserId(1).size)
            }
        }

        @Test
        fun `get all activities of same id works`() {
            transaction {
                val activityDAO = populateActivityTable()
                assertEquals(1, activityDAO.getAllActivitiesOfSameId(2).size)
            }
        }
    }

    @Nested
    inner class DeleteActivities {
        @Test
        fun `delete activity by activity id and user id works`() {
            transaction {
                val activityDAO = populateActivityTable()
                assertEquals(3, activityDAO.getAll().size)
                activityDAO.deleteActivity(1, 1)
                assertEquals(2, activityDAO.getAll().size)
            }
        }

        @Test
        fun `delete activity by activity id and user id that doesnt exist results in no deletion`() {
            transaction {
                val activityDAO = populateActivityTable()
                assertEquals(3, activityDAO.getAll().size)
                activityDAO.deleteActivity(1, 2)
                assertEquals(3, activityDAO.getAll().size)
            }
        }

        @Test
        fun `delete all activities of a user results in all activites of a user in table being deleted`() {
            transaction {
                val activityDAO = populateActivityTable()
                assertEquals(3, activityDAO.getAll().size)
                activityDAO.deleteActivities(1)
                assertEquals(0, activityDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateActivity {
        @Test
        fun `update activity of a user results in the record in the table being updated`() {
            transaction {
                val activityDAO = populateActivityTable()
                val activity1updated = Activity(description = "Walking and singing", duration = 40.0, calories = 101, started = DateTime.now(), userId = 1, id = 1)
                activityDAO.updateActivity(activity1updated.id, activity1updated)
                assertEquals(activity1updated, activityDAO.findByActivityId(1))
            }
        }
    }
}
