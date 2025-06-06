package trixo.api.trixo_api.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stripe.model.Customer;
import com.stripe.model.PaymentIntent;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.PaymentIntentCreateParams;

import trixo.api.trixo_api.dto.AddressDto;
import trixo.api.trixo_api.dto.CustomerDto;
import trixo.api.trixo_api.dto.PaymentDto;
import trixo.api.trixo_api.entities.PaymentMethod;
import trixo.api.trixo_api.repositories.PaymentRepository;

@Service
public class StripeService {

    @Autowired
    private PaymentRepository paymentRepository;
    
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
                .setAmount((long) dto.getAmount())
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

    public PaymentIntent getPaymentIntent(String customerID) {
        try {
            PaymentMethod pm = paymentRepository.findByCliente_StripeCustomerId(customerID);
            return PaymentIntent.retrieve(pm.getStripePaymentMethodId());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateCustomer(String customerId, CustomerDto customerDto) {
        try {
            Customer customer = Customer.retrieve(customerId);
            Map<String, Object> params = new HashMap<>();
            params.put("email", customerDto.getEmail());
            params.put("name", customerDto.getName());
            params.put("phone", customerDto.getPhone());
            if (customerDto.getAddress() != null) {
                Map<String, Object> addressParams = new HashMap<>();
                addressParams.put("line1", customerDto.getAddress().getLine1());
                addressParams.put("line2", customerDto.getAddress().getLine2());
                addressParams.put("city", customerDto.getAddress().getCity());
                addressParams.put("state", customerDto.getAddress().getState());
                addressParams.put("postal_code", customerDto.getAddress().getPostalCode());
                addressParams.put("country", customerDto.getAddress().getCountry());
                params.put("address", addressParams);
            } else {
                params.put("address", null);
            }

            customer.update(params);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}

