package ie.setu.domain.repository

import ie.setu.domain.User

class UserDAO {
    private val users= arrayListOf<User>(
        User(name = "Alice", email = "alice@wonderland.com", id = 0),
        User(name = "Bob", email = "bob@abracadabra.com", id = 1),
        User(name = "Mary", email = "mary@dgreg.verg", id = 2)
    )
    fun getAll():ArrayList<User>{
        return users
    }
    fun findById(id:Int): User?{
        return users.find{it.id==id}
    }
    fun save(user: User){
        users.add(user)
    }
    fun findByEmail(email:String):User?{
        return users.find { it.email==email }
    }
    fun delete(id: Int):User{
        return users.removeAt(id)
    }
    fun update(id: Int,user: User){
        val foundUser=users.find { it.id==id }
        if(foundUser!=null){
            foundUser.name=user.name
            foundUser.email=user.email
        }
    }
}