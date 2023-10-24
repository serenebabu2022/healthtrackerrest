package ie.setu.helpers

import ie.setu.domain.User

val nonExistingEmail= "gvregergre@berber.erg"
val validName="Test user 1"
val validEmail="testuser1@test.com"

val users= arrayListOf<User>(
    User(name = "Alice wonderland", email = "alice@wonderland.com", id = 1),
    User(name = "Bob Cat", email = "bob@cat.com", id = 2),
    User(name = "Mary Contrary", email = "mary@contrary.com", id = 3)
)