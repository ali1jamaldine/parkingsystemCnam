package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

public class FareCalculatorService {

    public void calculateFare(Ticket ticket){
        if( (ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime())) ){
            throw new IllegalArgumentException("Out time provided is incorrect:"+ticket.getOutTime().toString());
        }

        long inMin = ticket.getInTime().getTime()/1000/60;
        long outMin = ticket.getOutTime().getTime()/1000/60;
        //TODO: Some tests are failing here. Need to check if this logic is correct
        long dur = outMin - inMin;
        int duration=(int) dur/60;
        double rate = 1.0;
        if(dur<30) {
        	rate = 0.0 ;
        	}
        else {
        	if(dur<60) {
        		duration=1;
        		rate=0.75;
        		}
        	}
        switch (ticket.getParkingSpot().getParkingType()){
            case CAR:  {
                ticket.setPrice(rate*duration * Fare.CAR_RATE_PER_HOUR);
                break;
            }
            case BIKE: {
                ticket.setPrice(rate*duration * Fare.BIKE_RATE_PER_HOUR);
                break;
            }
            default: throw new IllegalArgumentException("Unkown Parking Type");
        }
    }
}