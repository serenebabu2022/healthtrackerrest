package ie.setu.domain.repository

import ie.setu.domain.Friend
import ie.setu.domain.db.Friends
import ie.setu.utils.mapToFriend
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class FriendsDAO {
    fun getAll(): ArrayList<Friend> {
        val friendsList: ArrayList<Friend> = arrayListOf()
        transaction {
            Friends.selectAll().map {
                friendsList.add(mapToFriend(it))
            }
        }
        return friendsList
    }
    fun findFriendById(id: Int): Friend? {
        return transaction {
            Friends.select() {
                Friends.id eq id
            }.map { mapToFriend(it) }.firstOrNull()
        }
    }
    fun save(friend: Friend) {
        transaction {
            Friends.insert {
                it[name] = friend.name
                it[goals] = friend.goals
            }
        }
    }
    fun delete(id: Int) {
        return transaction {
            Friends.deleteWhere {
                Friends.id eq id
            }
        }
    }
    fun update(id: Int, friend: Friend) {
        transaction {
            Friends.update({
                Friends.id eq id
            }) {
                it[name] = friend.name
                it[goals] = friend.goals
            }
        }
    }
}
