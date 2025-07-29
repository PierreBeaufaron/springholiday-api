package com.hb.cda.springholiday.controller.dto.booking;

import com.hb.cda.springholiday.controller.dto.room.RoomInListDTO;
import com.hb.cda.springholiday.controller.dto.user.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class BookingDTO {
    private String id;
    private List<RoomInListDTO> rooms;
    private Integer guestCount;
    private UserDto user;
    private Double total;
    private LocalDate startDate;
    private LocalDate endDate;
}
