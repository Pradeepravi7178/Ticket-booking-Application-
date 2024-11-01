package com.application.Ticketbooking;

import com.application.Ticketbooking.DTO.BookingDetailsResponseDTO;
import com.application.Ticketbooking.Entity.BookingDetails;
import com.application.Ticketbooking.Entity.Customer;
import com.application.Ticketbooking.Entity.SeatCount;
import com.application.Ticketbooking.Repository.BookingDetailsRepository;
import com.application.Ticketbooking.Repository.SeatCountRepository;
import com.application.Ticketbooking.Repository.UserRepository;
import com.application.Ticketbooking.Services.TrainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SeatCountRepository seatCountRepository;

    @Mock
    private BookingDetailsRepository bookingDetailsRepository;

    @InjectMocks
    private TrainService trainService; // Replace with your actual service class name

    private Customer user;
    private String section = "A";

    private Long ticketId;

    @BeforeEach
    public void setUp() {
        ticketId = 1L;
        user = new Customer();
        user.setFirstName("Pradeep");
        user.setLastName("R");
        user.setEmail("pradeep.r@gmail.com");
    }

    @Test
    public void testPurchaseTicket_Success() {

        Integer availableSeats = 1;
        Integer firstVacantSeat = 1;
        String section = "A"; // Ensure this matches your section setup
        SeatCount seatCount = new SeatCount();
        seatCount.setSection(section);
        seatCount.setSeatNumber(firstVacantSeat);

        when(seatCountRepository.findcountBysection(section)).thenReturn(availableSeats);
        when(seatCountRepository.findFirstVacantSeatNumberBySection(section)).thenReturn(firstVacantSeat);
        when(seatCountRepository.findByseatNumberAndSection(firstVacantSeat, section)).thenReturn(Optional.of(seatCount));
        when(userRepository.save(user)).thenReturn(user);

        BookingDetails bookingDetails = new BookingDetails();
        bookingDetails.setTicketId(1L);
        bookingDetails.setFromLocation("Location A");
        bookingDetails.setToLocation("Location B");
        bookingDetails.setCreateDate(LocalDateTime.now());
        bookingDetails.setCost(100.0);

        when(bookingDetailsRepository.save(any(BookingDetails.class))).thenReturn(bookingDetails);


        when(seatCountRepository.save(any(SeatCount.class))).thenAnswer(invocation -> {
            SeatCount updated = invocation.getArgument(0);
            updated.setTicketId(bookingDetails.getTicketId());
            return updated;
        });

        ResponseEntity<?> response = trainService.purchaseTicket(user, section);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof BookingDetailsResponseDTO);
    }


    @Test
    public void testPurchaseTicket_NoSeatsAvailable() {

        when(seatCountRepository.findcountBysection(section)).thenReturn(0);
        when(seatCountRepository.findFirstVacantSeatNumberBySection(section)).thenReturn(null);


        ResponseEntity<?> response = trainService.purchaseTicket(user, section);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("No seats available in section " + section + ", please try a different section.", response.getBody());
    }
    @Test
    public void testRemoveUserFromTrain_Success() {
        // Arrange
        SeatCount seatCount = new SeatCount();
        seatCount.setAllocation(true);
        seatCount.setTicketId(ticketId);

        when(seatCountRepository.findByticketID(ticketId)).thenReturn(seatCount);

        // Act
        ResponseEntity<String> response = trainService.removeUserFromTrain(ticketId);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Ticket number " + ticketId + " deleted successfuuly", response.getBody());
        verify(seatCountRepository).findByticketID(ticketId);
        verify(bookingDetailsRepository).deleteById(ticketId);
    }

    @Test
    public void testRemoveUserFromTrain_NullTicketId() {
        // Act
        ResponseEntity<String> response = trainService.removeUserFromTrain(null);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Please enter a valid ticket number.", response.getBody());
    }

    @Test
    public void testRemoveUserFromTrain_InvalidTicketId() {
        // Arrange
        when(seatCountRepository.findByticketID(ticketId)).thenReturn(null);

        // Act
        ResponseEntity<String> response = trainService.removeUserFromTrain(ticketId);

        // Assert
        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Please enter the correct ticket number.", response.getBody());
        verify(bookingDetailsRepository, never()).deleteById(anyLong()); // Ensure delete is not called
    }
}
