package ParkingLotDesign.Models;

import ParkingLotDesign.Entities.Vehicle;
import ParkingLotDesign.Enums.ParkingSpotType;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class ParkingSpot {

    private long id;

    private ParkingSpotType spotType;
    private boolean isAvailable = true;
    private Vehicle vehicleParked;

    @Override
    public String toString() {
        return "\n"+"ParkingSpot{" +
                "id=" + id +
                ", spotType=" + spotType +
                ", isAvailable=" + isAvailable +
                ", vehicleParked=" + vehicleParked +
                '}';
    }
}
