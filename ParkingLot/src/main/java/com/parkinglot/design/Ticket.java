package com.parkinglot.design;

import com.parkinglot.design.Enum.ParkingLotType;
import com.parkinglot.design.Enum.PaymentMode;
import com.parkinglot.design.Enum.VehicleType;
import lombok.Data;
import lombok.Generated;
import lombok.ToString;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Data
@ToString
public class Ticket extends  Tariff{
    @Generated
    int ticketId;
    Vehicle vehicle;
    LocalDateTime entryTime;
    LocalDateTime exitTime;
    PaymentMode paymentMode;
    boolean paymentDone;
    double amount;

    public boolean processPayment(ParkingLotType parkingLotType, VehicleType vehicleType)
    {
        this.amount = calculateTariff(this.entryTime,this.exitTime,parkingLotType,vehicleType);
        //processPayment(amount, this.paymentMode)
        return true;
    }
}
