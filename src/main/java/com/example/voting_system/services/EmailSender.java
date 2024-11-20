package com.example.voting_system.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${emails.sender_email}")
    private String senderEmail;

    @Value("${emails.sender_name}")
    private String senderName;

    @Value("${emails.sender_address}")
    private String senderAddress;

    // Send email with HTML template
    public void sendEmail(String receiverEmail, String userName, String subject, String messageText) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo("aabidmahat72@gmail.com");
            helper.setSubject(subject);
            helper.setText(generateEmailTemplate(userName, messageText), true); // Set to true to enable HTML

            mailSender.send(message);
            System.out.println("Email sent successfully");
        } catch (MessagingException e) {
            System.out.println("Failed to send email: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private String generateEmailTemplate(String userName, String messageText) {
        return """
                <html>
                <body style="margin: 0; padding: 0; font-family: Arial, sans-serif; color: #333; background-color: #f4f4f4;">
                    <div style="width: 100%%; max-width: 600px; margin: 20px auto; padding: 30px; background-color: #ffffff; border-radius: 8px; box-shadow: 0px 4px 12px rgba(0, 0, 0, 0.1);">
                        <h2 style="color: #4CAF50; text-align: center;">Hello, %s!</h2>
                        <p style="font-size: 16px; line-height: 1.6; color: #555;">We are reaching out with some important information for you:</p>
                        <p style="font-size: 16px; line-height: 1.6; color: #333;">%s</p>
                        <div style="text-align: center; margin: 20px 0;">
                            <a href="https://example.com" style="display: inline-block; padding: 10px 20px; color: #ffffff; background-color: #4CAF50; border-radius: 5px; text-decoration: none; font-weight: bold;">Visit Our Website</a>
                        </div>
                        <p style="font-size: 16px; color: #777; text-align: center;">Thank you,<br><strong>%s</strong></p>
                    </div>
                    <footer style="width: 100%%; background-color: #f4f4f4; padding: 10px; text-align: center; color: #888;">
                        <p style="font-size: 12px;">&copy; 2024 Voting System | All Rights Reserved.</p>
                        <p style="font-size: 12px;">1234 Voting Street, Your City, Country | 
                           <a href="mailto:support@example.com" style="color: #4CAF50; text-decoration: none;">Contact Us</a></p>
                    </footer>
                </body>
                </html>
                """.formatted(userName, messageText, senderName);
    }


}
