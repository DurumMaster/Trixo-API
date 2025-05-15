package trixo.api.trixo_api.entities;

public class Report {
    private String userId;
    private String reason;
    private String status;

    public Report(){}

    public Report(String userId, String reason, String status) {
        this.userId = userId;
        this.reason = reason;
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public String getStatus() {
        return status;
    }

    public String getUserId() {
        return userId;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
