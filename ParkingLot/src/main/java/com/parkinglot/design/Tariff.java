package com.parkinglot.design;

import com.parkinglot.design.Enum.ParkingLotType;
import com.parkinglot.design.Enum.VehicleType;

import java.time.LocalDateTime;

public abstract class Tariff {
    protected Double calculateTariff(LocalDateTime entry, LocalDateTime exit, ParkingLotType parkingLotType, VehicleType vehicleType)
    {
        if(parkingLotType.equals(ParkingLotType.MALL))
        {
            //depends on vehicle type calculate the tariff
        }
        return 100.00;
    }
}
