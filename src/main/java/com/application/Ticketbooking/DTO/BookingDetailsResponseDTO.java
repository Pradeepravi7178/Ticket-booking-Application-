package com.application.Ticketbooking.DTO;

import java.time.LocalDateTime;

public class BookingDetailsResponseDTO {
    private Long TicketId;

    private String fromLocation = "London";

    private String toLocation = "France";
    private String Fistname;
    private String Lastname;
    private LocalDateTime createDate;
    private double cost;
    private String email;
    private String section;  // Section A or B
    private Integer seatNumber;

    // Constructor

    public BookingDetailsResponseDTO(Long ticketId, String fromLocation, String toLocation, String fistname,
                                     String lastname, LocalDateTime createDate, double cost, String email,
                                     String section, Integer seatNumber) {
        TicketId = ticketId;
        this.fromLocation = fromLocation;
        this.toLocation = toLocation;
        Fistname = fistname;
        Lastname = lastname;
        this.createDate = createDate;
        this.cost = cost;
        this.email = email;
        this.section = section;
        this.seatNumber = seatNumber;
    }



    public BookingDetailsResponseDTO(String s) {
    }


    // Getters and setters


    public Long getTicketId() {
        return TicketId;
    }

    public void setTicketId(Long ticketId) {
        TicketId = ticketId;
    }

    public String getFistname() {
        return Fistname;
    }

    public void setFistname(String fistname) {
        Fistname = fistname;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString() {
        return "BookingDetailsResponseDTO{" +
                "TicketId=" + TicketId +
                ", fromLocation='" + fromLocation + '\'' +
                ", toLocation='" + toLocation + '\'' +
                ", Fistname='" + Fistname + '\'' +
                ", Lastname='" + Lastname + '\'' +
                ", createDate=" + createDate +
                ", cost=" + cost +
                ", email='" + email + '\'' +
                ", section='" + section + '\'' +
                ", seatNumber=" + seatNumber +
                '}';
    }
}

