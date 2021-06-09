package commInfra;

/**
 * Definition of the simulation parameters
 *
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
    public static final int Passenger_MinSleep = 2000;

    /**
     * Passenger maximum sleep time
     */
    public static final int Passenger_MaxSleep = 15000;

    /**
     * Plane Server HostName
     */
    public static final String planeServerHost = "l040101-ws06.ua.pt";

    /**
     * Plane Server Port
     */
    public static final int planeServerPort = 22190;

    /**
     * General Repository Server HostName
     */
    public static String genRepoServerHost = "l040101-ws07.ua.pt";

    /**
     * General Repository Server Port
     */
    public static int genRepoServerPort = 22191;

    /**
     * Departure Airport Server HostName
     */
    public static String departureAirportServerHost = "l040101-ws01.ua.pt";

    /**
     * Departure Airport Server Port
     */
    public static int departureAirportServerPort = 22192;

    /**
     * Destination Airport Server HostName
     */
    public static String destinationAirportServerHost = "l040101-ws02.ua.pt";

    /**
     * Destination Airport Server Port
     */
    public static int destinationAirportServerPort = 22193;

    public static String RegistryHost = "l040101-ws05.ua.pt";

    public static int RegistryPort = 22194;




    /**
     * It can not be instantiated
     */
    private SimulPar(){}
}