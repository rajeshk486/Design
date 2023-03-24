package com.parkinglot.design;

import com.parkinglot.design.Enum.VehicleType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Vehicle {
    VehicleType vehicleType;
    String number;
    Spot spot;
    Ticket ticket;
}
