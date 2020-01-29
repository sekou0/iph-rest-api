package uk.nhs.imperial.common.contacts.restapi.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static uk.nhs.imperial.common.contacts.restapi.MockData.getCreateContactRequest;
import static uk.nhs.imperial.common.contacts.restapi.MockData.getPersistedContactEntries;
import static uk.nhs.imperial.common.contacts.restapi.MockData.getPersistedContactEntry;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.nhs.imperial.common.contacts.restapi.domain.Contact;
import uk.nhs.imperial.common.contacts.restapi.domain.CreateContactRequest;
import uk.nhs.imperial.common.contacts.restapi.repositories.ContactRepository;

@RunWith(MockitoJUnitRunner.class)
public class ContactServiceTest {

    @BeforeEach
    void setUp() {
    }

    @InjectMocks
    private ContactService contactService;

    @Mock
    private ContactRepository contactRepository;

    @Test
    public void getAllContacts() throws Exception {

        when(contactRepository.findAll()).thenReturn(getPersistedContactEntries());

        List<Contact> results = contactService.getAllContacts();

        assertNotNull(results);
        assertThat(results.size(), is(1));
    }

    @Test
    public void getContact() throws Exception {

        when(contactRepository.findFirstByUid("tester")).thenReturn(getPersistedContactEntry());
        Optional<Contact> contact = contactService.getContact("tester");

        assertNotNull(contact);
        assertTrue(contact.isPresent());
    }

    @Test
    public void getContactNoUid() throws Exception {

        when(contactRepository.findFirstByUid("xxx")).thenReturn(Optional.empty());
        Optional<Contact> contact = contactService.getContact("xxx");

        assertNotNull(contact);
        assertTrue(contact.isEmpty());
    }

    @Test
    public void createContact() throws Exception {

        when(contactRepository.save(any())).thenReturn(getPersistedContactEntry().get());

        final CreateContactRequest request = getCreateContactRequest();

        final Contact contact = contactService.createContact(request);

        assertNotNull(contact);
        assertThat(contact.getUid(), is("tester"));
    }

    @Test
    public void createContactNullParam() throws Exception {

        when(contactRepository.save(any())).thenThrow(new IllegalArgumentException());

        final CreateContactRequest request = getCreateContactRequest();

        final Contact contact = contactService.createContact(null);

        assertNotNull(contact);
        assertThat(contact.getUid(), is("tester"));
    }

    @Test(expected = NoSuchObjectException.class)
    public void deleteRecordNotFound() throws Exception {

        when(contactRepository.findFirstByUid(any())).thenReturn(Optional.empty());

        contactService.deleteContact("xxx");
    }
}
