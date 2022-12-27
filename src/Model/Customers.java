package Model;

public class Customers {
    private Integer customerId;
    private String customerName;
    private String customerPhone;

    public Customers(Integer customerId, String customerName, String customerPhone) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
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
}
