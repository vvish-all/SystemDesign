package ParkingLotDesign.Entities;

import ParkingLotDesign.Enums.AttendantStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Attendant {

    private long id;
    private String name;
    private AttendantStatus status;

    private Attendant(){}
    public  Attendant(long id, String name){
        this.id = id;
        this.name =name;
        this.status = AttendantStatus.FREE;
    }
}
