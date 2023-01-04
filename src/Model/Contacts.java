package Model;

import javafx.collections.ObservableList;

public class Contacts {
    private Integer contactId;
    private String contactName;
    private String contactEmail;

    public Contacts(Integer contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * Getter of contact ID
     * @return contactID
     */
    public Integer getContactId() {return contactId;};

    /**
     * Getter of customer name
     * @return customer name
     */
    public String getContactName() {return contactName;};

    /**
     * getter of contact email
     * @return contact email
     */
    public String getContactEmail() {return contactEmail;};
}
