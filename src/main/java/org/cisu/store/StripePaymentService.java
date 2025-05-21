package org.cisu.store;


public class StripePaymentService implements PaymentService {
    @Override
    public void processPayment(double amount) {
        System.out.println("Stripe payment amount : " + amount);
    }
}
