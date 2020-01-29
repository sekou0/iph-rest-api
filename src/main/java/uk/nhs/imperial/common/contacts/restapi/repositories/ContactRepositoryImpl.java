package uk.nhs.imperial.common.contacts.restapi.repositories;

import static org.springframework.ldap.query.LdapQueryBuilder.query;
import static uk.nhs.imperial.common.contacts.restapi.tools.ApiTools.generateContactDN;

import java.awt.dnd.InvalidDnDOperationException;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Name;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.ContainerCriteria;
import org.springframework.ldap.query.SearchScope;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import uk.nhs.imperial.common.contacts.restapi.domain.ContactSearchRequest;
import uk.nhs.imperial.common.contacts.restapi.model.ContactEntry;

@Repository
public class ContactRepositoryImpl implements ContactRepositoryCustom {

    private static final Logger logger = LoggerFactory.getLogger(ContactRepositoryImpl.class);

    private LdapTemplate ldapTemplate;

    @Autowired
    public ContactRepositoryImpl(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

    @Override
    public List<ContactEntry> searchContacts(final ContactSearchRequest request) {

        logger.trace(String.format("Searching with:%s", request.toString()));

        ContainerCriteria criteria = query()
                .base("dc=contacts,dc=imperial,dc=nhs,dc=uk")
                .searchScope(SearchScope.ONELEVEL)
                .where("objectClass").is("person");

        if(!StringUtils.isEmpty(request.getFirstName())) {
            criteria.and("givenName").like(String.format("%s*", request.getFirstName()));
        }

        if(!StringUtils.isEmpty(request.getLastName())) {
            criteria.and("sn").like(String.format("%s*", request.getFirstName()));
        }

        final List<ContactEntry> results = new ArrayList<>();

        ldapTemplate.search(criteria, new AttributesMapper<ContactEntry>() {
            @Override
            public ContactEntry mapFromAttributes(final Attributes attributes) throws NamingException {

                final ContactEntry contact = new ContactEntry();

                contact.setUid(attributes.get("uid").get().toString());
                contact.setDn(generateContactDN(contact.getUid()));
                contact.setFirstName(attributes.get("givenName").get().toString());
                contact.setLastName(attributes.get("sn").get().toString());
                contact.setDisplayName(attributes.get("displayName").get().toString());

                Attribute employeeTypeAttr = attributes.get("employeeType");

                if(employeeTypeAttr != null) {
                    contact.setContactType(attributes.get("employeeType").get().toString());
                }

                results.add(contact);

                return contact;
            }
        });

        return results;
    }

    @Override
    public byte[] getPhoto(final String uid) throws NoSuchObjectException {

        logger.debug(String.format("Getting photo for: %s", uid));

        try {
            final DirContextOperations ctx =  ldapTemplate.lookupContext(String.format("uid=%s,dc=contacts,dc=imperial,dc=nhs,dc=uk", uid));

            byte[] value = (byte[])ctx.getObjectAttribute("jpegPhoto");

            return value;
        } catch (NameNotFoundException e) {
            logger.warn(String.format("Uid %s not found", uid));
            throw new NoSuchObjectException(String.format("Uid %s not found", uid));
        }
    }
}
