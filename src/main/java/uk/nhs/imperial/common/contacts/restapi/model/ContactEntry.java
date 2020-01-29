package uk.nhs.imperial.common.contacts.restapi.model;

import java.util.List;

import javax.naming.Name;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.DnAttribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

@Entry(base = "dc=contacts,dc=imperial,dc=nhs,dc=uk",
        objectClasses = {"top", "uidObject", "person", "organizationalPerson",  "inetOrgPerson"})
public class ContactEntry {

    @Id
    private Name dn;

    @DnAttribute(value = "uid", index = 4)
    private String uid;

    @Attribute(name = "cn")
    private String commonName;

    @Attribute(name = "sn")
    private String lastName;

    @Attribute(name = "givenName")
    private String firstName;

    @Attribute(name = "displayName")
    private String displayName;

    @Attribute(name = "description")
    private String bio;

    @Attribute(name = "employeeType")
    private String contactType;

    @Attribute(name = "employeeNumber")
    private String employeeNumber;

    @Attribute(name = "secretary")
    private List<Name> secretary;

    public Name getDn() {
        return dn;
    }

    public void setDn(final Name dn) {
        this.dn = dn;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(final String uid) {
        this.uid = uid;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
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

    public String getCommonName() {
        return commonName;
    }

    public void setCommonName(final String commonName) {
        this.commonName = commonName;
    }

    public List<Name> getSecretary() {
        return secretary;
    }

    public void setSecretary(final List<Name> secretary) {
        this.secretary = secretary;
    }
}
