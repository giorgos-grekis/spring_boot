package org.cisu.store;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;

//@Service
public class OrderService {

    private PaymentService paymentService;

    public OrderService(PaymentService paymentService, int x) {}

    // Could not autowire. No beans of 'PaymentService' type found.
    /* OrderService needs a paymentService
    * but paymentService is an interface so spring doesn't know
    * how to create instances of the payment service interface.
    */
    //  @Autowired -> must be used in multi constructors
    // @Qualifier("stripe") => only needs if we don't specify manually AppConfig.java
    @Autowired
    public OrderService(PaymentService paymentService) {
        this.paymentService = paymentService;
        System.out.println("OrderService created");
    }

    @PostConstruct
   public void init() {
        System.out.println("OrderService PostConstruct");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("OrderService PreDestroy" );
    }

    public void placeOrder() {
        paymentService.processPayment(10);
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }
}
