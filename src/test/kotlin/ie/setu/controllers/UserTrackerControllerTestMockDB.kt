package ie.setu.controllers

import ie.setu.domain.User
import ie.setu.domain.db.Users
import ie.setu.helpers.*
import ie.setu.helpers.ServerContainer
import ie.setu.utils.jsonToObject
import junit.framework.TestCase.assertEquals
import kong.unirest.HttpResponse
import kong.unirest.JsonNode
import kong.unirest.Unirest
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class UserTrackerControllerTestMockDB {
    private val app = ServerContainer.instance
    private val origin = "http://localhost:" + app.port()

    companion object {

        // Make a connection to a local, in memory H2 database.
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }

    // helper function to retrieve a test user from the database by id
    private fun retrieveUserById(id: Int): HttpResponse<String> {
        return Unirest.get(origin + "/api/users/$id").asString()
    }
    private fun addUser(name: String, email: String): HttpResponse<JsonNode> {
        return Unirest.post(origin + "/api/users")
            .body("{\"name\":\"$name\", \"email\":\"$email\"}")
            .asJson()
    }

    private fun retrieveUserByEmail(email: String): HttpResponse<String> {
        return Unirest.get(origin + "/api/users/email/$email").asString()
    }

    @Test
    fun `getting a user by id when id exists, returns a 200 response`() {
        transaction {
            // Arrange – create a fake users table in the h2 database
            SchemaUtils.create(Users)
            // Arrange - add the user to the h2 database
            val addResponse = addUser(validName, validEmail)
            Assertions.assertEquals(201, addResponse.status)

            // Assert - retrieve the user from the fake database
            val retrieveResponse = retrieveUserByEmail(validEmail)

            // Assert - verify the return code and the contents of the retrieved user
            Assertions.assertEquals(200, retrieveResponse.status)
            val retrievedUser: User = jsonToObject(addResponse.body.toString())
            Assertions.assertEquals(validEmail, retrievedUser.email)
            Assertions.assertEquals(validName, retrievedUser.name)
        }
    }
}
