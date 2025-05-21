package org.cisu.store;

import org.cisu.store.notification.EmailNotificationService;
import org.cisu.store.notification.NotificationManager;
import org.cisu.store.notification.SMSNotificationService;
import org.cisu.store.registration.User;
import org.cisu.store.registration.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        // application context is ioc container (it's a storage for our objects)
        ApplicationContext context = SpringApplication.run(StoreApplication.class, args);
//        ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);



//        // var orderService = new OrderService(new StripePaymentService());
//        // orderService.placeOrder();
//
//        var orderPaypalService = new OrderService();
//        // orderPaypalService.setPaymentService(new PayPalPaymentService());
//        orderPaypalService.placeOrder();
       var orderService =  context.getBean(OrderService.class);
        var orderService2 =  context.getBean(OrderService.class);
       orderService.placeOrder();
//       context.close();

//       // Print notification services
//       var emailNotificationManager = new NotificationManager(new EmailNotificationService());
//        emailNotificationManager.sendNotification("Hello email ", "test@test.com");
//
//        var smsNotificationManager = new NotificationManager(new SMSNotificationService());
//        smsNotificationManager.sendNotification("Hello sms", "john@smith.com");
//

//        var notificationService = context.getBean(NotificationManager.class);
//       notificationService.sendNotification("Hello ioc bean", "test@test.com");

            var userService = context.getBean(UserService.class);
        userService.registerUser(new User(1L, "myemail@gmail.com", "123456" , "cisu"));
        userService.registerUser(new User(1L, "myemail@gmail.com", "123456" , "cisu"));


    }

}
