package uk.nhs.imperial.common.contacts.restapi.domain;

import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;
import uk.nhs.imperial.common.contacts.restapi.model.ContactEntry;

public class ContactSummary extends RepresentationModel<ContactSummary> {

    private String dn;
    private String uid;
    private String displayName;
    private String firstName;
    private String lastName;
    private String contactType;
    private String title;

    public ContactSummary(ContactEntry entry) {

        if(entry.getDn() != null) {
            this.dn = entry.getDn().toString();
        }

        this.uid = entry.getUid();
        this.displayName = entry.getDisplayName();
        this.firstName = entry.getFirstName();
        this.lastName = entry.getLastName();
        this.contactType = entry.getContactType();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ContactSummary that = (ContactSummary) o;
        return Objects.equals(dn, that.dn) &&
                uid.equals(that.uid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dn, uid);
    }

    @Override
    public String toString() {
        return "ContactSummary{" +
                "dn='" + dn + '\'' +
                ", uid='" + uid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", contactType='" + contactType + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public String getDn() {
        return dn;
    }

    public void setDn(final String dn) {
        this.dn = dn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(final String uid) {
        this.uid = uid;
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

    public String getContactType() {
        return contactType;
    }

    public void setContactType(final String contactType) {
        this.contactType = contactType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
}
