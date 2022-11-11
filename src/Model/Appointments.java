package Model;

import java.time.LocalDateTime;
import java.util.Locale;


public class Appointments {
    private Integer apptID;
    private String apptTitle;
    private String apptDescription;
    private String apptLocation;
    private String apptType;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Integer customerID;
    private Integer userID;
    private Integer contactID;

    public Appointments(Integer apptID, String apptTitle, String apptDescription,String apptLocation,String apptType,
                        LocalDateTime startTime,LocalDateTime endTime,Integer customerID,Integer userID,Integer contactID) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactID = contactID;
    }
    public Integer getApptID() { return apptID;};
    public String getApptTitle() { return apptTitle;};
    public String getApptDescription() {return apptDescription;};
    public String getApptLocation() {return apptLocation;};
    public String getApptType() {return apptType;};
    public LocalDateTime getStartTime() {return startTime;};
    public LocalDateTime getEndTime() {return endTime;};
    public Integer getCustomerID() {return customerID;};
    public Integer getUserID() {return userID;};
    public Integer getContactID() {return contactID;};
}
