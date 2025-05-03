package trixo.api.trixo_api.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.cloud.Timestamp;

import trixo.api.trixo_api.controllers.TimestampDeserializer;
import trixo.api.trixo_api.controllers.TimestampSerializer;

public class User {
    private String id;
    private String username;
    private String password;
    private String email;
    private String avatar_img;

    @JsonDeserialize(using = TimestampDeserializer.class)
    @JsonSerialize(using = TimestampSerializer.class)
    private Timestamp registration_date;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Timestamp getRegistration_date() {
        return registration_date;
    }
    public void setRegistration_date(Timestamp registrationDate) {
        this.registration_date = registrationDate;
    }
    public String getAvatar_img() {
        return avatar_img;
    }
    public void setAvatar_img(String avatarImg) {
        this.avatar_img = avatarImg;
    }

    
}
