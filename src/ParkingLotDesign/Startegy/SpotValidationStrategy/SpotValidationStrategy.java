package ParkingLotDesign.Startegy.SpotValidationStrategy;

import ParkingLotDesign.Enums.ParkingSpotType;
import ParkingLotDesign.Enums.VehicleType;

public interface SpotValidationStrategy {

    boolean validate(ParkingSpotType parkingSpotType, VehicleType vehicleType);
    void setNext(SpotValidationStrategy next);
}
