package states;

public enum hostessStates {

    WAIT_FOR_NEXT_FLIGHT("WFF"),
    WAIT_FOR_PASSENGER("WFP"),
    CHECK_PASSENGER("CP"),
    READY_TO_FLY("RF");

    private String state;

    hostessStates(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
