package states;

public enum pilotStates {

    AT_TRANSFER_GATE("ATG"),
    READY_FOR_BOARDING("RFB"),
    WAIT_FOR_BOARDING("WFB"),
    FLYING_FORWARD("FF"),
    DEBOARDING("DB"),
    FLYING_BACK("FB");

    private String state;

    pilotStates(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
