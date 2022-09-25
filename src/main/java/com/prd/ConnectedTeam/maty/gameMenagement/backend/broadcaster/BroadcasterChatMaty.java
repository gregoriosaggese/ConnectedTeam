package com.prd.ConnectedTeam.maty.gameMenagement.backend.broadcaster;

import com.prd.ConnectedTeam.maty.gameMenagement.backend.listeners.ChatListenerMaty;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


public class BroadcasterChatMaty implements Serializable {

    private static final List<ChatListenerMaty> listeners = new CopyOnWriteArrayList<ChatListenerMaty>();

    public static void register(ChatListenerMaty listener) {
        listeners.add(listener);
    }

    public static void broadcast(final String message) {
        for (ChatListenerMaty listener : listeners) {
            listener.receiveBroadcast(message);
        }
    }

    public static List<ChatListenerMaty> getListeners() {
        return listeners;
    }

}
