package com.application.Ticketbooking.Services;

import com.application.Ticketbooking.DTO.BookingDetailsResponseDTO;
import com.application.Ticketbooking.DTO.TicketListDTO;
import com.application.Ticketbooking.Entity.BookingDetails;
import com.application.Ticketbooking.Entity.SeatCount;
import com.application.Ticketbooking.Entity.Customer;
import com.application.Ticketbooking.Repository.BookingDetailsRepository;
import com.application.Ticketbooking.Repository.SeatCountRepository;
import com.application.Ticketbooking.Repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainService {

    private Integer max_seat = 5;

    private char max_section = 'C';
    @Autowired
    private BookingDetailsRepository bookingDetailsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SeatCountRepository seatCountRepository;

    private List<SeatCount> arrseats;

    public TrainService() {
    }

    @PostConstruct
    public void initData() {
        for (char section = 'A'; section <= max_section; section++) {
            for (int seatNumber = 1; seatNumber <= max_seat; seatNumber++) {
                SeatCount seatCount = new SeatCount(String.valueOf(section), seatNumber, false);
                seatCountRepository.save(seatCount);
            }
        }
    }

    public ResponseEntity<?> purchaseTicket(Customer user, String section) {
        Integer seats = seatCountRepository.findcountBysection(section);
        Integer seatAvailable = seatCountRepository.findFirstVacantSeatNumberBySection(section);

        // Check if there are seats available
        if (seats != null && seats > 0 && seatAvailable != null) {
            user = userRepository.save(user);
            Integer currentSeat = seatAvailable;
            BookingDetails booking = new BookingDetails();

            SeatCount seatCount = seatCountRepository.findByseatNumberAndSection(currentSeat, section)
                    .orElseThrow(() -> new RuntimeException("Seat with ID " + currentSeat + " not found"));

            seatCount.setAllocation(true);
            seatCount.setTicketId(booking.getTicketId());
            SeatCount updatedSeatCount = seatCountRepository.save(seatCount);

            booking.setUser(user);
            booking.setSeat(updatedSeatCount);
            BookingDetails bookingDetails = bookingDetailsRepository.save(booking);

            updatedSeatCount.setTicketId(booking.getTicketId());
            seatCountRepository.save(updatedSeatCount);

            BookingDetailsResponseDTO responseDTO = new BookingDetailsResponseDTO(
                    bookingDetails.getTicketId(),
                    bookingDetails.getFromLocation(),
                    bookingDetails.getToLocation(),
                    user.getFirstName(),
                    user.getLastName(),
                    bookingDetails.getCreateDate(),
                    bookingDetails.getCost(),
                    user.getEmail(),
                    updatedSeatCount.getSection(),
                    updatedSeatCount.getSeatNumber()
            );

            return ResponseEntity.ok(responseDTO);

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("No seats available in section " + section + ", please try a different section.");
        }
    }



    public ResponseEntity<?> getReceipt(Long ticketId) {
        if (ticketId == null) {
            return ResponseEntity.badRequest().body("Please enter a valid ticket number.");
        }

        BookingDetails bookingDetails = bookingDetailsRepository.findById(ticketId)
                .orElse(null);

        if (bookingDetails == null) {
            return ResponseEntity.badRequest().body("Please enter the correct ticket number.");
        }

        BookingDetailsResponseDTO responseDTO = new BookingDetailsResponseDTO(
                bookingDetails.getTicketId(),
                bookingDetails.getFromLocation(),
                bookingDetails.getToLocation(),
                bookingDetails.getUser().getFirstName(),
                bookingDetails.getUser().getLastName(),
                bookingDetails.getCreateDate(),
                bookingDetails.getCost(),
                bookingDetails.getUser().getEmail(),
                bookingDetails.getSeat().getSection(),
                bookingDetails.getSeat().getSeatNumber()
        );

        return ResponseEntity.ok(responseDTO);
    }

    public ResponseEntity<String> removeUserFromTrain(Long ticketId) {
        if (ticketId == null) {
            return ResponseEntity.badRequest().body("Please enter a valid ticket number.");
        }

        SeatCount seatdetails = seatCountRepository.findByticketID(ticketId);
        if (seatdetails == null) {
            return ResponseEntity.badRequest().body("Please enter the correct ticket number.");
        }
        seatdetails.setAllocation(false);
        seatdetails.setTicketId(null);
        bookingDetailsRepository.deleteById(ticketId);
        return ResponseEntity.ok("Ticket number " + ticketId + " deleted successfuuly");
    }

    public String modifyUserSeat(Long ticketId, String newSection, Integer newSeat) {
        SeatCount seatdet = seatCountRepository.findByticketID(ticketId);
        BookingDetails bookingDet = bookingDetailsRepository.findByIddeatils(ticketId);

        if (seatdet == null || bookingDet == null) {
            return "Invalid ticket ID. No booking found with the specified ticket ID.";
        }
        SeatCount available = seatCountRepository.findVacancyByNewSectionAndNewSeat(newSection, newSeat);

        if (available != null) {

            bookingDet.setSeat(available);
            bookingDetailsRepository.save(bookingDet);
            seatdet.setAllocation(false);
            seatdet.setTicketId(null);
            seatCountRepository.save(seatdet);
            available.setAllocation(true);
            available.setTicketId(bookingDet.getTicketId());
            seatCountRepository.save(available);

            return "New ticket number is " + newSeat + ", new section is " + newSection;

        } else {
            return "The mentioned seat is already occupied, please try with some other seat/section";
        }

    }

    public List<TicketListDTO> getList() {

        List<BookingDetails> bookingDetails = bookingDetailsRepository.findAll();
        List<TicketListDTO> ticketList = new ArrayList<>();
        for (BookingDetails booking : bookingDetails) {
            TicketListDTO ticket = new TicketListDTO(
                    booking.getTicketId(),
                    booking.getSeat().getSection(),
                    booking.getSeat().getSeatNumber(),
                    booking.getUser().getFirstName(),
                    booking.getUser().getLastName()
            );
            ticketList.add(ticket);
        }
        return ticketList;
    }

}



