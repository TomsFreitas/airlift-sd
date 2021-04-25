package main;

/**
 * Definition of the simulation parameters
 * @author Tomás Freitas
 * @author Tiago Gomes
 */

public final class SimulPar{

    /**
     * Number os passengers
     */
    public static final int N_Passengers = 21;

    /**
     * Flight maximum capacity
     */
    public static final int F_MaxCapacity = 10;

    /**
     * Flight minimum capacity
     */
    public static final int F_MinCapacity = 5;

    /**
     * Pilot minimum sleep time
     */
    public static final int Pilot_MinSleep = 50;

    /**
     * Pilot maximum sleep time
     */
    public static final int Pilot_MaxSleep = 200;

    /**
     * Passenger minimum sleep time
     */
    public static final int Passenger_MinSleep = 200;

    /**
     * Passenger maximum sleep time
     */
    public static final int Passenger_MaxSleep = 1000;

    /**
     * It can not be instantiated
     */
    private SimulPar(){}
}