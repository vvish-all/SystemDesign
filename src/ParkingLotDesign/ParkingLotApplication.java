package ParkingLotDesign;

import ParkingLotDesign.Entities.Attendant;
import ParkingLotDesign.Entities.Vehicle;
import ParkingLotDesign.Enums.AttendantStatus;
import ParkingLotDesign.Enums.ParkingSpotType;
import ParkingLotDesign.Enums.VehicleType;
import ParkingLotDesign.Models.*;
import ParkingLotDesign.Services.ParkingService;
import ParkingLotDesign.Services.TicketService;
import ParkingLotDesign.Startegy.SpotFindingStategy.NearestSpotStrategy;
import ParkingLotDesign.Startegy.SpotValidationStrategy.BiggerOrEqualStrategy;
import ParkingLotDesign.Startegy.SpotValidationStrategy.SpotValidationStrategy;

import java.util.*;
import java.util.concurrent.*;

public class ParkingLotApplication {

    public static void main(String[] args) {

        System.out.println("----Creating Parking Lot----");

        //get parking lot instance
        ParkingLot parkingLot = ParkingLot.getInstance();

        int numberOfFloors = 1;

        //adding Floors with Parking Spots
        for(int i=1; i<=numberOfFloors; i++){
            List<ParkingSpot> list = new ArrayList<>();
            for(int j=1; j<=5; j++){
                ParkingSpot parkingSpot = new ParkingSpot();
                parkingSpot.setId(i*10+j);
                parkingSpot.setSpotType(ParkingSpotType.COMPACT);
                list.add(parkingSpot);
            }for(int j=6; j<=10; j++){
                ParkingSpot parkingSpot = new ParkingSpot();
                parkingSpot.setId(i*10+j);
                parkingSpot.setSpotType(ParkingSpotType.EV);
                list.add(parkingSpot);
            }
            for(int j=11; j<=15; j++){
                ParkingSpot parkingSpot = new ParkingSpot();
                parkingSpot.setId(i*10+j);
                parkingSpot.setSpotType(ParkingSpotType.LARGE);
                list.add(parkingSpot);
            }
            for(int j=16; j<=20; j++){
                ParkingSpot parkingSpot = new ParkingSpot();
                parkingSpot.setId(i*10+j);
                parkingSpot.setSpotType(ParkingSpotType.MEDIUM);
                list.add(parkingSpot);
            }
            Floor floor = new Floor(list);
            floor.setId(i);
            floor.setFloorNumber(i);

            parkingLot.addFloor(floor);
        }
        //adding entry gates to Parking Lot
        for(int i=1; i<=numberOfFloors; i++){
            Gate entryGate = new Gate(1, "Entry Gate-"+i, i);
            parkingLot.addEntryGate(entryGate);
        }
        //adding exit gates to Parking Lot
        for(int i=1; i<=numberOfFloors; i++){
            Gate exitgate = new Gate(1, "Exit Gate-"+i, i);
            parkingLot.addExitGate(exitgate);
        }

        //creating Attendants
        List<String> AttendantNames = Arrays.asList("Rancho","Farhan", "Raju", "Silencer","Virus");
        List<Attendant> attendants = new ArrayList<>();
        for(int i=1; i<=5; i++){
            Attendant attendant = new Attendant(i, AttendantNames.get(i-1));
            attendants.add(attendant);
        }

        //Creating Vehicles
        Queue<Vehicle> vehicleQueue = new LinkedList<>();
        for(int i = 11; i <= 20; i++){
            Vehicle vehicle = new Vehicle((long)i,i+"234", VehicleType.BIKE);
            vehicleQueue.add(vehicle);
        }
        for(int i = 21; i <= 30; i++){
            Vehicle vehicle = new Vehicle((long)i,i+"234", VehicleType.CAR);
            vehicleQueue.add(vehicle);
        }
        for(int i = 31; i <= 40; i++){
            Vehicle vehicle = new Vehicle((long)i,i+"234", VehicleType.BUSES);
            vehicleQueue.add(vehicle);
        }
        for(int i = 41; i <= 50; i++){
            Vehicle vehicle = new Vehicle((long)i,i+"234", VehicleType.TRUCKS);
            vehicleQueue.add(vehicle);
        }
        for(int i = 51; i <= 60; i++){
            Vehicle vehicle = new Vehicle((long)i,i+"234", VehicleType.EV);
            vehicleQueue.add(vehicle);
        }

        System.out.println("Total count of available Parking spots:"+ parkingLot.getCountSpots());

        //parking service
        List<SpotValidationStrategy> validations = List.of(new BiggerOrEqualStrategy());
        ParkingService parkingService = new ParkingService(new NearestSpotStrategy(),validations);
        TicketService ticketService = new TicketService();

        Queue<ParkingTicket> tickets = new LinkedList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(attendants.size());
        BlockingQueue<Attendant> freeAttendants = new ArrayBlockingQueue<>(attendants.size());
        freeAttendants.addAll(attendants);


        while(!vehicleQueue.isEmpty()){
            Vehicle vehicle = vehicleQueue.poll();

            //find free attendant
            /*
            Attendant myAttendent = null;
            while(myAttendent == null){
                for(Attendant attendant : attendants){
                    if(attendant.getStatus()== AttendantStatus.FREE){
                        myAttendent = attendant;
                        myAttendent.setStatus(AttendantStatus.BUSY);
                        break;
                    }
                }
            }
             */

            executorService.submit(() ->{

                Attendant myAttendant = null;
                        try {
                            myAttendant =freeAttendants.poll(2,TimeUnit.SECONDS);
                            if(myAttendant==null){
                                System.out.println("No Attendant is available , adding vehicle back in queue");
                                vehicleQueue.add(vehicle);
                                return;
                            }
                            myAttendant.setStatus(AttendantStatus.BUSY);
                            System.out.println(myAttendant.getName() + " is serving parking of vehicle id " + vehicle.getId());
                            ParkingSpot parkingSpot = parkingService.parkVehicle(vehicle, parkingLot.getEntryGates().get((int) (Math.random() * numberOfFloors)));
                            if (parkingSpot == null) {
                                myAttendant.setStatus(AttendantStatus.FREE);
//                              System.out.println(" Parking spot on this floor sending to another floor");
                                System.out.println(myAttendant.getName() + " is free to serve!!");
                                return;
                            }
                            ParkingTicket parkingTicket = ticketService.getParkingTicket(parkingSpot, vehicle, parkingLot.getEntryGates().get((int) (Math.random() * numberOfFloors)));

                            synchronized (tickets){
                                tickets.add(parkingTicket);
                            }
//                            myAttendant.setStatus(AttendantStatus.FREE);
//                            System.out.println(myAttendant.getName() + " is free to serve!!");
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }finally {
                            if(myAttendant!=null){
                                freeAttendants.offer(myAttendant);
                                myAttendant.setStatus(AttendantStatus.FREE);
                                System.out.println(myAttendant.getName() + " is free to serve!!");
                            }
                        }
            });
        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(parkingLot.getFloors());
        System.out.println("Total count of available Parking spots:"+ parkingLot.getCountSpots());


//        //simulating time elapse
//        System.out.println("Simulating time elapse");
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("time elapsed");

        executorService = Executors.newFixedThreadPool(attendants.size());

        while(!tickets.isEmpty()){

            ParkingTicket ticket = tickets.poll();


            executorService.submit(()-> {
                Attendant myAttendent = null;
                try {
                    myAttendent = freeAttendants.take();
                    System.out.println(myAttendent.getName() + " is serving exit of vehicle id " + ticket.getVehicle().getId());
                    ParkingTicket parkingTicket = parkingService.processExit(ticket, parkingLot.getExitGates().get((int) (Math.random() * numberOfFloors)));
                    System.out.println("Exit time:" + parkingTicket.getExitTime());
                    myAttendent.setStatus(AttendantStatus.FREE);
                    System.out.println(myAttendent.getName() + " is free to serve!!");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    if (myAttendent != null) {
                        freeAttendants.offer(myAttendent);
                        myAttendent.setStatus(AttendantStatus.FREE);
                        System.out.println(myAttendent.getName() + " is free to serve!!");
                    }
                }
            });

        }

        executorService.shutdown();
        try {
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(parkingLot.getFloors());
        System.out.println("Total count of available Parking spots:"+ parkingLot.getCountSpots());



        System.out.println("________END_________");


    }


}
