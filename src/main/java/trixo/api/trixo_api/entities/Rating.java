package trixo.api.trixo_api.entities;

public class Rating {
    private int id;
    private String message;
    private double rating;
    private String userID;

    public Rating() {
    }

    public Rating(int id, String message, double valoracion, String userID) {
        this.id = id;
        this.message = message;
        this.rating = valoracion;
        this.userID = userID;
    }

    public int getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }
    
    public String getUserID() {
        return userID;
    }

    public double getRating() {
        return rating;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
