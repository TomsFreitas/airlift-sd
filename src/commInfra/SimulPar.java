package commInfra;

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
    public static final int Passenger_MinSleep = 500;

    /**
     * Passenger maximum sleep time
     */
    public static final int Passenger_MaxSleep = 2000;

    public static final String planeServerHost = "localhost";
    public static final int planeServerPort = 22190;

    public static String genRepoServerHost = "localhost";
    public static int genRepoServerPort = 22191;

    public static String departureAirportServerHost = "localhost";
    public static int departureAirportServerPort = 22192;

    public static String destinationAirportServerHost = "localhost";
    public static int destinationAirportServerPort = 22193;




    /**
     * It can not be instantiated
     */
    private SimulPar(){}
}