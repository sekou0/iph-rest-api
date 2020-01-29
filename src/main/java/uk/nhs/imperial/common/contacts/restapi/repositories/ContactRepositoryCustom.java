package uk.nhs.imperial.common.contacts.restapi.repositories;

import java.rmi.NoSuchObjectException;
import java.util.List;

import uk.nhs.imperial.common.contacts.restapi.domain.ContactSearchRequest;
import uk.nhs.imperial.common.contacts.restapi.model.ContactEntry;

public interface ContactRepositoryCustom {

    List<ContactEntry> searchContacts(ContactSearchRequest request);
    byte[] getPhoto(String uid) throws NoSuchObjectException;
}
