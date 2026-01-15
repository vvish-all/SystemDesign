package ParkingLotDesign.Startegy.SpotFindingStategy;

import ParkingLotDesign.Enums.VehicleType;
import ParkingLotDesign.Models.ParkingSpot;
import ParkingLotDesign.Startegy.SpotValidationStrategy.SpotValidationStrategy;

public interface SpotFindingStrategy {
    ParkingSpot findParkingSpot(int floorNumber, VehicleType vehicleType, SpotValidationStrategy validations);
}
