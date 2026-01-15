package ParkingLotDesign.Services;

import ParkingLotDesign.Entities.Vehicle;
import ParkingLotDesign.Models.Gate;
import ParkingLotDesign.Models.ParkingSpot;
import ParkingLotDesign.Models.ParkingTicket;

public class TicketService {

    public ParkingTicket getParkingTicket(ParkingSpot parkingSpot, Vehicle vehicle, Gate entrygate){
        ParkingTicket parkingTicket = new ParkingTicket();
        parkingTicket.setParkingSpot(parkingSpot);
        parkingTicket.setVehicle(vehicle);
        parkingTicket.setFloorNumber(entrygate.getFloorNumber());
        return parkingTicket;
    }
}
