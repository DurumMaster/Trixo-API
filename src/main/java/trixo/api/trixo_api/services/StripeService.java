package trixo.api.trixo_api.services;

import org.springframework.stereotype.Service;

import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

@Service
public class StripeService {
    
    public Customer createCustomer(String email, String name) {
        try {
            CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(email)
                .setName(name)
                .build();

            return Customer.create(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public PaymentIntent createPaymentIntent(Long amount, String currency, String customerID, String paymentMethod) {
        try{
            PaymentIntentCreateParams.Builder builder = PaymentIntentCreateParams.builder()
                    .setAmount(amount)
                    .setCurrency(currency)
                .setPaymentMethod(paymentMethod);

            if(customerID != null && !customerID.isEmpty()){
                builder.setCustomer(customerID);
            }

            PaymentIntentCreateParams params = builder.build();
            return PaymentIntent.create(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
