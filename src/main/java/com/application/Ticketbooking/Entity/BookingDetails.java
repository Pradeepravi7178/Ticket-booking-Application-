package com.application.Ticketbooking.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class BookingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ticketId;
    private String fromLocation = "London";
    private String toLocation = "France";
    private double cost = 5.0;
    private LocalDateTime createDate = LocalDateTime.now();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    private Customer user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "seatId")
    private SeatCount seat;

    public BookingDetails() {

    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public String getFromLocation() {
        return fromLocation;
    }

    public void setFromLocation(String fromLocation) {
        this.fromLocation = fromLocation;
    }

    public String getToLocation() {
        return toLocation;
    }

    public void setToLocation(String toLocation) {
        this.toLocation = toLocation;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public SeatCount getSeat() {
        return seat;
    }

    public void setSeat(SeatCount seat) {
        this.seat = seat;
    }

    public BookingDetails(Long ticketId, String fromLocation, String toLocation, double cost, LocalDateTime createDate, Customer user, SeatCount seat) {
        this.ticketId = ticketId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        this.cost = cost;
        this.createDate = createDate;
        this.user = user;
        this.seat = seat;
    }
}

