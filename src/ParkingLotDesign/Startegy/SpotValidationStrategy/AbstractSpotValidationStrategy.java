package ParkingLotDesign.Startegy.SpotValidationStrategy;

import ParkingLotDesign.Enums.ParkingSpotType;
import ParkingLotDesign.Enums.VehicleType;

abstract class AbstractSpotValidationStrategy implements SpotValidationStrategy{

    private SpotValidationStrategy next;


    @Override
    public boolean validate(ParkingSpotType parkingSpotType, VehicleType vehicleType) {
        boolean valid = check(parkingSpotType, vehicleType);
        if(next != null){
            return valid && next.validate(parkingSpotType, vehicleType);
        }
        return valid;
    }

    @Override
    public void setNext(SpotValidationStrategy next) {
        this.next=next;

    }

    protected abstract boolean check(ParkingSpotType parkingSpotType, VehicleType vehicleType);
}
