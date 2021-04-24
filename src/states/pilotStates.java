package states;

public enum pilotStates {

    AT_TRANSFER_GATE("ATRG"),
    READY_FOR_BOARDING("RDFB"),
    WAIT_FOR_BOARDING("WTFB"),
    FLYING_FORWARD("FLFW"),
    DEBOARDING("DRPP"),
    FLYING_BACK("FLBK");

    private String state;

    pilotStates(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
