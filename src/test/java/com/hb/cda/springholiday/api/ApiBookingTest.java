package com.hb.cda.springholiday.api;

import com.hb.cda.DataLoader;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
@Transactional
public class ApiBookingTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    EntityManager em;

    List<String> roomsId;

    @BeforeEach
    void setup() throws Exception {
        DataLoader dl = new DataLoader();
        dl.setEm(em);
        dl.run();
        roomsId = dl.roomsId;
    }

    @Test
    @WithUserDetails(value = "user@test.com", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    void postOnBookingShouldAddBooking() throws Exception {
        mvc.perform(post("/api/booking")
                .header("Content-type", MediaType.APPLICATION_JSON)
                .content("""
                        {
                           "startDate":"2025-11-10",
                           "endDate":"2025-12-13",
                           "guestCount":4,
                           "rooms": [
                               {"id":"%s"},
                               {"id":"%s"}
                           ]
                        }
                    """.formatted(roomsId.get(0), roomsId.get(1))))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.total").isNumber());
    }
}
