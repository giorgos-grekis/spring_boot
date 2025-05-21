package org.cisu.store;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("stripe")
@Primary // use this in order to use the default Bean
public class StripePaymentService implements PaymentService {

    @Value("${stripe.apiUrl}")
    private String apiUrl;

    @Value("${stripe.enabled}")
    private boolean enabled;

    // set a default value
    @Value("${stripe.timeout:3000}")
    private int timeout;

    @Value("${stripe.supported-currencies}")
    private List<String> supportedCurrencies;

    @Override
    public void processPayment(double amount) {
        System.out.println("Stripe");
        System.out.println("amount : " + amount);
        System.out.println("enabled : " + enabled);
        System.out.println("timeout : " + timeout);
        System.out.println("supportedCurrencies : " + supportedCurrencies);
    }
}
