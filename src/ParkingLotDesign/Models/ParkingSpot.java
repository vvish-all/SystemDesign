package ParkingLotDesign.Models;

import ParkingLotDesign.Entities.Vehicle;
import ParkingLotDesign.Enums.ParkingSpotType;
import lombok.Getter;
import lombok.Setter;
import lombok.Synchronized;

import java.util.concurrent.locks.ReentrantLock;


@Setter(onMethod_ = @Synchronized)
@Getter(onMethod_ = @Synchronized)
public class ParkingSpot {

    private long id;

    private ParkingSpotType spotType;
    private volatile boolean isAvailable = true;
    private volatile Vehicle vehicleParked;

    private final ReentrantLock spotLock = new ReentrantLock();

    public boolean tryOccupy(Vehicle vehicle){
        spotLock.lock();

        try {
            if(!isAvailable){
                return false;
            }
            isAvailable = false;
            vehicleParked = vehicle;
            return true;
        }finally {
            spotLock.unlock();
        }
    }

    public void vacate(){
        spotLock.lock();
        try {
            isAvailable = true;
            vehicleParked = null;
        }finally {
            spotLock.unlock();
        }
    }
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
