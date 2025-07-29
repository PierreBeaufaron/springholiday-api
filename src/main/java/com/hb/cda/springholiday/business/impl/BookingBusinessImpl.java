package com.hb.cda.springholiday.business.impl;

import com.hb.cda.springholiday.business.BookingBusiness;
import com.hb.cda.springholiday.business.exception.BookingException;
import com.hb.cda.springholiday.entity.Booking;
import com.hb.cda.springholiday.entity.Room;
import com.hb.cda.springholiday.entity.User;
import com.hb.cda.springholiday.repository.BookingRepository;
import com.hb.cda.springholiday.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BookingBusinessImpl implements BookingBusiness {
    private final RoomRepository roomRepository;
    private final BookingRepository bookingRepository;

    public BookingBusinessImpl(RoomRepository roomRepository,  BookingRepository bookingRepository) {
        this.roomRepository = roomRepository;
        this.bookingRepository = bookingRepository;
    }

    @Override
    public List<Room> getAvailableRooms(LocalDate start, LocalDate end) {
        return roomRepository.findAvailable(start, end);
    }

    @Override
    public Booking bookRooms(Booking booking, User user) {
        int totalCapacity = 0;
        double totalPrice = 0.0;
        long dayCount = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());

        for (Room room : booking.getRooms()) {
            Room persistedRoom = roomRepository.findById(room.getId())
                    .orElseThrow(() -> new BookingException("Room " + room.getNumber() + " not found"));
            if(!roomRepository.isRoomAvailable(persistedRoom, booking.getStartDate(), booking.getEndDate())) {
                throw new BookingException("Room " + persistedRoom.getNumber() + " is not available for given dates");
            }
            totalCapacity += persistedRoom.getCapacity();
            totalPrice += persistedRoom.getPrice()*dayCount;
        }
        if(booking.getGuestCount() > totalCapacity) {
            throw new BookingException("Excessive guest count for selected rooms");
        }
        booking.setUser(user);
        booking.setTotal(totalPrice);
        bookingRepository.save(booking);
        return booking;
    }
}
