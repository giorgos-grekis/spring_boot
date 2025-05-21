package org.cisu.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        // application context is ioc container (it's a storage for our objects)
//        ApplicationContext context = SpringApplication.run(StoreApplication.class, args);
        ConfigurableApplicationContext context = SpringApplication.run(StoreApplication.class, args);



//        // var orderService = new OrderService(new StripePaymentService());
//        // orderService.placeOrder();
//
//        var orderPaypalService = new OrderService();
//        // orderPaypalService.setPaymentService(new PayPalPaymentService());
//        orderPaypalService.placeOrder();
       var orderService =  context.getBean(OrderService.class);
        var orderService2 =  context.getBean(OrderService.class);
       orderService.placeOrder();
       context.close();

       // Print notification services
//       var emailNotificationManager = new NotificationManager(new EmailNotificationService());
//        emailNotificationManager.sendNotification("Hello email ");
//
//        var smsNotificationManager = new NotificationManager(new SMSNotificationService());
//        smsNotificationManager.sendNotification("Hello sms");


//        var notificationService = context.getBean(NotificationManager.class);
//       notificationService.sendNotification("Hello ioc bean");


    }

}
