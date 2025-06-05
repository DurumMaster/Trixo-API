package trixo.api.trixo_api.dto;

public class CustomerDto {
    private String id;
    private String email;
    private String name;
    private boolean gdprConsent;
    private String phone;
    private AddressDto address;

    // Constructor vacío
    public CustomerDto() { }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    public boolean isGdprConsent() {
        return gdprConsent;
    }

    public void setGdprConsent(boolean gdprConsent) {
        this.gdprConsent = gdprConsent;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
}
