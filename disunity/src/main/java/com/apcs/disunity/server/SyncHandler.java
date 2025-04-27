package com.apcs.disunity.server;

import java.util.LinkedList;
import java.util.List;

import static com.apcs.disunity.server.CODEC.decodeInt;

public abstract class SyncHandler {
    public static final int HOST_ID = 0;
    private static SyncHandler instance;

    {
        if (instance == null) {
            instance = this;
        } else {
            throw new SingletonViolationException("You can only make one instance of SyncHandler.");
        }
    }

    protected final List<Object> syncs = new LinkedList<>();

    public void register(Object obj) {
        syncs.add(obj);
    }

    public static SyncHandler getInstance() {
        return instance;
    }

    public final boolean isClient() {
        return getEndpointId() != HOST_ID;
    }

    public abstract int getEndpointId();
    public abstract void start();
    public String getLabel() {
        return isClient() ? "[CLIENT "+getEndpointId()+"]" : "[SERVER]";
    }

    public static void log(String fmt, Object... arg) {
        System.out.printf(getInstance().getLabel()+ ' ' + fmt, arg);
    }
}