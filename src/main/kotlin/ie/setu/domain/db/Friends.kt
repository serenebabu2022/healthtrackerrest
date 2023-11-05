package ie.setu.domain.db

import org.jetbrains.exposed.sql.Table

object Friends : Table("friends") {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 100)
    val goals = varchar("goals", 100)
}
