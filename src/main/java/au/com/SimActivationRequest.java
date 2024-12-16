package au.com;

// Request payload for SIM activation
public class SimActivationRequest {
    private String iccid;
    private String customerEmail;

    // Getters and Setters
    public String getIccid() {
        return iccid;
    }
    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
}

// Response from the actuator

