package com.application.Ticketbooking.Repository;

import com.application.Ticketbooking.Entity.SeatCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatCountRepository extends JpaRepository<SeatCount, Long> {

    @Query("SELECT COUNT(s) FROM SeatCount s WHERE s.section = :section and s.allocation = false")
    Integer findcountBysection(@Param("section") String section);

    @Query(value = "SELECT seat_number FROM seat_count WHERE section = :section AND allocation = false LIMIT 1", nativeQuery = true)
    Integer findFirstVacantSeatNumberBySection(@Param("section") String section);

    @Query(value = "SELECT * FROM seat_count WHERE section = :section AND seat_number = :curseat", nativeQuery = true)
    Optional<SeatCount> findByseatNumberAndSection(Integer curseat, @Param("section") String section);

    @Query(value = "select * from seat_count where ticket_id =:ticketId", nativeQuery = true)
    SeatCount findByticketID(Long ticketId);

    @Query(value = "SELECT * FROM seat_count s WHERE s.section = :newSection AND s.seat_number = :newSeat and allocation = false", nativeQuery = true)
    SeatCount findVacancyByNewSectionAndNewSeat(@Param("newSection") String newSection, @Param("newSeat") Integer newSeat);


}
