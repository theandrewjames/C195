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
    private String contactName;

    public Appointments(Integer apptID, String apptTitle, String apptDescription,String apptLocation,String apptType,
                        LocalDateTime startTime,LocalDateTime endTime,Integer customerID,Integer userID,String contactName) {
        this.apptID = apptID;
        this.apptTitle = apptTitle;
        this.apptDescription = apptDescription;
        this.apptLocation = apptLocation;
        this.apptType = apptType;
        this.startTime = startTime;
        this.endTime = endTime;
        this.customerID = customerID;
        this.userID = userID;
        this.contactName = contactName;
    }

    /**
     * getter of appt ID
     * @return appt ID
     */
    public Integer getApptID() { return apptID;};

    /**
     * getter of appt title
     * @return appt tile
     */
    public String getApptTitle() { return apptTitle;};

    /**
     * getter of appt description
     * @return appt description
     */
    public String getApptDescription() {return apptDescription;};

    /**
     * getter of appt location
     * @return appt location
     */
    public String getApptLocation() {return apptLocation;};

    /**
     * getter of appt type
     * @return appt type
     */
    public String getApptType() {return apptType;};

    /**
     * getter of start time
     * @return start time of appt
     */
    public LocalDateTime getStartTime() {return startTime;};

    /**
     * getter of end time
     * @return end time of appt
     */
    public LocalDateTime getEndTime() {return endTime;};

    /**
     * getter of customer ID
     * @return customer ID
     */
    public Integer getCustomerID() {return customerID;};

    /**
     * getter of user ID
     * @return user ID
     */
    public Integer getUserID() {return userID;};

    /**
     * getter of contact ID
     * @return contact ID
     */
    public String getContactName() {return contactName;};
}
