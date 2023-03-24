package com.parkinglot.design;

import com.parkinglot.design.Enum.ParkingLotType;
import com.parkinglot.design.Enum.PaymentMode;
import com.parkinglot.design.Enum.VehicleType;

public class MainClass {
    public static void main(String args[])
    {
        ParkingLot MallParkingLot= new ParkingLot(1, ParkingLotType.MALL,"Phoneix parking lot","Phoneix",500,200,100);
        MallParkingLot.allowVehicle(VehicleType.CAR,"tn14p3282");
        MallParkingLot.exitVehicle("tn14p3282", PaymentMode.CREDITCARD);
        System.out.println(MallParkingLot.toString());
    }
}
