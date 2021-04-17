package main;
import sharedRegion.*;
import entity.*;

public class Airlift {

    public static void main(String[] args){

        int MAX_PASSENGERS = 10;
        int MIN_PASSENGERS = 5;
        departureAirport depA = new departureAirport();
        destinationAirport destA = new destinationAirport();
        Plane plane = new Plane();
        genRepo repo = new genRepo();

        Passenger passenger = new Passenger(1, depA, destA, plane);
        Passenger passenger2 = new Passenger(2, depA, destA, plane);



        Hostess hostess = new Hostess(destA, depA, plane);
        Pilot pilot = new Pilot(depA, destA, plane);
        passenger.start();
        hostess.start();
        pilot.start();












    }

}
