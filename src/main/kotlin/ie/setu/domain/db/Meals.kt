package ie.setu.domain.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object Meals : Table("meals") {
    val id = integer("id").autoIncrement().primaryKey()
    val description = varchar("description", 100)
    val calories = integer("calories")
    val time = datetime("time")
    val userId = integer("user_id").references(Users.id, onDelete = ReferenceOption.CASCADE)
}
