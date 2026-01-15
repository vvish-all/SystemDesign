package ParkingLotDesign.Models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class Floor {

    private long id;
    private int floorNumber;
    private List<ParkingSpot> spots;

    public Floor(List<ParkingSpot> spots) {
        this.spots = spots;
    }

    public void addParkingSpot(ParkingSpot spot){
        if(spots == null){
            spots = new ArrayList<>();
        }
        spots.add(spot);
    }

    public void removeParkingSpot(ParkingSpot spot){
        if(spots != null && !spots.isEmpty()) spots.remove(spot);
    }

    @Override
    public String toString() {
        return "Floor{" +
                "id=" + id +
                ", floorNumber=" + floorNumber +
                ", spots=" + spots +"\n"+
                '}';
    }
}
