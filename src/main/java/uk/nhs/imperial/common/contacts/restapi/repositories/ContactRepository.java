package uk.nhs.imperial.common.contacts.restapi.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.ldap.repository.LdapRepository;
import uk.nhs.imperial.common.contacts.restapi.model.ContactEntry;

public interface ContactRepository extends LdapRepository<ContactEntry>, ContactRepositoryCustom {

    List<ContactEntry> findAllOrOrderByLastName();
    Optional<ContactEntry> findFirstByUid(String uid);
    List<ContactEntry> findAllByLastNameOrderByLastName(String lastName);

}
