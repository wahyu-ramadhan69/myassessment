package com.recruitment.myassessment.core;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.recruitment.myassessment.Configuration.SMTPConfiguration;

import java.util.Date;
import java.util.Properties;

public class SMTPCore {

    Properties prop;
    private Message message;
    private Session session;
    private String strDestination;
    private StringBuilder sBuild;
    private MimeBodyPart messageBodyPart;
    private Multipart multipart;
    private String[] strException = new String[2];

    public SMTPCore() {
        sBuild = new StringBuilder();
        strException[0] = "SMTPCore";
    }

    private Properties getTLSProp() {
        prop = new Properties();
        prop.put("mail.smtp.host", SMTPConfiguration.getEmailHost());
        prop.put("mail.smtp.port", SMTPConfiguration.getEmailPortTLS());
        prop.put("mail.smtp.auth", SMTPConfiguration.getEmailAuth());
        prop.put("mail.smtp.starttls.enable", SMTPConfiguration.getEmailStartTLSEnable());

        return prop;
    }

    private Properties getSSLProp() {
        prop = new Properties();
        prop.put("mail.smtp.host", SMTPConfiguration.getEmailHost());
        prop.put("mail.smtp.port", SMTPConfiguration.getEmailPortSSL());
        prop.put("mail.smtp.auth", SMTPConfiguration.getEmailAuth());
        prop.put("mail.smtp.socketFactory.port", SMTPConfiguration.getEmailStartTLSEnable());
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return prop;
    }

    public boolean sendSimpleMail(String[] strMailTo, String strSubject, String strContentMessage, String strLayer)
            throws Exception {
        Properties execProp;
        try {

            if (strLayer.equals("SSL")) {
                execProp = getSSLProp();
            } else {
                execProp = getTLSProp();
            }

            sBuild.setLength(0);
            for (int i = 0; i < strMailTo.length; i++) {
                sBuild.setLength(0);
                strDestination = sBuild.append(strMailTo[i]).toString();

                if (i != strMailTo.length - 1) {
                    sBuild.setLength(0);
                    strDestination = sBuild.append(strDestination).append(",").toString();
                }

            }

            session = Session.getInstance(execProp,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(SMTPConfiguration.getEmailUserName(),
                                    SMTPConfiguration.getEmailPassword());
                        }
                    });

            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTPConfiguration.getEmailUserName()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(strDestination));
            message.setSentDate(new Date());

            /* BODY OF MAIL */
            message.setSubject(strSubject);
            message.setText(strContentMessage);

            Transport.send(message);
            System.out.println("Done");

        } catch (Exception e) {
            strException[1] = "sendSimpleMail(String[] strMailTo, String strSubject, String strContentMessage, String strLayer) -- LINE 107";
            return false;
        }

        return true;
    }

    public boolean sendMailWithAttachment(String[] strMailTo,
            String strSubject,
            String strContentMessage,
            String strLayer, String[] attachFiles) throws Exception {
        Properties execProp;

        if (strLayer.equals("SSL")) {
            execProp = getSSLProp();
        } else {
            execProp = getTLSProp();
        }

        sBuild.setLength(0);
        for (int i = 0; i < strMailTo.length; i++) {
            sBuild.setLength(0);
            strDestination = sBuild.append(strMailTo[i]).toString();

            if (i != strMailTo.length - 1) {
                sBuild.setLength(0);
                strDestination = sBuild.append(strDestination).append(",").toString();
            }

        }
        session = Session.getInstance(execProp,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(SMTPConfiguration.getEmailUserName(),
                                SMTPConfiguration.getEmailPassword());
                    }
                });

        try {
            message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SMTPConfiguration.getEmailUserName()));
            message.setRecipients(
                    Message.RecipientType.TO,
                    InternetAddress.parse(strDestination));
            message.setSentDate(new Date());
            message.setSubject(strSubject);

            // creates message part
            messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(strContentMessage, "text/html");

            // creates multi-part
            multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);

            // adds attachments
            if (attachFiles != null && attachFiles.length > 0) {
                for (String filePath : attachFiles) {
                    MimeBodyPart attachPart = new MimeBodyPart();

                    try {
                        attachPart.attachFile(filePath);
                    } catch (Exception ex) {
                        throw new Exception(ex.getMessage());
                    }
                    multipart.addBodyPart(attachPart);
                }
            }

            // sets the multi-part as e-mail's content
            message.setContent(multipart);

            // sends the e-mail
            Transport.send(message);

        } catch (Exception e) {
            strException[1] = "sendMailWithAttachment(String[] strMailTo, String strSubject, String strContentMessage, String strLayer, String[] attachFiles) -- LINE 107";
            return false;
        }

        return true;
    }
}