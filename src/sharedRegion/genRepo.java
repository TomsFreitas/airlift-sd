package sharedRegion;

import main.SimulPar;
import states.*;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.LinkedList;
import java.util.Queue;
//import java.util.concurrent.locks.ReentrantLock;

public class genRepo {
    //private final ReentrantLock rl;
    private String logFileName;

    private Integer flightNumber;
    private String pilotState;
    private String hostessState;
    private String[] passengerState;
    private int passengersInQueue;
    private int passengerInFlight;
    private int numberOfPassengersArrived;
    private int passengerCheckedId;
    private ArrayList<Integer> flightsmade;


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


        for(int i = 0; i < SimulPar.N_Passengers; i++){
            passengerState[i] = "GTAP";
        }


        this.pilotState = pilotStates.AT_TRANSFER_GATE.getState();
        this.hostessState = hostessStates.WAIT_FOR_NEXT_FLIGHT.getState();
        this.passengerInFlight = 0;
        this.numberOfPassengersArrived = 0;
        this.passengersInQueue = 0;
        this.flightsmade = new ArrayList<>();
        reportInitialStatus();
    }

    /**
     *  Set flight number
     *  @param flightNumber number of the actual flight
     */

    public void setFlightNumber(int flightNumber){
        this.flightNumber = flightNumber;
    }

    /**
     *  Set pilotState
     *  @param state state of the pilot
     */
    public void setPilotState(String state){
        this.pilotState = state;
    }

    /**
     *  Set hostessState
     *  @param state state of the hostess
     */
    public void setHostessState(String state){
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


    public void setNumberOfPassengersArrived(Integer numberOfPassengersArrived) {
        this.numberOfPassengersArrived = numberOfPassengersArrived;
    }

    public void setPassengerInFlight(Integer passengerInFlight) {
        this.passengerInFlight = passengerInFlight;
    }

    public void setPassengerCheckedId(int passengerCheckedId) {
        this.passengerCheckedId = passengerCheckedId;
    }

    public void setPassengersInQueue(int passengersInQueue) {
        this.passengersInQueue = passengersInQueue;
    }

    /**
     *  Write the header to the logging file.
     *  Internal operation.
     */

    private synchronized String getHeader(){
        String header;

        header = "                                                       AirLift - Description of the internal state";
        header += "\n  PT   HT\t ";
        for(int i = 0; i < SimulPar.N_Passengers; i++){
            header += "P" + (i+1) + " \t ";
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
        try{
            FileWriter log = new FileWriter(logFileName, false);
            String header = getHeader();
            String initialStatus;

            try(PrintWriter pw = new PrintWriter((log))){
                pw.print(header);
            }
            System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    /**
     *  Write a state line at the end of the logging file.
     *
     *  The current state is organized in a line to be printed.
     *  Internal operation.
     */

    public void reportBoardingStarted(){
        try {
            FileWriter log = new FileWriter(logFileName, true);
            StringBuilder lineStatus = new StringBuilder();
            lineStatus.append(String.format("\nFlight %-2d : Boarding Started.    \n", this.flightNumber));
            try(PrintWriter pw = new PrintWriter(log)){
                pw.print(lineStatus);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void reportPassengerChecked(){
        try {
            FileWriter log = new FileWriter(logFileName, true);
            StringBuilder lineStatus = new StringBuilder();
            lineStatus.append(String.format("\nFlight %-2d : Passenger %-2d checked.    \n", this.flightNumber, this.passengerCheckedId));
            try(PrintWriter pw = new PrintWriter(log)){
                pw.print(lineStatus);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reportFlightDeparted(){
        try {
            FileWriter log = new FileWriter(logFileName, true);
            StringBuilder lineStatus = new StringBuilder();
            lineStatus.append(String.format("\nFlight %-2d : Departed with %-2d passengers.    \n", this.flightNumber, passengerInFlight));
            this.flightsmade.add(passengerInFlight);
            try(PrintWriter pw = new PrintWriter(log)){
                pw.print(lineStatus);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reportFlightArrived(){
        try {
            FileWriter log = new FileWriter(logFileName, true);
            StringBuilder lineStatus = new StringBuilder();
            lineStatus.append(String.format("\nFlight %-2d : arrived.    \n", this.flightNumber));
            try(PrintWriter pw = new PrintWriter(log)){
                pw.print(lineStatus);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reportFlightReturning(){
        try {
            FileWriter log = new FileWriter(logFileName, true);
            StringBuilder lineStatus = new StringBuilder();
            lineStatus.append(String.format("\nFlight %-2d : returning.    \n", this.flightNumber));
            try(PrintWriter pw = new PrintWriter(log)){
                pw.print(lineStatus);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finalReport(){
        try {
            FileWriter log = new FileWriter(logFileName, true);
            try (PrintWriter pw = new PrintWriter(log)) {
                pw.println("Airlift sum up:");
                for (int i = 0; i < this.flightsmade.size(); i++) {
                    pw.println("Flight " + (i+1) + " transported " + this.flightsmade.get(i) + " passengers");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public synchronized void reportStatus(){
        try{
            FileWriter log = new FileWriter(logFileName, true);
            StringBuilder lineStatus = new StringBuilder();                                     // state line to be printed


            lineStatus.append(String.format("%-4s  %-2s   ", pilotState, hostessState));
            for(int i = 0; i < SimulPar.N_Passengers; i++){
                lineStatus.append(String.format("    %s", passengerState[i]));
            }

            lineStatus.append(String.format("             %-2d      %-2d     %-2d ", passengersInQueue, passengerInFlight, numberOfPassengersArrived));
            lineStatus.append("\n");

            try(PrintWriter pw = new PrintWriter(log)){
                pw.print(lineStatus);
            }

            //System.out.println("Successfully wrote to the file.");
        }
        catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }




    }

}
