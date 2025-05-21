package org.cisu.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class StoreApplication {

    public static void main(String[] args) {
        // application context is ioc container (it's a storage for our objects)
       ApplicationContext context = SpringApplication.run(StoreApplication.class, args);

       var orderService =  context.getBean(OrderService.class);

       orderService.placeOrder();

////        var orderService = new OrderService(new StripePaymentService());
////        orderService.placeOrder();
//
//        var orderPaypalService = new OrderService();
////        orderPaypalService.setPaymentService(new PayPalPaymentService());
//        orderPaypalService.placeOrder();
    }

}
