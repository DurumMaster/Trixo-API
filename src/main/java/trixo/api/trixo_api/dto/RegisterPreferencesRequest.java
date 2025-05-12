package trixo.api.trixo_api.dto;

import java.util.List;

public class RegisterPreferencesRequest {
    private String userID;
    private List<String> preferences;

    // Getters y setters
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public List<String> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<String> preferences) {
        this.preferences = preferences;
    }
}
