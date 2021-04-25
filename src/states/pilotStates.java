package states;

/**
 * Enum implementing the typical hostess States:
 *
 * <ul>
 *     <li>AT_TRANSFER_GATE - The pilot parks the plane at transfer gate.</li>
 *     <li>READY_FOR_BOARDING - The pilot informs the hostess that the plane is ready for boarding.</li>
 *     <li>WAIT_FOR_BOARDING - The pilot waits for the hostess notifying him that the boarding is complete.</li>
 *     <li>FLYING_FORWARD - The pilot flies to the destination town.</li>
 *     <li>DEBOARDING - After landing and parking the plane at the arrival gate, the pilot announces the passengers that they may leave.</li>
 *     li>FLYING_BACK - When the last passenger has exited, the pilot flies back to the origin town.</li>
 * </ul>
 */

public enum pilotStates {

    /**
     * The pilot parks the plane at transfer gate.
     */
    AT_TRANSFER_GATE("ATRG"),

    /**
     * The pilot informs the hostess that the plane is ready for boarding.
     */
    READY_FOR_BOARDING("RDFB"),

    /**
     * The pilot waits for the hostess notifying him that the boarding is complete.
     */
    WAIT_FOR_BOARDING("WTFB"),

    /**
     * The pilot flies to the destination town.
     */
    FLYING_FORWARD("FLFW"),

    /**
     * After landing and parking the plane at the arrival gate, the pilot announces the passengers that they may leave.
     */
    DEBOARDING("DRPP"),

    /**
     * When the last passenger has exited, the pilot flies back to the origin town.
     */
    FLYING_BACK("FLBK");

    private String state;

    pilotStates(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
