package uk.nhs.imperial.common.contacts.restapi;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.naming.NamingException;
import javax.naming.ldap.LdapName;

import uk.nhs.imperial.common.contacts.restapi.domain.Contact;
import uk.nhs.imperial.common.contacts.restapi.domain.CreateContactRequest;
import uk.nhs.imperial.common.contacts.restapi.model.ContactEntry;

public class MockData {


    public final static Optional<ContactEntry> getPersistedContactEntry() throws NamingException {
        final ContactEntry contactEntry = new ContactEntry();

        contactEntry.setBio("this is the bio");
        contactEntry.setContactType("CONSULTANT");
        contactEntry.setDisplayName("Display Name");
        contactEntry.setDn(new LdapName("uid=tester"));
        contactEntry.setEmployeeNumber("123456");
        contactEntry.setFirstName("Jack");
        contactEntry.setLastName("Jones");
        contactEntry.setUid("tester");

        return Optional.of(contactEntry);
    }
    public final static List<ContactEntry> getPersistedContactEntries() throws NamingException{
        return Arrays.asList(getPersistedContactEntry().get());
    }

    public final static CreateContactRequest getCreateContactRequest() {
        final CreateContactRequest request = new CreateContactRequest();
        request.setBio("Bio string");
        request.setDisplayName("display name");
        request.setEmailAddress("user@emailAddress.com");
        request.setEmployeeNumber("123456");
        request.setFirstName("firstName");
        request.setLastName("lastName");
        request.setMobileNumber("079012345678");
        request.setPhoneNumber("02084059030");

        return request;
    }

    public final static Contact getMockContact() throws Exception{
        return new Contact(getPersistedContactEntry().get());
    }

    public final static List<Contact> getMockContacts() throws Exception{
        return Arrays.asList(getMockContact());
    }
}
