package com.hb.cda.springholiday.controller.dto.booking;

import com.hb.cda.springholiday.controller.dto.room.RoomInListDTO;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
public class AddBookingDTO {
    @NotNull
    @Positive
    private Integer guestCount;
    @NotEmpty
    private List<RoomInListDTO> rooms;
    @NotNull
    @FutureOrPresent
    private LocalDate startDate;
    @NotNull
    @Future
    private LocalDate endDate;
}
