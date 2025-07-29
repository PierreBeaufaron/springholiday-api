package com.hb.cda.springholiday.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.hb.cda.DataLoader;
import com.hb.cda.springholiday.entity.Booking;
import com.hb.cda.springholiday.entity.Room;

import jakarta.persistence.EntityManager;

@DataJpaTest
public class RoomRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    RoomRepository roomRepository;

    Room testRoom;

    @BeforeEach
    void setUp() throws Exception {
        DataLoader dl = new DataLoader();
        dl.setEm(em);
        dl.run();
        testRoom = roomRepository.findByNumber("1A").get();
    }

    @Test
    void countRoom() {
        assertNotNull(testRoom);
        assertEquals(4, roomRepository.count());
    }


    @ParameterizedTest
    @MethodSource("roomAvailableSource")
    void isRoomAvailableShouldReturnTrueIfNoBookingForGivenDates(LocalDate start, LocalDate end, boolean expected) {
        assertEquals(expected,
                roomRepository.isRoomAvailable(
                        testRoom,
                        start,
                        end
                )
        );
    }


    @ParameterizedTest
    @MethodSource("findAvailableSource")
    void findAvailableShouldReturnRoomsWithoutBookingForGivenDates(LocalDate start, LocalDate end, int exepectedCount) {

        List<Room> rooms = roomRepository.findAvailable(
                start, end);
        assertEquals(exepectedCount, rooms.size());

    }

    static Stream<Arguments> roomAvailableSource() {
        return Stream.of(
                Arguments.of(LocalDate.of(2026, 1, 10), LocalDate.of(2026, 1, 12), true),
                Arguments.of(LocalDate.of(2025, 8, 8), LocalDate.of(2025, 8, 9), true),
                Arguments.of(LocalDate.of(2025, 8, 10), LocalDate.of(2025, 8, 11), false),
                Arguments.of(LocalDate.of(2025, 8, 11), LocalDate.of(2025, 8, 12), false),
                Arguments.of(LocalDate.of(2025, 8, 10), LocalDate.of(2025, 8, 14), false),
                Arguments.of(LocalDate.of(2025, 8, 10), LocalDate.of(2025, 8, 18), false),
                Arguments.of(LocalDate.of(2025, 8, 13), LocalDate.of(2025, 8, 18), false)
        );
    }



    static Stream<Arguments> findAvailableSource() {
        return Stream.of(
                Arguments.of(LocalDate.of(2026, 1, 10), LocalDate.of(2026, 1, 12), 4),
                Arguments.of(LocalDate.of(2025, 8, 8), LocalDate.of(2025, 8, 9), 4),
                Arguments.of(LocalDate.of(2025, 8, 10), LocalDate.of(2025, 8, 11), 3),
                Arguments.of(LocalDate.of(2025, 8, 11), LocalDate.of(2025, 8, 12), 3),
                Arguments.of(LocalDate.of(2025, 8, 10), LocalDate.of(2025, 8, 14), 3),
                Arguments.of(LocalDate.of(2025, 8, 10), LocalDate.of(2025, 8, 18), 3),
                Arguments.of(LocalDate.of(2025, 8, 13), LocalDate.of(2025, 8, 18), 3),
                Arguments.of(LocalDate.of(2025, 8, 1), LocalDate.of(2025, 8, 30), 2),
                Arguments.of(LocalDate.of(2025, 8, 20), LocalDate.of(2025, 8, 30), 2)
        );
    }
}
