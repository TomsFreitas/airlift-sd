package commInfra.messages;

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


    /**
     * Message constructor
     * @param type The type of message
     * @param id ID of the message creator
     */
    public message(messageType type, int id){
        this.messageType = type;
        this.ID = id;

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
}
