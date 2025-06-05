package trixo.api.trixo_api.services;

import org.springframework.stereotype.Service;

import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

import trixo.api.trixo_api.dto.AddressDto;
import trixo.api.trixo_api.dto.CustomerDto;
import trixo.api.trixo_api.dto.PaymentDto;

@Service
public class StripeService {
    
    public Customer createCustomer(CustomerDto customerDto) {
        try {
            CustomerCreateParams params = CustomerCreateParams.builder()
                .setEmail(customerDto.getEmail())
                .setName(customerDto.getName())
                .setAddress(CustomerCreateParams.Address.builder()
                    .setLine1(customerDto.getAddress().getLine1())
                    .setLine2(customerDto.getAddress().getLine2())
                    .setCity(customerDto.getAddress().getCity())
                    .setState(customerDto.getAddress().getState())
                    .setPostalCode(customerDto.getAddress().getPostalCode())
                    .setCountry(customerDto.getAddress().getCountry())
                .build())
            .build();

            return Customer.create(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public CustomerDto getStripeCustomer(String customerId) {
        try {
            Customer c = Customer.retrieve(customerId);
            if (c == null) {
                return null;
            }
            CustomerDto customerDto = new CustomerDto();
            customerDto.setId(customerId);
            customerDto.setEmail(c.getEmail());
            customerDto.setName(c.getName());
            if (c.getAddress() != null) {
                AddressDto addressDto = new AddressDto();
                addressDto.setLine1(c.getAddress().getLine1());
                addressDto.setLine2(c.getAddress().getLine2());
                addressDto.setCity(c.getAddress().getCity());
                addressDto.setState(c.getAddress().getState());
                addressDto.setPostalCode(c.getAddress().getPostalCode());
                addressDto.setCountry(c.getAddress().getCountry());
                customerDto.setAddress(addressDto);
            }
            customerDto.setPhone(c.getPhone() != null ? c.getPhone() : null);
            return customerDto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public PaymentIntent createPaymentIntent(PaymentDto dto, String paymentMethod) {
        try {
            PaymentIntentCreateParams.Builder builder = PaymentIntentCreateParams.builder()
                .setAmount(dto.getAmount())
                .setCurrency(dto.getCurrency())
                .setPaymentMethod(paymentMethod)
                .setConfirm(true)
                .setCustomer(dto.getCustomerID()) 
            .setSetupFutureUsage(PaymentIntentCreateParams.SetupFutureUsage.OFF_SESSION);

            PaymentIntentCreateParams params = builder.build();
            return PaymentIntent.create(params);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public PaymentIntent getPaymentIntent(String paymentIntentId) {
        try {
            return PaymentIntent.retrieve(paymentIntentId);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
