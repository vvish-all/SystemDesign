package ParkingLotDesign.Startegy.SpotFindingStategy;

import ParkingLotDesign.Enums.VehicleType;
import ParkingLotDesign.Models.Floor;
import ParkingLotDesign.Models.ParkingLot;
import ParkingLotDesign.Models.ParkingSpot;
import ParkingLotDesign.Startegy.SpotValidationStrategy.SpotValidationStrategy;

import java.util.List;

public class NearestSpotStrategy implements SpotFindingStrategy {


    @Override
    public ParkingSpot findParkingSpot(int  floorNumber, VehicleType vehicleType, SpotValidationStrategy validationsChain) {

        ParkingLot parkingLot = ParkingLot.getInstance();
        Floor floor = parkingLot.getFloors().get(floorNumber -1);

        for(ParkingSpot spot: floor.getSpots()){
            if(spot.isAvailable() && validationsChain.validate(spot.getSpotType(), vehicleType)) {
                return spot;
            }
        }
        //search other floors
        List<Floor> floors = parkingLot.getFloors();
        for(Floor f : floors){
            for(ParkingSpot spot: f.getSpots()){
                if(spot.isAvailable() && validationsChain.validate(spot.getSpotType(), vehicleType)) {
                    return spot;
                }
            }

        }

        System.out.println("No Parking Spot Available for this Vehicle Type: "+vehicleType);
        return null;
    }
}
