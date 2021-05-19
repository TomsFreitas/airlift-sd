package commInfra.messages;

import commInfra.states.*;
import java.io.Serializable;

public class message implements Serializable {
    /**
     * Type of message
     */
    private messageType messageType;


    /**
     * ID of the client.entity that sent the message
     */
    private int ID;

    /**
     * Placeholder for a boolean value to be sent
     */
    private boolean aBoolean;

    private String aString;

    private int anInt;
    
    private hostessStates hostessStates;
    
    private pilotStates pilotStates;
    
    private passengerStates passengerStates;

    /**
     * Message constructor
     * @param type The type of message
     * @param id ID of the message creator
     */

    public message(){}

    public message(messageType type, int id){
        this.messageType = type;
        this.ID = id;

    }


    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }


    public void setMessageType(commInfra.messages.messageType messageType) {
        this.messageType = messageType;
    }

    public messageType getMessageType() {
        return this.messageType;
    }

    public void setaBoolean(boolean aBoolean) {
        this.aBoolean = aBoolean;
    }

    public boolean getaBoolean(){
        return this.aBoolean;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public int getAnInt() {
        return anInt;
    }

    public void setaString(String aString) {
        this.aString = aString;
    }

    public String getaString() {
        return aString;
    }

    public hostessStates getHostessStates() {
        return hostessStates;
    }

    public void setHostessStates(hostessStates hostessStates) {
        this.hostessStates = hostessStates;
    }

    public pilotStates getPilotStates() {
        return pilotStates;
    }

    public void setPilotStates(pilotStates pilotStates) {
        this.pilotStates = pilotStates;
    }

    public passengerStates getPassengerStates() {
        return passengerStates;
    }

    public void setPassengerStates(passengerStates passengerStates) {
        this.passengerStates = passengerStates;
    }
    
    
}
