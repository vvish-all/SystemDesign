package ParkingLotDesign.Models;

import ParkingLotDesign.Enums.GateType;
import ParkingLotDesign.Startegy.SpotFindingStategy.SpotFindingStrategy;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Setter
@Getter
public class ParkingLot {
    private long id;
    private String name;
    private String Address;
    private List<Floor> floors;
    private List<Gate> entryGates;
    private List<Gate> exitGates;
    private List<DisplayBoard> displayBoards;
    private int countSpots;
    private boolean isFull;

    private static ParkingLot instance = null;

    private SpotFindingStrategy spotFindingStartegy;



    private ParkingLot() {
        this.floors = new ArrayList<>();
        this.entryGates = new ArrayList<>();
        this.exitGates = new ArrayList<>();
        this.isFull = true;
        this.countSpots =0;
    }

    public static ParkingLot getInstance(){
        if(instance == null){
            synchronized (ParkingLot.class){
                if (instance == null) {
                    instance = new ParkingLot();
                }
            }
        }
        return instance;
    }

    public void addFloor(Floor floor){
        if(floors == null){
            this.floors = new ArrayList<>();
        }
        floors.add(floor);
        countSpots+=floor.getSpots().size();
        isFull = false;
    }
    public void removeFloor(Floor floor){
        if(floors != null){
            floors.remove(floor);
            countSpots-=floor.getSpots().size();
            for(ParkingSpot spot : floor.getSpots()){
                if(spot.isAvailable()){
                    countSpots--;
                }
            }
            if(countSpots<=0) isFull = true;
        }
    }

    public void addEntryGate(Gate gate){
        if(entryGates == null){
            this.entryGates = new ArrayList<>();
        }
        gate.setType(GateType.ENTRY);
        entryGates.add(gate);
    }
    public void removeEntryGate(Gate gate){
        if(entryGates!= null) entryGates.remove(gate);
    }
    public void addExitGate(Gate gate){
        if(exitGates == null){
            this.exitGates = new ArrayList<>();
        }
        gate.setType(GateType.EXIT);
        exitGates.add(gate);
    }
    public void removeExitGate(Gate gate){
        if(exitGates!= null) exitGates.remove(gate);
    }

    @Override
    public String toString() {
        return "ParkingLot{" +
                "exitGates=" + exitGates + "\n"+
                ", name='" + name + '\'' +"\n"+
                ", floors=" + floors +"\n"+
                ", entryGates=" + entryGates +"\n"+
                '}';
    }
}
