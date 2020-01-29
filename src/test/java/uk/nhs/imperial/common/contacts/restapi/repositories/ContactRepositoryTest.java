package uk.nhs.imperial.common.contacts.restapi.repositories;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import uk.nhs.imperial.common.contacts.restapi.model.ContactEntry;

@SpringBootTest
// @ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    public void findFirstByUid() {

        final Optional<ContactEntry> entry = contactRepository.findFirstByUid("admin");

        assertNotNull(entry);
        assertTrue(entry.isPresent());
    }

    @Test
    public void findAllByLastNameOrderByLastName() {
    }
}
