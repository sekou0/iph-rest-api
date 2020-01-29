package uk.nhs.imperial.common.contacts.restapi.domain;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

public class ContactSearchResponse extends RepresentationModel<ContactSearchResponse> {

    private int size;
    private int index;
    private int pageSize;

    private List<ContactSummary> contacts;

    public ContactSearchResponse(List<ContactSummary> contacts) {
        if(contacts != null) {

            this.contacts = contacts;
            this.size = contacts.size();
        } else {
            this.size = 0;
        }
    }

    public int getSize() {
        return size;
    }

    public void setSize(final int size) {
        this.size = size;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(final int pageSize) {
        this.pageSize = pageSize;
    }

    public List<ContactSummary> getContacts() {
        return contacts;
    }

    public void setContacts(final List<ContactSummary> contacts) {
        this.contacts = contacts;
    }
}
