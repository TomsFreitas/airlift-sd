package states;

/**
 * Enum implementing the typical hostess States:
 *
 * <ul>
 *     <li>WAIT_FOR_NEXT_FLIGHT - The hostess waits for the next flight (pilot parks the plane at the departure gate).</li>
 *     <li>WAIT_FOR_PASSENGER - The hostess waits for passengers arrives at the transfer gate.</li>
 *     <li>CHECK_PASSENGER - The hostess calls a passenger to check the documents and allow him to board the plane.</li>
 *     <li>READY_TO_FLY - The hostess notifies the pilot that the flight is ready to take off.</li>
 * </ul>
 */
public enum hostessStates {

    /**
     * The hostess waits for the next flight (pilot parks the plane at the departure gate).
     */
    WAIT_FOR_NEXT_FLIGHT("WTFL"),

    /**
     * The hostess waits for passengers arrives at the transfer gate.
     */
    WAIT_FOR_PASSENGER("WTPS"),

    /**
     * The hostess calls a passenger to check the documents and allow him to board the plane.
     */
    CHECK_PASSENGER("CKPS"),

    /**
     * The hostess notifies the pilot that the flight is ready to take off.
     */
    READY_TO_FLY("RDTF");

    private String state;

    hostessStates(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
