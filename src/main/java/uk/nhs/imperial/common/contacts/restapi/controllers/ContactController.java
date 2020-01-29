package uk.nhs.imperial.common.contacts.restapi.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.rmi.NoSuchObjectException;
import java.util.List;
import java.util.Optional;

import javax.naming.InvalidNameException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.nhs.imperial.common.contacts.restapi.domain.AddSecretaryRequest;
import uk.nhs.imperial.common.contacts.restapi.domain.Contact;
import uk.nhs.imperial.common.contacts.restapi.domain.ContactSearchRequest;
import uk.nhs.imperial.common.contacts.restapi.domain.ContactSearchResponse;
import uk.nhs.imperial.common.contacts.restapi.domain.ContactSummary;
import uk.nhs.imperial.common.contacts.restapi.domain.CreateContactRequest;
import uk.nhs.imperial.common.contacts.restapi.domain.ErrorResponse;
import uk.nhs.imperial.common.contacts.restapi.services.ContactService;

@RestController
@RequestMapping(path = "contacts")
public class ContactController {

    private static final Logger logger = LoggerFactory.getLogger(ContactController.class);

    private ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ContactSearchResponse searchContacts(
            @RequestParam(value = "firstName", required = false) String firstName,
            @RequestParam(value = "lastName", required = false) String lastName,
            @RequestParam(value = "contactType", required = false) String contactType
    ) {

        logger.info("Searching all contacts");

        final ContactSearchRequest request =
                new ContactSearchRequest.Builder()
                        .withFirstName(firstName)
                        .withLastName(lastName)
                        .withContactType(contactType)
                        .build();

        final List<ContactSummary> results = contactService.searchContacts(request);

        logger.info(String.format("Got %d contacts", results.size()));

        return new ContactSearchResponse(results);
    }

    @RequestMapping(method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Contact createContact(@RequestBody CreateContactRequest request) throws InvalidNameException, NoSuchObjectException {

        logger.info(String.format("Creating new contacts:%s", request.toString()));

        final Contact contact = contactService.createContact(request);

        logger.info(String.format("New contact created: %s", contact));

        contact.add(linkTo(methodOn(ContactController.class).getContact(contact.getUid())).withSelfRel());

        return contact;
    }

    @RequestMapping(path = "/{uid}/secretary", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ContactSearchResponse getSecretary(
            @PathVariable("uid") String uid) throws NoSuchObjectException {

        logger.info(String.format("Getting secretary for :%s", uid));

        final List<ContactSummary> secretaries = contactService.getSecretary(uid);

        logger.info(String.format("Got %d secretaries", secretaries.size()));

        for(ContactSummary sec : secretaries) {
            sec.add(linkTo(methodOn(ContactController.class).getContact(sec.getUid())).withSelfRel());
        }

        ContactSearchResponse response = new ContactSearchResponse(secretaries);

        response.add(linkTo(methodOn(ContactController.class).getSecretary(uid)).withSelfRel());

        return response;
    }

    @RequestMapping(path = "/{uid}/secretary", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ContactSummary addSecretary(
            @PathVariable("uid") String uid,
            @RequestBody AddSecretaryRequest request) throws NoSuchObjectException {

        logger.info(String.format("Creating new contacts:%s", request.toString()));

        final ContactSummary contact = contactService.addSecretary(uid, request);

        logger.info(String.format("New contact created: %s", contact));

        contact.add(linkTo(methodOn(ContactController.class).getContact(contact.getUid())).withSelfRel());

        return contact;
    }

    @RequestMapping(path = "/{uid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public Contact getContact(@PathVariable("uid") String uid) throws NoSuchObjectException  {

        logger.info(String.format("Finding contact for %s", uid));

        final Optional<Contact> contact = contactService.getContact(uid);

        if(contact.isPresent()) {

            final Contact result = contact.get();

            result.add(linkTo(methodOn(ContactController.class).getContact(uid)).withSelfRel());
            result.add(linkTo(ContactController.class).slash(uid).slash("photo").withRel("photo"));

            return result;

        } else {
            throw new NoSuchObjectException(String.format("Uid '%s' could not be found", uid));
        }
    }

    @RequestMapping(path = "/{uid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteContact(@PathVariable("uid") String uid) throws NoSuchObjectException  {

        logger.info(String.format("Deleting contact for %s", uid));

        contactService.deleteContact(uid);
    }

    @RequestMapping(path = "/{uid}/photo", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public byte[] getContactPhoto(@PathVariable("uid") String uid) throws NoSuchObjectException  {

        logger.info(String.format("Finding Photo for %s", uid));

        return contactService.getContactPhoto(uid);
    }

    @ExceptionHandler(NoSuchObjectException.class)
    public ResponseEntity<ErrorResponse> badRequestErrorResponse(NoSuchObjectException exception) {

        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.BAD_REQUEST.name());
        errorResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidNameException.class)
    public ResponseEntity<ErrorResponse> generalErrorResponse(Exception exception) {

        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setHttpStatus(HttpStatus.INTERNAL_SERVER_ERROR.name());
        errorResponse.setMessage(exception.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
