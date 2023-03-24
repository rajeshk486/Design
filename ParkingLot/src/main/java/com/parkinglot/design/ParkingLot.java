package com.parkinglot.design;

import com.parkinglot.design.Enum.ParkingLotType;
import com.parkinglot.design.Enum.PaymentMode;
import com.parkinglot.design.Enum.SpotState;
import com.parkinglot.design.Enum.VehicleType;
import com.parkinglot.design.Spot;
import com.parkinglot.design.Ticket;
import com.parkinglot.design.Vehicle;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.*;

@Data
@NoArgsConstructor
@ToString
public class ParkingLot {

    private ParkingLotType parkingLotType;
    private int id;
    private String name;
    private String address;
    //below are the count for each vehicle type, no more extra vehicle will be allowed.
    private  List<Spot> motorCycleSpotCount= new ArrayList<>();
    private  int availableMotoCycleSpots;
    private  List<Spot> carSpotCount= new ArrayList<>();
    private  int availableCarSpots;
    private  List<Spot> busSpotCount = new ArrayList<>();
    private  int availableBusSpots;
    private Map<String,Vehicle> parkedVehicle = new HashMap<>();

    public ParkingLot(int id,ParkingLotType parkingLotType,String name, String address,  int availableMotoCycleSpots,  int availableCarSpots, int availableBusSpots) {
        this.id= id;
        this.parkingLotType = parkingLotType;
        this.name = name;
        this.address = address;
        this.availableMotoCycleSpots = availableMotoCycleSpots;
        this.availableCarSpots = availableCarSpots;
        this.availableBusSpots = availableBusSpots;

        motorCycleSpotCount.addAll(initializeSpots(availableMotoCycleSpots, VehicleType.MOTOTCYCLE));
        busSpotCount.addAll(initializeSpots(availableBusSpots, VehicleType.BUS));
        carSpotCount.addAll(initializeSpots(availableCarSpots, VehicleType.CAR));
    }

    private List<Spot> initializeSpots(int count,VehicleType vehicleType)
    {
        List<Spot> spots = new ArrayList<>(count);
        for(int i = 1; i<=count;i++)
        {
            Spot tempSpot= new Spot();
            tempSpot.setNumber(i);
            tempSpot.setState(SpotState.EMPTY);
            tempSpot.setVehicleType(vehicleType);
            spots.add(tempSpot);
        }
        return spots;
    }

