package ie.setu.controllers

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import ie.setu.domain.Friend
import ie.setu.domain.repository.FriendsDAO
import io.javalin.http.Context

object FriendsController {
    private val friendDao = FriendsDAO()
    fun getAllFriends(ctx: Context) {
        ctx.json(friendDao.getAll())
    }
    fun getFriendById(ctx: Context) {
        val friend = friendDao.findFriendById(ctx.pathParam("friend-id").toInt())
        if (friend != null) {
            ctx.json(friend)
        }
    }
    fun addFriend(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val friend = mapper.readValue<Friend>(ctx.body())
        friendDao.save(friend)
        ctx.json(friend)
    }
    fun deleteFriends(ctx: Context) {
        val friend = friendDao.delete(ctx.pathParam("friend-id").toInt())
        ctx.json(friend)
    }
    fun updateFriend(ctx: Context) {
        val mapper = jacksonObjectMapper()
        val friend = mapper.readValue<Friend>(ctx.body())
        friendDao.update(ctx.pathParam("friend-id").toInt(), friend)
        ctx.json(friend)
    }
}
