package com.hb.cda.springholiday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hb.cda.springholiday.entity.Booking;
import com.hb.cda.springholiday.entity.User;


@Repository
public interface BookingRepository extends JpaRepository<Booking,String>{
    List<Booking> findByUser(User user);
}
