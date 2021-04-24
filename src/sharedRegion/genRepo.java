package sharedRegion;

import main.SimulPar;
import entity.*;
import states.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Arrays;
//import java.util.concurrent.locks.ReentrantLock;

public class genRepo {
    //private final ReentrantLock rl;
    private String logFileName;

    private Integer flightNumber;
    private String pilotState;
    private String hostessState;
    private String[] passengerState;
    private Queue<Integer> passengersInQueue;
    //private Queue<Integer> passengerInFlight;
    private Integer passengerInFlight;
    private Integer numberOfPassengersArrived;

    private boolean readyToFly = false;
    private boolean passengerChecked = false;
    private boolean readyForBoarding = false;
    private int atualPassengerCheckedId;


    /**
     *  Repository Instatiation
     *  @param logFileName name of the logging file
     */

    public genRepo(String logFileName){
        if((logFileName == null) || Objects.equals(logFileName, ""))
            this.logFileName = "logger";
        else
            this.logFileName = logFileName;

        passengerState = new String[SimulPar.N_Passengers];
        this.passengersInQueue = new LinkedList<>();
        //this.passengerInFlight = new LinkedList<>();

/*
        for(int i = 0; i < SimulPar.N_Passengers; i++)
            passengersInQueue.add(0);
*/

        for(int i = 0; i < SimulPar.N_Passengers; i++){
            passengerState[i] = "GTAP";
        }


        this.pilotState = pilotStates.AT_TRANSFER_GATE.getState();
        this.hostessState = hostessStates.WAIT_FOR_NEXT_FLIGHT.getState();
        this.passengerInFlight = 0;
        this.numberOfPassengersArrived = 0;
        reportInitialStatus();
    }

    /**
     *  Set flight number
     *  @param flightNumber number of the actual flight
     */

    public synchronized void setFlightNumber(int flightNumber){
        this.flightNumber = flightNumber;
    }

    /**
     *  Set pilotState
     *  @param state state of the pilot
     */
    public synchronized void setPilotState(String state){
        this.pilotState = state;
    }

    /**
     *  Set hostessState
     *  @param state state of the hostess
     */
    public synchronized void setHostessState(String state){
        this.hostessState = state;
    }

    /**
     *  Set state of passenger
     *  @param id id passenger to set state
     *  @param state state of the passenger
     */
    public synchronized void setPassengerState(int id, String state){
        this.passengerState[id-1] = state;
    }

    /**
     *  Put passenger in Queue
     *  @param id id passenger
     */
    public synchronized void putPassengerInQueue(int id){
        passengersInQueue.add(id);
        reportStatus();
        passengerChecked = true;
        atualPassengerCheckedId = id - 1;
    }

    /**
     *  Put passenger in Flight
     */
    /*public void putPassengerInFlight(){
        remPassengerInQueue();
    }*/

    /**
     *  Remove passenger in Queue
     */
    public synchronized void remPassengerInQueue(int id){
        //passengerChecked = true;
        //atualPassengerCheckedId = passengersInQueue.remove() - 1;
        passengersInQueue.remove();

        //reportStatus();
    }

    public synchronized void getPassengersInFlight(int occupiedSeats){
        passengerInFlight = occupiedSeats;
    }

    public synchronized void readyForBoarding(boolean readyForBoarding){
        this.readyForBoarding = readyForBoarding;
    }

    public synchronized void leaveThePlane(int passengersAtDestination, int occupiedSeats){
        this.numberOfPassengersArrived = passengersAtDestination;
        this.passengerInFlight = occupiedSeats;
        reportStatus();
    }

    public synchronized void readyToFly(boolean readyToFly){
        this.readyToFly = readyToFly;
        reportStatus();
    }




    /*public synchronized void passengerChecked(boolean boardThePlane){
        passengerChecked = boardThePlane;
    }*/

    /*public synchronized void getPassengerChecked(int id){
        passengerChecked = true;
        atualPassengerCheckedId = id;
    }
*/
    /**
     *  Write the header to the logging file.
     *  Internal operation.
     */

    private synchronized String getHeader(){
        String header;

        header = "                                                       AirLift - Description of the internal state";
        header += "\n  PT   HT\t ";
        for(int i = 0; i < SimulPar.N_Passengers; i++){
            header += "P" + i + " \t ";
        }
        header += "\t InQ \t InF \t PTAL";
        //log.write("\nPT \t HT \t P00 \t P01 \t P02 \t P03 \t P04 \t P05 \t P06 \t P07 \t P08 \t P09 \t P10 \t P11 \t P12\t P13 \t P14 \t P15 \t P16 \t P17 \t P18 \t P19 \t P20 \t InQ \t InF \t PTAL")
        header += "\n";

        return header;
    }

    /**
     *  Write the header to the logging file.
     *  Internal operation.
     */


    private synchronized void reportInitialStatus(){
        flightNumber = 0;
        try{
            FileWriter log = new FileWriter(logFileName, false);
            String header = getHeader();
            String initialStatus;

            initialStatus = pilotState + "  " + hostessState;
            for(int i = 0; i < SimulPar.N_Passengers; i++){
                initialStatus += passengerState[i];
            }
            //initialStatus += passengersInQueue.size() + numberOfPassengersPlane + numberOfPassengersArrived;

            try(PrintWriter pw = new PrintWriter((log))){
                pw.print(header);
            }
            System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        reportStatus();
    }

    /**
     *  Write a state line at the end of the logging file.
     *
     *  The current state is organized in a line to be printed.
     *  Internal operation.
     */


    public synchronized void reportStatus(){
        try{
            FileWriter log = new FileWriter(logFileName, true);
            StringBuilder lineStatus = new StringBuilder();                                     // state line to be printed

            //if(readyForBoarding){
            if(pilotState.equals("RDFB")){
                flightNumber += 1;
                lineStatus.append(String.format("\nFlight %-2d : Boarding Started.    \n", flightNumber));
            }

            if(pilotState.equals("FLBK")){
                passengerInFlight = 0;
            }

            if(passengerChecked){
                lineStatus.append(String.format("\nFlight %-2d : Passenger %-2d checked.    \n", flightNumber, atualPassengerCheckedId));
                passengerChecked = false;
            }

            if(readyToFly){
                lineStatus.append(String.format("\nFlight %-2d : Departed with %-2d passengers.    \n", flightNumber, passengerInFlight));
            }


            lineStatus.append(String.format("%-4s  %-2s   ", pilotState, hostessState));
            for(int i = 0; i < SimulPar.N_Passengers; i++){
                lineStatus.append(String.format("    %s", passengerState[i]));
            }

            lineStatus.append(String.format("             %-2d      %-2d     %-2d ", passengersInQueue.size(), passengerInFlight, numberOfPassengersArrived));
            lineStatus.append("\n");

            try(PrintWriter pw = new PrintWriter(log)){
                pw.print(lineStatus);
            }

            System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }




    }

    /*private synchronized void finalReport(){
        File log = new File();

        if(!log.openForWriting(".", logFileName)){
            System.out.println("The operationg of creating the file " + logFileName + " failed!");
            System.exit(1);
        }
        log.write("Airlift sum up:");
        for(i = 1; i <= numFlights; i++){
            log.write("\nFlight " + flightNumber + " transported " + numberOfPassengersPerFlight + " passengers");
        }
        log.close();

        if(!log.close()){
            System.out.println("The operation of closing the file " + logFileName + " failde!");
        }

    }*/
}
