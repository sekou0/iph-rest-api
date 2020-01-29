package uk.nhs.imperial.common.contacts.restapi.domain;

public class AddSecretaryRequest {

    private String secretary;

    @Override
    public String toString() {
        return "AddSecretaryRequest{" +
                "secretary='" + secretary + '\'' +
                '}';
    }

    public String getSecretary() {
        return secretary;
    }

    public void setSecretary(final String secretary) {
        this.secretary = secretary;
    }
}
