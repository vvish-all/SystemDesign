package ParkingLotDesign.Services;

import ParkingLotDesign.Entities.Vehicle;
import ParkingLotDesign.Enums.ParkingSpotType;
import ParkingLotDesign.Enums.TicketStatus;
import ParkingLotDesign.Enums.VehicleStatus;
import ParkingLotDesign.Enums.VehicleType;
import ParkingLotDesign.Models.Gate;
import ParkingLotDesign.Models.ParkingLot;
import ParkingLotDesign.Models.ParkingSpot;
import ParkingLotDesign.Models.ParkingTicket;
import ParkingLotDesign.Startegy.SpotFindingStategy.SpotFindingStrategy;
import ParkingLotDesign.Startegy.SpotValidationStrategy.SpotValidationStrategy;

import java.time.LocalDateTime;
import java.util.List;

public class ParkingService {

    private SpotFindingStrategy spotFindingStrategy;
    private List<SpotValidationStrategy> validations;
    private SpotValidationStrategy validationChain;

    private ParkingLot parkingLot;


    private  ParkingService(){}

    public ParkingService(SpotFindingStrategy spotFindingStrategy, List<SpotValidationStrategy> validations){

        this.validations = validations;
        this.spotFindingStrategy = spotFindingStrategy;
        this.validationChain= buildValidationChain(validations);

        parkingLot = ParkingLot.getInstance();
    }

    public ParkingSpot parkVehicle(Vehicle vehicle, Gate entryGate){

        if(parkingLot.isFull()) {
            System.out.println("Parking Lot is full!!!");
            return null;
        }
        ParkingSpot parkingSpot = spotFindingStrategy.findParkingSpot(entryGate.getFloorNumber(),vehicle.getVehicleType(), validationChain);

        if(parkingSpot == null) return null;

        parkingSpot.setAvailable(false);
        parkingLot.setCountSpots(parkingLot.getCountSpots()-1);

        if(parkingLot.getCountSpots()<=0){
            parkingLot.setFull(true);
        }

        System.out.println("vehicle id: "+vehicle.getId() +" is parked at spot id: " + parkingSpot.getId());
        return parkingSpot;

    }

    public ParkingTicket processExit(ParkingTicket parkingTicket, Gate exitGate){

        //create payment then
        System.out.println("Payment Service to be added later!! Assuming payment Done!!");
        Vehicle vehicle= parkingTicket.getVehicle();
        ParkingSpot parkingSpot = parkingTicket.getParkingSpot();

        vehicle.setStatus(VehicleStatus.EXITED);
        parkingSpot.setAvailable(true);
        parkingSpot.setVehicleParked(null);

        parkingTicket.setTicketStatus(TicketStatus.COMPLETED);
        parkingTicket.setExitTime(LocalDateTime.now());

        parkingLot.setCountSpots(parkingLot.getCountSpots()+1);
        parkingLot.setFull(false);
        return parkingTicket;
    }



    private SpotValidationStrategy buildValidationChain(List<SpotValidationStrategy> validations){

        if(validations== null || validations.isEmpty()) {
            return new SpotValidationStrategy() {
                @Override
                public boolean validate(ParkingSpotType parkingSpotType, VehicleType vehicleType) {
                    return true;
                }

                @Override
                public void setNext(SpotValidationStrategy next) {

                }
            };
        }

        for (int i = 0; i < validations.size() - 1; i++) {
            validations.get(i).setNext(validations.get(i + 1));
        }

        return validations.get(0);
    }


}
