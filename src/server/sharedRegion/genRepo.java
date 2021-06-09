package server.sharedRegion;

import commInfra.SimulPar;
import interfaces.genRepoInterface;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import commInfra.states.*;


/**
 * Implementation of the General Repository Shared Memory
 * Responsible for logging all changes during the program run.
 * @author Tomás Freitas
 * @author Tiago Gomes
 */
public class genRepo implements genRepoInterface {

    /**
     * Name of the file where the logger will be saved.
     */
    private String logFileName;

    /**
     * Number of the current flight
     */
    private Integer flightNumber;

    /**
     * State of pilot
     */
    private String pilotState;

    /**
     * State of hostess
     */
    private String hostessState;

    /**
     * State of passengers # (# - 0 .. 20) - GTAP (GOING_TO_AIRPORT) / INQE (IN_QUEUE) / INFL (IN_FLIGHT) / ATDS (AT_DESTINATION)
     */
    private String[] passengerState;

    /**
     * Number of passengers in the transfer gate
     */
    private int passengersInQueue;

    /**
     * Number of passengers in the transfer plane
     */
    private int passengerInFlight;

    /**
     * Number of passengers that arrived at the destination
     */
    private int numberOfPassengersArrived;

    /**
     * Id of the checked passenger
     */
    private int passengerCheckedId;

    /**
     * Number of flights made and how many passengers where transported
     */
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

    @Override
    public void setFlightNumber(int flightNumber){
        this.flightNumber = flightNumber;
    }

    /**
     *  Set pilotState
     *  @param state state of the pilot
     */
    @Override
    public void setPilotState(String state){
        this.pilotState = state;
    }

    /**
     *  Set hostessState
     *  @param state state of the hostess
     */
    @Override
    public void setHostessState(String state){
        this.hostessState = state;
    }

    /**
     *  Set state of passenger
     *  @param id id passenger to set state
     *  @param state state of the passenger
     */
    @Override
    public synchronized void setPassengerState(int id, String state){
        this.passengerState[id-1] = state;
    }

    /**
     *  Set number of passengers arrived
     *  @param numberOfPassengersArrived number of passengers at destination
     */
    @Override
    public void setNumberOfPassengersArrived(Integer numberOfPassengersArrived) {
        this.numberOfPassengersArrived = numberOfPassengersArrived;
    }

    /**
     *  Set passenger in flight
     *  @param passengerInFlight Number of passengers in flight
     */
    @Override
    public void setPassengerInFlight(int passengerInFlight) {
        this.passengerInFlight = passengerInFlight;
    }

    /**
     *  Set passenger checked
     *  @param passengerCheckedId ID of passenger being checked
     */
    @Override
    public void setPassengerCheckedId(int passengerCheckedId) {
        this.passengerCheckedId = passengerCheckedId;
    }

    /**
     *  Set passengers in transfer gate (queue)
     *  @param passengersInQueue Number of passengers in Queue
     */
    @Override
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

    /**
     * Boarding started
     * Append the message to the logger file.
     */
    @Override
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

    /**
     * Passenger checked
     * Append the message to the logger file.
     */
    @Override
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

    /**
     * Flight departed
     * Append the message to the logger file.
     */
    @Override
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

    /**
     * Flight arrived
     * Append the message to the logger file.
     */
    @Override
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

    /**
     * Flight returning
     * Append the message to the logger file.
     */
    @Override
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

    /**
     * Calculate the Airlift sum up (number of passengers transported per flight)
     * Append the final report to the logger file.
     */
    @Override
    public void finalReport(){
        try {
            FileWriter log = new FileWriter(logFileName, true);
            try (PrintWriter pw = new PrintWriter(log)) {
                pw.println("\nAirlift sum up:");
                for (int i = 0; i < this.flightsmade.size(); i++) {
                    pw.println("Flight " + (i+1) + " transported " + this.flightsmade.get(i) + " passengers");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Report status
     * Append the message to the logger file.
     */
    @Override
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
