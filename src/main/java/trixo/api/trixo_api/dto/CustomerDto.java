package trixo.api.trixo_api.dto;

public class CustomerDto {
    private String email;
    private String name;
    private boolean gdprConsent;

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
}
