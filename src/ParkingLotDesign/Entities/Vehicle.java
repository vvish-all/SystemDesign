package ParkingLotDesign.Entities;

import ParkingLotDesign.Enums.VehicleStatus;
import ParkingLotDesign.Enums.VehicleType;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Vehicle {
    private long id;
    private String vehicleNumber;
    private VehicleType vehicleType;
    private VehicleStatus status;

    private Vehicle(){}

    public Vehicle(Long id, String vehicleNumber, VehicleType vehicleType){
        this.id = id;
        this.vehicleNumber=vehicleNumber;
        this.vehicleType=vehicleType;
        this.setStatus(VehicleStatus.ENQUEUE);
    }
}
