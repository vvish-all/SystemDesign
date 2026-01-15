package ParkingLotDesign.Models;

import ParkingLotDesign.Enums.GateType;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class Gate {
    private long id;
    private String name;
    private GateType type;
    private int floorNumber;

    private Gate(){}

    public Gate(long id, String name, int floorNumber) {
        this.id = id;
        this.name = name;
        this.floorNumber = floorNumber;
    }

    @Override
    public String toString() {
        return "Gate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", floorNumber=" + floorNumber +
                '}';
    }
}
