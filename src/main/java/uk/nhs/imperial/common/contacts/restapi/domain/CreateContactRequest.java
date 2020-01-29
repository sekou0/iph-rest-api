package uk.nhs.imperial.common.contacts.restapi.domain;

import uk.nhs.imperial.common.contacts.restapi.model.ContactEntry;

public class CreateContactRequest {

    private String firstName;
    private String lastName;
    private String displayName;
    private String bio;
    private String employeeNumber;
    private String phoneNumber;
    private String emailAddress;
    private String mobileNumber;
    private String userId;

    public String toString() {
        return String.format("[userId:%s firstName:%s lastName:%s displayName:%s]",
                userId, firstName, lastName, displayName );
    }

    public ContactEntry toEntry() {
        final ContactEntry entry = new ContactEntry();

        entry.setUid(this.userId);
        entry.setFirstName(this.firstName);
        entry.setLastName(this.lastName);
        entry.setDisplayName(this.displayName);
        entry.setBio(this.bio);
        entry.setEmployeeNumber(this.employeeNumber);
        entry.setCommonName(this.userId);

        return entry;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(final String bio) {
        this.bio = bio;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(final String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(final String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(final String userId) {
        this.userId = userId;
    }
}
