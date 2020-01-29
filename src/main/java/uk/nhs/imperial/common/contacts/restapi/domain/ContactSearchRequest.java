package uk.nhs.imperial.common.contacts.restapi.domain;

public class ContactSearchRequest {

    private String firstName;
    private String lastName;
    private String contactType;
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public static class Builder {

        private String firstName;
        private String lastName;
        private String contactType;

        public Builder withFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder withLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder withContactType(String contactType) {
            this.contactType = contactType;
            return this;
        }

        public ContactSearchRequest build() {

            final ContactSearchRequest contactSearchRequest = new ContactSearchRequest();

            contactSearchRequest.setFirstName(this.firstName);
            contactSearchRequest.setLastName(this.lastName);
            contactSearchRequest.setContactType(this.contactType);

            return contactSearchRequest;
        }
    }
}