    private int getAvailableSpot(VehicleType vehicleType)
    {
        if(vehicleType==VehicleType.CAR)return availableCarSpots;

           // return (int)carSpotCount.stream().count();

        if(vehicleType==VehicleType.BUS) return availableBusSpots;
            //return (int)busSpotCount.stream().count();

        if(vehicleType==VehicleType.MOTOTCYCLE) return availableMotoCycleSpots;
            //return (int)motorCycleSpotCount.stream().count();
        return 0;
    }
    public Spot blockSpot(VehicleType vehicleType, Vehicle vehicle)
    {
        Spot nextAvailableSpot= null;
        if(vehicleType==VehicleType.CAR)
        {
            if(availableCarSpots==0)
                return  null;
            for(int i=0;i<carSpotCount.stream().count();i++)
            {
                if(carSpotCount.get(i).getState().equals(SpotState.EMPTY)) {
                    nextAvailableSpot = carSpotCount.get(i);
                    carSpotCount.get(i).setState(SpotState.OCCUPIED);
                    availableCarSpots--;
                    break;
                }
            }
        }
        if(vehicleType==VehicleType.BUS) {
            if (availableBusSpots == 0)
                return null;
            for (int i = 0; i < busSpotCount.stream().count(); i++) {
                if (busSpotCount.get(i).getState().equals(SpotState.EMPTY)) {
                    nextAvailableSpot = busSpotCount.get(i);
                    busSpotCount.get(i).setState(SpotState.OCCUPIED);
                    availableBusSpots--;
                    break;
                }
            }
        }
        if(vehicleType==VehicleType.MOTOTCYCLE) {
            if (availableMotoCycleSpots == 0)
                return null;
            for (int i = 0; i < motorCycleSpotCount.stream().count(); i++) {
                if (motorCycleSpotCount.get(i).getState().equals(SpotState.EMPTY)) {
                    nextAvailableSpot = motorCycleSpotCount.get(i);
                    motorCycleSpotCount.get(i).setState(SpotState.OCCUPIED);
                    availableMotoCycleSpots--;
                    break;
                }
            }
        }
            vehicle.setSpot(nextAvailableSpot);
        parkedVehicle.put(vehicle.getNumber(),vehicle);
        System.out.println("No: of parked vehicles: "+parkedVehicle.size());
        return nextAvailableSpot;
    }

public boolean releaseSpot(Vehicle vehicle)
{
    Spot spot = vehicle.getSpot();
    if(vehicle.getVehicleType().equals(VehicleType.MOTOTCYCLE))
    {
        motorCycleSpotCount.get(spot.getNumber()).setState(SpotState.EMPTY);
        availableMotoCycleSpots++;
    }
    if(vehicle.getVehicleType().equals(VehicleType.CAR))
    {
        carSpotCount.get(spot.getNumber()).setState(SpotState.EMPTY);
        availableCarSpots++;
    }
    if(vehicle.getVehicleType().equals(VehicleType.BUS))
    {
        busSpotCount.get(spot.getNumber()).setState(SpotState.EMPTY);
        availableBusSpots++;
    }
    if(Optional.ofNullable(parkedVehicle.get(vehicle.getNumber())).isPresent())
        parkedVehicle.remove(vehicle.getNumber());
    System.out.println("No: of parked vehicles: "+parkedVehicle.size());
    return true;
}
public boolean allowVehicle(VehicleType vehicleType, String vehicleNumber)
{
    System.out.println("Allowing a Vehicle with number: "+vehicleNumber);
    if(getAvailableSpot(vehicleType)<=0)
        return false;
    Vehicle vehicle = new Vehicle();
    vehicle.setVehicleType(vehicleType);
    vehicle.setNumber(vehicleNumber);
    vehicle.setSpot(blockSpot(vehicleType,vehicle));
    vehicle.setTicket(generateTicket(vehicle));
    System.out.println("Vehicle allotted spot:"+ vehicle.getSpot().getNumber()+"\nAvailable parking spot for: "+vehicle.getVehicleType().toString()+" is: "+this.getAvailableSpot(vehicleType));
    return true;
}

    private Ticket generateTicket(Vehicle vehicle) {
        Ticket ticket = new Ticket();
        ticket.setVehicle(vehicle);
        ticket.setEntryTime(LocalDateTime.now());
        return ticket;
    }

    public boolean exitVehicle(String vehicleNumber, PaymentMode paymentMode)
{
    if(Optional.ofNullable(parkedVehicle.get(vehicleNumber)).isEmpty())return false;
    System.out.println("Vehicle exiting the parking lot: "+vehicleNumber);
    //calculate tariff and process payment via ticket class
    parkedVehicle.get(vehicleNumber).getTicket().setExitTime(LocalDateTime.now());
    parkedVehicle.get(vehicleNumber).getTicket().setPaymentMode(paymentMode);
    Ticket ticket=parkedVehicle.get(vehicleNumber).getTicket();
    boolean paymentDone=ticket.processPayment(this.parkingLotType,parkedVehicle.get(vehicleNumber).getVehicleType());
    parkedVehicle.get(vehicleNumber).getTicket().setPaymentDone(paymentDone);
    System.out.println("Vehicle tariff for the parked time: "+ ticket.getAmount()+"\nPayment made via: "+ ticket.getPaymentMode()+"\nPaymentStatus: "+ ticket.isPaymentDone());
    //release the spot
    if(paymentDone) {
        System.out.println("Available parking spot for: "+parkedVehicle.get(vehicleNumber).getVehicleType().toString()+" is: "+this.getAvailableSpot(parkedVehicle.get(vehicleNumber).getVehicleType()));
        return releaseSpot(parkedVehicle.get(vehicleNumber));
    }

    return false;
}
}
