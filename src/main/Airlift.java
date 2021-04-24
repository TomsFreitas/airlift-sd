package main;
import sharedRegion.*;
import entity.*;

import java.util.ArrayList;

public class Airlift {

    public static void main(String[] args){

        int MAX_PASSENGERS = 21;
        int MIN_PASSENGERS = 5;
        ArrayList<Passenger> passengers = new ArrayList<>();

        //Initiate Shared Regions
        genRepo repo = new genRepo("logger.txt");
        departureAirport depA = new departureAirport(repo);
        destinationAirport destA = new destinationAirport(repo);
        Plane plane = new Plane(repo);

        Hostess hostess = new Hostess(destA, depA, plane);
        Pilot pilot = new Pilot(depA, destA, plane, repo);


        for (int i = 0; i < MAX_PASSENGERS; i++){
            passengers.add(new Passenger(i+1, depA, destA, plane));
        }

        hostess.start();
        pilot.start();

        for(Passenger passenger : passengers){
            passenger.start();
        }




        for (Passenger passenger : passengers){
            try {
                passenger.join();
            } catch (InterruptedException interruptedException) {
                interruptedException.printStackTrace();
            }
        }

        try {
            hostess.join();
            pilot.join();
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        repo.finalReport();

















    }

}
