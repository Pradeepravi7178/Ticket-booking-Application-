package com.application.Ticketbooking.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seat_count")
public class SeatCount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;
    @Column(name = "section")
    private String section;

    @Column(name = "seat_number")
    private Integer seatNumber;

    @Column(name = "ticket_id", nullable = true)
    private Long ticketId;

    @Column(name = "allocation")
    private boolean allocation;


    public SeatCount() {

    }

    public SeatCount(String section, Integer seatNumber, boolean allocation) {
        this.section = section;
        this.seatNumber = seatNumber;
        this.allocation = allocation;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public boolean isAllocation() {
        return allocation;
    }

    public void setAllocation(boolean allocation) {
        this.allocation = allocation;
    }


    public Long getSeatId() {
        return seatId;
    }

    public void setSeatId(Long seatId) {
        this.seatId = seatId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }
}

