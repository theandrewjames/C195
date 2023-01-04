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

    /**
     * getter of customer id
     * @return customer id
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /**
     * setter of customer id
     * @param customerId
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /**
     * getter of customer name
     * @return customer name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * setter of customer name
     * @param customerName
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * getter of customer phone
     * @return customer phone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * setter of customer phone
     * @param customerPhone
     */
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    /**
     * getter of customer address
     * @return customer address
     */
    public String getCustomerAddress() { return customerAddress;}

    /**
     * setter of customer address
     * @param customerAddress
     */
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    /**
     * getter of postal code
     * @return postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * setter of postal code
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * getter of division
     * @return division
     */
    public String getDivison() {
        return divison;
    }

    /**
     * setter of division
     * @param divison
     */
    public void setDivison(String divison) {
        this.divison = divison;
    }

    /**
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
