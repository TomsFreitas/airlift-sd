package states;


/**
 * Enum implementing the typical hostess States:
 *
 * <ul>
 *     <li>GOING_TO_AIRPORT - The passenger goes to the airport.</li>
 *     <li>IN_QUEUE - The passenger queue in at transfer gate waiting to be called by the hostess.</li>
 *     <li>IN_FLIGHT - The passenger boarded the flight.</li>
 *     <li>AT_DESTINATION - The passenger arrived at the destination.</li>
 * </ul>
 */

public enum passengerStates {

    /**
     * The passenger goes to the airport.
     */
    GOING_TO_AIRPORT("GTAP"),

    /**
     * The passenger queue in at transfer gate waiting to be called by the hostess.
     */
    IN_QUEUE("INQE"),

    /**
     * The passenger boarded the flight.
     */
    IN_FLIGHT("INFL"),

    /**
     * The passenger arrived at the destination.
     */
    AT_DESTINATION("ATDS");

    private String state;

    passengerStates(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
