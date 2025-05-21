package org.cisu.store.notification;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class NotificationManager {
    // this flied we not want to change latter, that's why we put final
    private final NotificationService notificationService;

    public NotificationManager(@Qualifier("sms") NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public void sendNotification(String message) {
        notificationService.send(message);
    }
}
