package Model;

public class Customers {
    private Integer customerId;
    private String customerName;
    private String customerPhone;
    private String customerAddress;
    private String postalCode;
    private String divison;
    private String country;

    public Customers(Integer customerId, String customerName, String customerPhone,
                     String customerAddress, String postalCode, String division, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.customerAddress = customerAddress;
        this.postalCode = postalCode;
        this.divison = division;
        this.country = country;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getCustomerAddress() { return customerAddress;}

    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getDivison() {
        return divison;
    }

    public void setDivison(String divison) {
        this.divison = divison;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
