/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Config;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Acer
 */
public class EmailConfig {
    public boolean sendEmail(String recipientEmail, String code) {
        String emailUsername = "ducanhqbz@gmail.com"; // email cua ban than
        String emailPassword = "pfeb gdjy vrnu tiva"; // mat khau ung dung

        try {
            Email email = new SimpleEmail();
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(587);
            email.setAuthenticator(new DefaultAuthenticator(emailUsername, emailPassword));
            email.setStartTLSEnabled(true);
            email.setFrom(emailUsername, "Tutorly.com"); // Nguoi gui
            email.setSubject("Confirmation"); // Tieu de
            email.setMsg("Your verification code is : " + code); // Noi dung
            email.addTo(recipientEmail); // Dia chi email can gui toi
            email.send(); // gui
            return true; // tra ve true
        } catch (EmailException e1) {
            e1.printStackTrace();
            System.err.println("Failed to send email via port 587. Attempting via port 465.");
            try {
                Email email = new SimpleEmail();
                email.setHostName("smtp.gmail.com");
                email.setSmtpPort(465);
                email.setAuthenticator(new DefaultAuthenticator(emailUsername, emailPassword));
                email.setSSLOnConnect(true);
                email.setFrom(emailUsername, "Tutorly.com");
                email.setSubject("Xác nhận Email");
                email.setMsg("Mã xác nhận của bạn là: " + code);
                email.addTo(recipientEmail);
                email.send();
                return true;
            } catch (EmailException e2) {
                e2.printStackTrace();
                System.err.println("Failed to send email via port 465. Error: " + e2.getMessage());
                return false;
            }
        }
    } 
}
