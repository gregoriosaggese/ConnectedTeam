package com.prd.ConnectedTeam.guess.gamesMenagemet.backend.broadcaster;

import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.listeners.ChatListener;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class BroadcasterChat implements Serializable {

    private static final List<ChatListener> listeners = new CopyOnWriteArrayList<ChatListener>();

    public static void register(ChatListener listener) {
        listeners.add(listener);
    }

    public static void broadcast(final String message) {
        for (ChatListener listener : listeners) {
            listener.receiveBroadcast(message);
        }
    }

    public static List<ChatListener> getListeners() {
        return listeners;
    }

}
