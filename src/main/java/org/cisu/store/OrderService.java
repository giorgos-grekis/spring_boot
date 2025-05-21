package org.cisu.store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private PaymentService paymentService;

    public OrderService(PaymentService paymentService, int x) {}

    // Could not autowire. No beans of 'PaymentService' type found.
    /* OrderService needs a paymentService
    * but paymentService is an interface so spring doesn't know
    * how to create instances of the payment service interface.
    */
    //  @Autowired -> must be used in multi constructors
    @Autowired
    public OrderService(@Qualifier("paypal") PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void placeOrder() {
        paymentService.processPayment(10);
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
