package states;

public enum hostessStates {

    WAIT_FOR_NEXT_FLIGHT("WTFL"),
    WAIT_FOR_PASSENGER("WTPS"),
    CHECK_PASSENGER("CKPS"),
    READY_TO_FLY("RDTF");

    private String state;

    hostessStates(String state){
        this.state = state;
    }

    public String getState() {
        return state;
    }
}
