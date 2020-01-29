package uk.nhs.imperial.common.contacts.restapi.services;

import java.awt.dnd.InvalidDnDOperationException;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;
import javax.naming.Name;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.stereotype.Service;
import uk.nhs.imperial.common.contacts.restapi.domain.AddSecretaryRequest;
import uk.nhs.imperial.common.contacts.restapi.domain.Contact;
import uk.nhs.imperial.common.contacts.restapi.domain.ContactSearchRequest;
import uk.nhs.imperial.common.contacts.restapi.domain.ContactSummary;
import uk.nhs.imperial.common.contacts.restapi.domain.CreateContactRequest;
import uk.nhs.imperial.common.contacts.restapi.model.ContactEntry;
import uk.nhs.imperial.common.contacts.restapi.repositories.ContactRepository;

@Service
public class ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactService.class);

    private ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    public List<Contact> getAllContacts() {

        logger.debug("Getting all contacts");
        final Iterable<ContactEntry> results = contactRepository.findAll();

        final List<Contact> list = new ArrayList<>();

        results.forEach(r -> list.add(new Contact(r)));

        logger.debug(String.format("Found %d contacts", list.size()));

        return list;
    }

    public List<ContactSummary> searchContacts(ContactSearchRequest request) {

        logger.debug(String.format("Params:%s", request.toString()));

        final List<ContactEntry> results = contactRepository.searchContacts(request);

        logger.debug(String.format("Found %d records", results.size()));

        final List<ContactSummary> contacts = new ArrayList<>(results.size());

        results.forEach(c -> {
            contacts.add(new ContactSummary(c));
        });

        return contacts;
    }

    public Optional<Contact> getContact(String uid) {

        logger.debug(String.format("Getting contact for: %s", uid));

        final Optional<ContactEntry> contact = contactRepository.findFirstByUid(uid);

        if(contact.isEmpty()) {
            logger.warn(String.format("Uid: %s not found", uid));

            return Optional.empty();
        } else {
            return Optional.of(new Contact(contact.get()));
        }
    }

    public Contact createContact(CreateContactRequest request) throws IllegalArgumentException, InvalidNameException {

        if(request == null) {
            throw new IllegalArgumentException("Request must not be null");
        }

        logger.debug(String.format("Create Contact Request:%s", request));

        final ContactEntry entry = request.toEntry();

        logger.debug(String.format("Creating record for %s", entry.toString()));

        final ContactEntry newEntry = contactRepository.save(entry);

        logger.debug(String.format("Created %s ", newEntry));

        return new Contact(newEntry);
    }

    public void deleteContact(String uid) throws NoSuchObjectException {

        logger.debug(String.format("Deleting entry for: '%s'",uid));

        Optional<ContactEntry> entry = contactRepository.findFirstByUid(uid);

        if(entry.isPresent()) {
            contactRepository.delete(entry.get());

            logger.debug("Entry deleted");
        } else {

            logger.warn(String.format("Uid doesnt exist to be deleted:%s", uid));

            throw new NoSuchObjectException(String.format("No data found for %s", uid));
        }
    }

    public List<ContactSummary> getSecretary(final String consultantUid) throws NoSuchObjectException {

        logger.debug(String.format("Getting secretaries for: %s", consultantUid));

        final Optional<ContactEntry> consultantEntry = contactRepository.findFirstByUid(consultantUid);

        if(consultantEntry.isPresent()) {

            ContactEntry consultant = consultantEntry.get();

            logger.debug(String.format("Got consultant: %s", consultant));

            final List<ContactSummary> secretaries = new ArrayList<>();

            final List<Name> secretaryDn = consultant.getSecretary();

            logger.debug(String.format("Found %d secretary DNs", secretaryDn.size()));

            secretaryDn.forEach(sec -> {

                final Optional<ContactEntry> secretary = contactRepository.findById(sec);

                if(secretary.isPresent()) {
                    secretaries.add(new ContactSummary(secretary.get()));
                } else {
                    logger.error(String.format("Entry :%s contains bad secretary dn: %s", consultant.getDn(), sec));
                }
            });

            return secretaries;

        } else {
            throw new NoSuchObjectException(String.format("No consultant found for %s", consultantUid));
        }
    }

    public ContactSummary addSecretary(final String consultantUid, final AddSecretaryRequest request) throws NoSuchObjectException, InvalidDnDOperationException {

        logger.debug(String.format("%s: adding secretary: %s", consultantUid, request));

        final Optional<ContactEntry> consultantEntry = contactRepository.findFirstByUid(consultantUid);

        if(consultantEntry.isEmpty()) {
            throw new NoSuchObjectException(String.format("Consultant not found:%s", consultantUid));
        }

        final ContactEntry consultant = consultantEntry.get();

        logger.debug(String.format("Consultant found:%s", consultant.toString()));

        Name secretaryDn = LdapNameBuilder.newInstance(request.getSecretary()).build();

        final List<Name> secretariesDns = consultant.getSecretary();

        if(secretariesDns != null) {
            secretariesDns.forEach(s -> {
                if(s.equals(secretaryDn)) {
                    throw new InvalidDnDOperationException(String.format("Secretary already exists for Consultant:%s", request.getSecretary()));
                }
            });
        }

        consultant.getSecretary().add(secretaryDn);

        contactRepository.save(consultant);

        return new ContactSummary(consultant);
    }

    private String getUniqueId(String uid) {

        boolean isUnique = false;

        String string = uid;

        do {

            isUnique = isUniqueId(string);

            if(!isUnique) {

                // TODO generate a unique id.  -  add a number to the end..
            }

        } while (!isUnique);

        return uid;
    }

    private boolean isUniqueId(String uid) {

        logger.debug(String.format("Checking %s for uniqueness", uid));

        Optional<ContactEntry> contactEntry = contactRepository.findFirstByUid(uid);

        logger.debug(String.format("%s is unique? %s", uid, contactEntry.isEmpty()));

        return contactEntry.isEmpty();
    }

    public byte[] getContactPhoto(String uid) throws NoSuchObjectException {

        logger.debug(String.format("Getting photo for contact: %s", uid));

        byte[] photo = contactRepository.getPhoto(uid);

        return photo;
    }
}
