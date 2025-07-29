package com.hb.cda.springholiday.controller.dto;

import com.hb.cda.springholiday.controller.dto.booking.AddBookingDTO;
import com.hb.cda.springholiday.controller.dto.booking.BookingDTO;
import com.hb.cda.springholiday.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    BookingDTO covertToDto(Booking booking);
    Booking covertToEntity(AddBookingDTO dto);
}
