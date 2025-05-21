package org.cisu.store.notification;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service("email")
@Primary
public class EmailNotificationService implements NotificationService {

    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private String port;

    @Override
    public void send(String message, String recipientEmail) {
        System.out.println("Email: ");
        System.out.println("message: " + message);
        System.out.println("recipient: " + recipientEmail);
        System.out.println("host: " + host);
        System.out.println("port: " + port);
    }
}
