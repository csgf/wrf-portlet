/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.infn.ct.cloudapps;

import java.util.Date;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendHTMLEmail 
{   
   public static void main(String [] args)
   {
      // Recipient's email ID needs to be mentioned.
      //String TO = "giuseppe.larocca@ct.infn.it";
      String TO = args[0];

      // Sender's email ID needs to be mentioned
      //String FROM = "giuseppe.larocca@ct.infn.it";
      String FROM = args[1];
      
      // Assuming you are sending email from localhost
      String HOST = "localhost";

      // Get system properties
      Properties properties = System.getProperties();
      
      // Application's ID to be mentioned
      String APPID = args[3];
      
      // Setup mail server
      String SMTP_HOST = args[2];
      properties.setProperty(SMTP_HOST, HOST);

      // Get the default Session object.
      Session session = Session.getDefaultInstance(properties);

      try {
         // Create a default MimeMessage object.
         MimeMessage message = new MimeMessage(session);

         // Set From: header field of the header.
         message.setFrom(new InternetAddress(FROM));

         // Set To: header field of the header.
         message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO));
         message.addRecipient(Message.RecipientType.CC, new InternetAddress(FROM));

         // Set Subject: header field
         message.setSubject(" [liferay-sg-gateway] - [ " + APPID + " ] ");

	 Date currentDate = new Date();
	 currentDate.setTime (currentDate.getTime());


         // Send the actual HTML message, as big as you like
         message.setContent(
	 "<br/><H4>" +
         "<img src=\"<%= renderRequest.getContextPath()%>/images/notification.jpg\" width=\"140\">Science Gateway Notification" +
	 "</H4><hr><br/>" +
         "<b>Description:</b> Notification for the application <b>[ " + APPID + " ]</b></br>" +
	 "<i>The application has been successfully submitted to the e-Infrastructure</i></br><br/>" +
         "<b>TimeStamp:</b> " + currentDate + "<br/><br/>" +
	 "<b>Disclaimer:</b><br/>" +
	 "<i>This is an automatic message sent by the Science Gateway based on Liferay technology.</br>" + 
	 "If you did not submit any jobs through the Science Gateway, please " +
         "<a href=\"mailto:sg-license@ct.infn.it\">contact us</a></i>",
	 "text/html");

         // Send message
         Transport.send(message);         
      } catch (MessagingException mex) { mex.printStackTrace(); }
   }   
}
