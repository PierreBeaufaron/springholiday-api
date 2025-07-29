package com.hb.cda.springholiday.controller.dto;

import com.hb.cda.springholiday.controller.dto.room.RoomInListDTO;
import com.hb.cda.springholiday.entity.Room;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface RoomMapper {

    Room convertToRoom(RoomInListDTO dto);
    RoomInListDTO convertToRoomInListDTO(Room room);
}
