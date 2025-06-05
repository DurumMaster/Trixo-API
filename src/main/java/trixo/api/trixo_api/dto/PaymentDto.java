package trixo.api.trixo_api.dto;

public class PaymentDto {
    private int amount;
    private String currency;
    private String customerID;

    public PaymentDto() {}

    public int getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }
}
