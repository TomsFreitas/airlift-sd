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
     *    Instantiates and runs all the needed threads for the problem.
     *    When all threads are finished the final report is generated and the program terminates
     *    @param args runtime arguments
     */

    public static void main(String[] args){

        //Global variables

        ArrayList<Passenger> passengers = new ArrayList<>();

        //Initiate Shared Regions
        genRepo repo = new genRepo("logger.txt");
        departureAirport depA = new departureAirport(repo);
        destinationAirport destA = new destinationAirport(repo);
        Plane plane = new Plane(repo);

        //Initiate entities
        Hostess hostess = new Hostess(depA, plane);
        Pilot pilot = new Pilot(depA, destA, plane);


        for (int i = 0; i < SimulPar.N_Passengers; i++){
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
