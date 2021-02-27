package com.panpal;

import org.springframework.data.repository.CrudRepository;
import java.util.Date;

import com.panpal.Booking;
import com.panpal.Desk;
import com.panpal.User;

public interface BookingRepository extends CrudRepository<Booking, Integer> {
    Booking findBookingById(Integer id);

    Iterable<Booking> findByDesk(Desk deskId);

    Iterable<Booking> findByEmployee(User employeeId);

    Iterable<Booking> findByDate(Date date);

    Iterable<Booking> findByDeskAndDate(Desk deskId, Date date);
}
