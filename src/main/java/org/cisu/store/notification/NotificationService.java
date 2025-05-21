package org.cisu.store.notification;

public interface NotificationService {
    void send(String message, String recipientEmail);
}
