package uk.nhs.imperial.common.contacts.restapi.controllers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import uk.nhs.imperial.common.contacts.restapi.services.ContactService;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static uk.nhs.imperial.common.contacts.restapi.MockData.getMockContact;
import static uk.nhs.imperial.common.contacts.restapi.MockData.getMockContacts;

import java.rmi.NoSuchObjectException;
import java.util.Optional;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactController.class)
class ContactControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContactService contactService;

    @Test
    void getAllContacts() throws Exception {

        when(contactService.getAllContacts()).thenReturn(getMockContacts());

        mvc.perform(get("/contacts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void getContact() throws Exception{
        when(contactService.getContact("1234")).thenReturn(Optional.of(getMockContact()));

        mvc.perform(get("/contacts/1234"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void getContactNoId() throws Exception{
        when(contactService.getContact("1234")).thenReturn(Optional.empty());

        mvc.perform(get("/contacts/1234"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}
