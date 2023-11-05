package ie.setu.repository

import ie.setu.domain.Friend
import ie.setu.domain.db.Friends
import ie.setu.domain.repository.FriendsDAO
import ie.setu.helpers.friends
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

val friend1 = friends[0]
val friend2 = friends[1]
val friend3 = friends[2]
class FriendDAOTest {
    companion object {
        // Make a connection to a local, in memory H2 database
        @BeforeAll
        @JvmStatic
        internal fun setupInMemoryDatabaseConnection() {
            Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
        }
    }
    internal fun populateFriendTable(): FriendsDAO {
        SchemaUtils.create(Friends)
        val friendDAO = FriendsDAO()
        friendDAO.save(friend1)
        friendDAO.save(friend2)
        friendDAO.save(friend3)
        return friendDAO
    }

    @Nested
    inner class CreateFriends {
        @Test
        fun `multiple friends added to table can be retrieved successfully`() {
            transaction {
                // Arrange -create and populate table with three friends
                val friendDAO = populateFriendTable()

                // Act and insert
                assertEquals(3, friendDAO.getAll().size)
                assertEquals(friend1, friendDAO.findFriendById(friend1.id))
                assertEquals(friend2, friendDAO.findFriendById(friend2.id))
                assertEquals(friend3, friendDAO.findFriendById(friend3.id))
            }
        }
    }

    @Nested
    inner class DeleteFriends {
        @Test
        fun `deleting an non existing friend results in no record being deleted`() {
            transaction {
                // Arrange- create and populate table with three frends
                val friendDAO = populateFriendTable()

                // Act and Assert
                assertEquals(3, friendDAO.getAll().size)
                friendDAO.delete(4)
                assertEquals(3, friendDAO.getAll().size)
            }
        }

        @Test
        fun `deleting an  existing friend results in record being deleted`() {
            transaction {
                // Arrange- create and populate table with three frends
                val friendDAO = populateFriendTable()

                // Act and Assert
                assertEquals(3, friendDAO.getAll().size)
                friendDAO.delete(1)
                assertEquals(2, friendDAO.getAll().size)
            }
        }
    }

    @Nested
    inner class UpdateFriends {
        @Test
        fun `update a friend using its id results in record being updated`() {
            transaction {
                // Arrange
                val friendDAO = populateFriendTable()

                // Act and Assert
                val friend3Updated = Friend(3, "new name", "health")
                friendDAO.update(friend3.id, friend3Updated)
                assertEquals(friend3Updated, friendDAO.findFriendById(3))
            }
        }
    }
}
