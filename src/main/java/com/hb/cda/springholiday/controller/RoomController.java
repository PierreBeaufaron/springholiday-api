package com.hb.cda.springholiday.controller;

import com.hb.cda.springholiday.controller.dto.room.RoomInListDTO;
import com.hb.cda.springholiday.repository.RoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
public class RoomController {
    private RoomRepository roomRepository;

    @GetMapping("rooms")
    public Page<RoomInListDTO> allAvailableRooms(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam LocalDate start,
            @RequestParam LocalDate end) {

        if (size > 45) {
            size = 45;
        }
        if (page < 1) {
            page = 1;
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        return null;
    }
}
