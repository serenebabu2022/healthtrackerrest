package ie.setu.domain.repository
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeBodyPart
import javax.mail.internet.MimeMessage
import javax.mail.internet.MimeMultipart

class EmailDAO {
    fun contactForm(from: String, subject: String, body: String) {
        val username = "20105064@mail.wit.ie"
        val appPassword = "esrz fyqs oglk xbpj"
        val host = "smtp.gmail.com"

        val properties = Properties()
        properties.setProperty("mail.smtp.host", host)
        properties.setProperty("mail.smtp.auth", "true")
        properties.setProperty("mail.smtp.starttls.enable", "true")

        val session: Session = Session.getDefaultInstance(properties)
        try {
            val message = MimeMessage(session)
            message.setFrom(InternetAddress(from))
            message.addRecipient(Message.RecipientType.TO, InternetAddress(from))
            message.subject = subject

            val messageBodyPart: BodyPart = MimeBodyPart()
            messageBodyPart.setText(body)
            val multipart: Multipart = MimeMultipart()
            multipart.addBodyPart(messageBodyPart)

            message.setContent(multipart)
            message.saveChanges()

            val transport: Transport = session.getTransport("smtp")
            transport.connect(host, username, appPassword)
            transport.sendMessage(message, message.allRecipients)
            transport.close()

            println("Sent message successfully....")
        } catch (mex: MessagingException) {
            mex.printStackTrace()
            println("Error sending message + $mex")
        }
    }
}
