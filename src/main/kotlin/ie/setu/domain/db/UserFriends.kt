package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table

object UserFriends : Table("UserFriends") {
    val userId = integer("user_id")
    val friendId = integer("friend_id")
    val userFriend = mutableListOf("user_id", "friend_id")
}
