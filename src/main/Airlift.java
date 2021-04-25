package main;
import sharedRegion.*;
import entity.*;

import java.util.ArrayList;

/**
 * Main class for the Airlift
 * It launches the threads for all entities of the problem
 * @author Tomás Freitas
 * @author Tiago Gomes
 */

public class Airlift {

    /**
     *    Main method.
     *    @param args runtime arguments
     */

    public static void main(String[] args){

        //Global variables
        int MAX_PASSENGERS = 21;
        int MIN_PASSENGERS = 5;
        ArrayList<Passenger> passengers = new ArrayList<>();

        //Initiate Shared Regions
        genRepo repo = new genRepo("logger.txt");
        departureAirport depA = new departureAirport(repo);
        destinationAirport destA = new destinationAirport(repo);
        Plane plane = new Plane(repo);

        //Initiate entities
        Hostess hostess = new Hostess(depA, plane);
        Pilot pilot = new Pilot(depA, destA, plane);


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
