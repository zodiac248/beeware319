package com.panpal.Booking;

import org.springframework.data.repository.CrudRepository;
import java.util.Date;
import java.time.LocalDate;

import com.panpal.Desk.Desk;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
    Booking findBookingById(Integer id);

    Iterable<Booking> findByDesk(Desk deskId);

    Iterable<Booking> findByDeskOrderByDateAsc(Desk deskId);

    Iterable<Booking> findByEmail(String email);

    Iterable<Booking> findByEmailOrderByDateAsc(String email);

    Iterable<Booking> findByDate(LocalDate date);

    Iterable<Booking> findByDeskAndDate(Desk deskId, LocalDate date);
}
