package main;
import sharedRegion.*;
import entity.*;

public class Airlift {

    public static void main(String[] args){

        int MAX_PASSENGERS = 21;
        int MIN_PASSENGERS = 5;
        departureAirport depA = new departureAirport();
        destinationAirport destA = new destinationAirport();
        Plane plane = new Plane();
        genRepo repo = new genRepo();
        Hostess hostess = new Hostess(destA, depA, plane);
        Pilot pilot = new Pilot(depA, destA, plane);

        Passenger passenger = new Passenger(1, depA, destA, plane);
        Passenger passenger2 = new Passenger(2, depA, destA, plane);

        for (int i = 0; i < MAX_PASSENGERS; i++){
            Passenger passenger_temp = new Passenger(i+1, depA, destA, plane);
            passenger_temp.start();
        }

        hostess.start();
        pilot.start();













    }

}
