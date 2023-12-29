package ie.setu.controllers
import ie.setu.domain.Contact
import ie.setu.domain.repository.EmailDAO
import ie.setu.utils.jsonToObject
import io.javalin.http.Context
object EmailController {
    private val emailDAO = EmailDAO()
    fun sendEmail(ctx: Context) {
        val emailDetails: Contact = jsonToObject(ctx.body())
        val from = emailDetails.from
        val subject = emailDetails.subject
        val body = emailDetails.message
        if (from != null && subject != null && body != null) {
            emailDAO.contactForm(from, subject, body)
            ctx.status(200).result("Email sent successfully")
        } else {
            ctx.status(404)
        }
    }
}
