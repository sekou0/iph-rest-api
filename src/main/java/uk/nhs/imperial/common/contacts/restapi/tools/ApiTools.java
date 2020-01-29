package uk.nhs.imperial.common.contacts.restapi.tools;

import javax.naming.Name;

import org.springframework.ldap.support.LdapNameBuilder;

public class ApiTools {

    public final static Name generateContactDN(String uid) {

        return LdapNameBuilder.newInstance("dc=imperial,dc=nhs,dc=uk").add("dc", "contacts").add("uid", uid).build();
    }
}
