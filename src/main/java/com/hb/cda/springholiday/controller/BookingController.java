package com.hb.cda.springholiday.controller;

import com.hb.cda.springholiday.business.BookingBusiness;
import com.hb.cda.springholiday.controller.dto.BookingMapper;
import com.hb.cda.springholiday.controller.dto.booking.AddBookingDTO;
import com.hb.cda.springholiday.controller.dto.booking.BookingDTO;
import com.hb.cda.springholiday.entity.Booking;
import com.hb.cda.springholiday.entity.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
@AllArgsConstructor
public class BookingController {
    private BookingBusiness bookingBusiness;
    private BookingMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured("ROLE_USER") // On peut gérer la sécurité par rôle ici ou dans le SecurityConfig
    public BookingDTO book(@RequestBody @Valid AddBookingDTO dto,
                           @AuthenticationPrincipal User user) {
        Booking booking = bookingBusiness.bookRooms(mapper.covertToEntity(dto), user);
        return mapper.covertToDto(booking);
    }

}
