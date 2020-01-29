package uk.nhs.imperial.common.contacts.restapi.domain;

public class ErrorResponse {
    private String httpStatus;
    private String message;

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(final String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
