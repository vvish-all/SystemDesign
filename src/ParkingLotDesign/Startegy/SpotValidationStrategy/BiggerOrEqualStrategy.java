package ParkingLotDesign.Startegy.SpotValidationStrategy;

import ParkingLotDesign.Enums.ParkingSpotType;
import ParkingLotDesign.Enums.VehicleType;

public class BiggerOrEqualStrategy extends AbstractSpotValidationStrategy{

    @Override
    protected boolean check(ParkingSpotType parkingSpotType, VehicleType vehicleType) {
        switch (vehicleType){
            case BUSES:
            case TRUCKS:
                return parkingSpotType == ParkingSpotType.LARGE;
            case CAR:
                return (parkingSpotType == ParkingSpotType.LARGE ||
                        parkingSpotType == ParkingSpotType.MEDIUM);
            case BIKE:
                return (parkingSpotType == ParkingSpotType.MEDIUM ||
                        parkingSpotType == ParkingSpotType.COMPACT);
            case EV:
                return parkingSpotType == ParkingSpotType.EV;
            default:
                return false;
        }
    }
}
