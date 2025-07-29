package com.hb.cda.springholiday.business;

import java.time.LocalDate;
import java.util.List;

import com.hb.cda.springholiday.entity.Booking;
import com.hb.cda.springholiday.entity.Room;
import com.hb.cda.springholiday.entity.User;

public interface BookingBusiness {
    /**
     * Renvoie la liste des chambres disponibles sur une période donnée
     * @param start date de début de période souhaitée
     * @param end date de fin de période souhaitée
     * @return La liste des chambres
     */

    List<Room> getAvailableRooms(LocalDate start, LocalDate end);
    /**
     * Méthode qui va vérifier si toutes les rooms à réserver sont disponibles sur la
     * période souhaitée, et si oui, créer une instance de Booking pour le User avec
     * les dates indiquées, calculer le total, et pourquoi pas vérifier que le guestCount
     * match avec les chambres souhaitées, et assigner le user au booking avant de le persister
     * @param booking Un booking non persisté avec la date de début, de fin, la liste des chambres, et le guestCount
     * @param user le user qui fait la réservation
     * @return Le booking persisté
     */
    Booking bookRooms(Booking booking, User user);
}

