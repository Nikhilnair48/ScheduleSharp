package JavaCode.admin;

import java.util.List;
import java.util.ListIterator;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendEmailToAcceptedStudents {

    public void sendEmail(List<EmailFields> emailInfo) {

        ListIterator<EmailFields> list = emailInfo.listIterator();

        while (list.hasNext()) {
            EmailFields fields = list.next();
            final String userName = fields.getUserName();   // MY EMAIL ID
            final String password = fields.getPassword();   // MY PASSWORD - NEED BOTH TO LOG IN FIRST
            Properties props = new Properties();
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(userName, password);
                        }
                    });

            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(userName));
                message.setRecipients(Message.RecipientType.TO,
                        InternetAddress.parse(fields.getToEmail()));
                message.setSubject(fields.getSubject());
                message.setText(fields.getMessage());

                Transport.send(message);

                System.out.println("Done");

            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
