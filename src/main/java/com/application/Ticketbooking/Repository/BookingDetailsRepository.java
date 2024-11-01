package com.application.Ticketbooking.Repository;

import com.application.Ticketbooking.Entity.BookingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingDetailsRepository extends JpaRepository<BookingDetails, Long> {
    List<BookingDetails> findBySeatSection(String section);

    @Query(value = "select * from booking_details where ticket_id =:ticketId", nativeQuery = true)
    BookingDetails findByIddeatils(Long ticketId);
}
