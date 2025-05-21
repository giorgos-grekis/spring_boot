package org.cisu.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/*
* Configuring Beans
*/


@Configuration
public class AppConfig  {

    @Value("${payment-gateway:stripe}")
    private String paymentGateway;

    // The name of a bean shouldn't be a verb, such as getStripePaymentService.
    @Bean
    public PaymentService stripe() {
        // more code logic...
        return new StripePaymentService();
    }

    public PaymentService paypal() {
        return new PayPalPaymentService();
    }

    @Bean
//    @Lazy
    @Scope("prototype")
    public OrderService orderService() {
        if (paymentGateway.equals("stripe")) {
            return new OrderService(stripe());
        }
        return new OrderService(paypal());
    }




}
