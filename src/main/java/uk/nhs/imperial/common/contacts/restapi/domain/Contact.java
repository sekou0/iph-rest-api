package uk.nhs.imperial.common.contacts.restapi.domain;

import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;
import uk.nhs.imperial.common.contacts.restapi.model.ContactEntry;

public class Contact extends RepresentationModel<Contact> {

    private String dn;

    private String uid;

    private String firstName;
    private String lastName;
    private String displayName;
    private String bio;
    private String contactType;
    private String employeeNumber;

    public Contact() {

    }

    public Contact(ContactEntry entry) {

        if(entry.getDn() != null) {
            this.dn = entry.getDn().toString();
        }

        this.uid = entry.getUid();
        this.displayName = entry.getDisplayName();
        this.firstName = entry.getFirstName();
        this.lastName = entry.getLastName();
        this.bio = entry.getBio();
        this.contactType = entry.getContactType();
        this.employeeNumber = entry.getEmployeeNumber();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final Contact contact = (Contact) o;
        return Objects.equals(dn, contact.dn) &&
                uid.equals(contact.uid);
    }

    @Override
    public String toString() {
        return "Contact{" +
                "dn='" + dn + '\'' +
                ", uid='" + uid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", contactType='" + contactType + '\'' +
                ", employeeNumber='" + employeeNumber + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dn, uid);
    }

    public String getUid() {
        return uid;
    }

    public void setUid(final String uid) {
        this.uid = uid;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(final String dn) {
        this.dn = dn;
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

    public String getContactType() {
        return contactType;
    }

    public void setContactType(final String contactType) {
        this.contactType = contactType;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(final String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }
}
