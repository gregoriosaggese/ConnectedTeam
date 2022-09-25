package com.prd.ConnectedTeam.maty.gameMenagement.backend.broadcaster;

import com.prd.ConnectedTeam.maty.gameMenagement.backend.db.ItemMaty;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.listeners.SuggerisciListenerMaty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class BroadcasterSuggerisciMaty implements Serializable {

    private static final List<SuggerisciListenerMaty> listeners = new CopyOnWriteArrayList<SuggerisciListenerMaty>();
    private static final ArrayList<ItemMaty> items = new ArrayList<>();

    public static synchronized void addItems(ItemMaty itemMaty){
        items.add(itemMaty);
    }

    public static ArrayList<ItemMaty> getItems() {
        return items;
    }

    public static synchronized void register(SuggerisciListenerMaty listener) {
        listeners.add(listener);
    }

    public static void broadcast(final String message,String operazione,String nome,boolean operation) {
        for (SuggerisciListenerMaty listener : listeners) {
            listener.operazione(message,operazione,nome,operation);
        }
    }


    public static List<SuggerisciListenerMaty> getListeners() {
        return listeners;
    }

    public static synchronized void setOperazione(){
        for (SuggerisciListenerMaty listener : listeners) {
            //listener.receiveBroadcast(message);
            listener.setOperazione();
        }
    }



}
