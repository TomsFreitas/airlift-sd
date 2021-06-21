package commInfra;
import commInfra.states.*;

import java.io.Serializable;

public class ReturnObject implements Serializable {

    public static final long serialVersionUID = 2021L;

    private int passengerschecked;
    private boolean bool;
    private passengerStates passengerState;
    private pilotStates pilotState;
    private hostessStates hostessState;


    public int getPassengerschecked() {
        return passengerschecked;
    }

    public boolean isBool() {
        return bool;
    }

    public passengerStates getPassengerState() {
        return passengerState;
    }

    public pilotStates getPilotState() {
        return pilotState;
    }

    public hostessStates getHostessState() {
        return hostessState;
    }

    public ReturnObject(pilotStates state) {
        this.pilotState = state;
    }

    public ReturnObject(hostessStates state) {
        this.hostessState = state;
    }

    public ReturnObject(passengerStates state) {
        this.passengerState = state;
    }

    public ReturnObject(boolean bool, int passengersCheckedtmp, hostessStates state) {
        this.bool = bool;
        this.passengerschecked = passengersCheckedtmp;
        this.hostessState = state;
    }

    public ReturnObject(boolean bool, hostessStates state) {
        this.bool = bool;
        this.hostessState = state;
    }
}
