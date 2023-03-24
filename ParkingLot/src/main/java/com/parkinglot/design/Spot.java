package com.parkinglot.design;

import com.parkinglot.design.Enum.SpotState;
import com.parkinglot.design.Enum.VehicleType;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Spot {
    int id;
    int number;
    String spotDetails;//store extra details about this spot
    SpotState state;
    VehicleType vehicleType;
}
