package com.application.Ticketbooking.DTO;

public class TicketListDTO {

    private Long TicketNo;
    private String Section;
    private Integer SeatNo;
    private String FirstName;
    private String LastName;

    public TicketListDTO(Long ticketNo, String section, Integer seatNo, String firstName, String lastName) {
        TicketNo = ticketNo;
        Section = section;
        SeatNo = seatNo;
        FirstName = firstName;
        LastName = lastName;
    }

    public Long getTicketNo() {
        return TicketNo;
    }

    public void setTicketNo(Long ticketNo) {
        TicketNo = ticketNo;
    }

    public String getSection() {
        return Section;
    }

    public void setSection(String section) {
        Section = section;
    }

    public Integer getSeatNo() {
        return SeatNo;
    }

    public void setSeatNo(Integer seatNo) {
        SeatNo = seatNo;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
