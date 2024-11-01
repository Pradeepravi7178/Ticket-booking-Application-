package com.application.Ticketbooking.Controller;

import com.application.Ticketbooking.DTO.BookingDetailsResponseDTO;
import com.application.Ticketbooking.DTO.TicketListDTO;
import com.application.Ticketbooking.Entity.Customer;
import com.application.Ticketbooking.Services.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("train")
public class BookingController {

    @Autowired
    private TrainService trainService;

    @PostMapping("/bookticket")
    public ResponseEntity<?> purchaseTicket(@RequestBody Customer user, @RequestParam String section) throws Exception {
        return trainService.purchaseTicket(user, section);
    }

    @GetMapping("/receipt/{ticketId}")
    public ResponseEntity<?> getReceipt(@PathVariable Long ticketId) {
        return trainService.getReceipt(ticketId);
    }

    @GetMapping("/ticketlist")
    public List<TicketListDTO> getTicketList() {

        return trainService.getList();
    }

    @DeleteMapping("/remove/{ticketId}")
    public ResponseEntity<String> RemoveUserFromTrain(@PathVariable Long ticketId) {
       return trainService.removeUserFromTrain(ticketId);
    }

    @PutMapping("/changeseat")
    public String modifyUserSeat(@RequestParam Long ticketId, @RequestParam String newSection, @RequestParam Integer newSeat) {
        return trainService.modifyUserSeat(ticketId, newSection, newSeat);
    }
}


