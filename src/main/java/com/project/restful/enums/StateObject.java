package com.project.restful.enums;

public enum StateObject {
    CLOSED((byte) 0),
    ACTIVE((byte) 1),
    PROCEDURES((byte) 2),
    COMPLETED((byte) 3);

    private final Byte State;

    StateObject(Byte state){
        this.State = state;
    }

    public Byte getState() {
        return State;
    }
}
