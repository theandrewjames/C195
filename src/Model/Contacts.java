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

    public Integer getContactId() {return contactId;};
    public String getContactName() {return contactName;};
    public String getContactEmail() {return contactEmail;};
}
