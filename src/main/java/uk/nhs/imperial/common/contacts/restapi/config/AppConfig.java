package uk.nhs.imperial.common.contacts.restapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;

@Configuration
@EnableLdapRepositories(basePackages = "uk.nhs.imperial.common.contacts.restapi" )
public class AppConfig {
}
