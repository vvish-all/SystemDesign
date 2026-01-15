package ParkingLotDesign.Models;

import ParkingLotDesign.Entities.Vehicle;
import ParkingLotDesign.Enums.TicketStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
public class ParkingTicket {
    private long id;
    private LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private TicketStatus ticketStatus;
    private Vehicle vehicle;
    private int floorNumber;
    private ParkingSpot parkingSpot;

    public ParkingTicket() {
        this.entryTime = LocalDateTime.now();
        this.ticketStatus = TicketStatus.PARKED;
        this.id= Long.parseLong((long)(Math.random()*9+1)+""+ LocalDateTime.now().getNano());
    }

}
