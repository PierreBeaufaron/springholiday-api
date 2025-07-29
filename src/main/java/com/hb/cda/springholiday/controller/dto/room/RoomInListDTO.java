package com.hb.cda.springholiday.controller.dto.room;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoomInListDTO {
    private String id;
    private String number;
    private Double price;
    private Integer capacity;
}
