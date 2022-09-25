package com.prd.ConnectedTeam.guess.gamesMenagemet.backend.broadcaster;

import com.prd.ConnectedTeam.entity.Account;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.GuessController;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.db.Item;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.listeners.BroadcastListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.shared.Registration;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Broadcaster implements Serializable {

    static Executor executor = Executors.newSingleThreadExecutor();
    static Map<Account, BroadcastListener> listeners = new HashMap();
    static List<Account> accountList = new ArrayList<>();
    static Map<String,Integer> votes = new HashMap();
    static ArrayList<String> strings = new ArrayList<>();
    static int indiziRicevuti = 0;
    static ArrayList<Item> items = new ArrayList<>();
    static List<GuessController.PartitaThread> partiteThread = new ArrayList<>();
    static int in = 0;

    public static synchronized Registration register(Account account, BroadcastListener broadcastListener) {
        accountList.add(account);
        /*for (int i=0; i<Broadcaster.getAccountList().size(); i++){
            if (accountList.get(i).getId() == account.getId()){
                in++;
            }
        }
        if (in == 1){
            listeners.put(account,broadcastListener);
        }*/
        listeners.put(account,broadcastListener);
        System.out.println("sono il boradcaster ed è stato chiamato register "+ listeners.size()+ "  ui:"+ broadcastListener);
        return () -> {
            synchronized (Broadcaster.class) {
                listeners.remove(account);
            }
        };
    }

    public static synchronized void unregister(Account account, BroadcastListener broadcastListener){
        System.out.println(listeners.size());
        listeners.remove(account,broadcastListener);
        accountList.remove(account);
        System.out.println(listeners.size());
    }

    public static synchronized void startGame(GuessController.PartitaThread partitaThread, Item item){
        items.add(item);
        partiteThread.add(partitaThread);
        listeners.forEach((account, broadcastListener) -> {
            executor.execute(() -> {
                broadcastListener.startGame1();
            });
        });
    }

    public static synchronized void riceveIndizio( final String message) {
        System.out.println("Indizio nel broadcaster: "+ message);
        indiziRicevuti++;
        listeners.forEach((aLong, broadcastListener) -> {
            executor.execute(()-> {
                broadcastListener.receiveIndizio(message);
            });
        });
    }

    public static synchronized void countDown(String n){
        listeners.forEach((aLong, broadcastListener) -> {
            executor.execute(()-> {
                broadcastListener.countDown(n);
            });
        });
    }

    public static synchronized void aggiornaUtentiConnessi(UI ui){
        try {
            listeners.forEach((account, broadcastListener) -> {
                executor.execute(() -> {
                    broadcastListener.countUser(ui,account.getNome());
                });
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static synchronized void addUsers(UI ui){
        try {
            listeners.forEach((account, broadcastListener) -> {
                executor.execute(() -> {
                    broadcastListener.addUsers(ui,in);
                });
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static synchronized void browserIsLeavingCalled(Account account1){
        try {
            listeners.forEach((account, broadcastListener) -> {
                executor.execute(() -> {
                    broadcastListener.browserIsLeavingCalled(account1);
                });
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static Map<Account, BroadcastListener> getListeners() {
        return listeners;
    }

    public static Map<String, Integer> getVotes() {
        return votes;
    }

    public static synchronized void getVotoParola(Map<String, Integer> stringIntegerMap) {
        try {
            votes = stringIntegerMap;
            listeners.forEach((account, broadcastListener) -> {
                executor.execute(() -> {
                    broadcastListener.parolaVotata();
                });
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static synchronized void addString(String s){
        strings.add(s);
    }

    public static ArrayList<String> getStrings() {
        return strings;
    }

    public static List<Account> getAccountList() {
        return accountList;
    }

    public static synchronized void partitaVincente(String s, Integer integer) {
        try {
            listeners.forEach((account, broadcastListener) -> {
                executor.execute(() -> {
                    broadcastListener.partititaVincente(s,integer);
                });
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static synchronized void partitanonVincente() {
        try {
            listeners.forEach((account, broadcastListener) -> {
                executor.execute(() -> {
                    broadcastListener.partititanonVincente();
                });
            });
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static int getIndiziRicevuti() {
        return indiziRicevuti;
    }

    public static void setIndiziRicevuti(int indiziRicevuti) {
        Broadcaster.indiziRicevuti = indiziRicevuti;
    }

    public static ArrayList<Item> getItems() {
        return items;
    }

    public static List<GuessController.PartitaThread> getPartiteThread() {
        return partiteThread;
    }

    public static synchronized void logOut(Account account){
        System.out.println(listeners.size());
        listeners.remove(account);
        accountList.remove(account);
        System.out.println(listeners.size());

    }


    public static int getIn() {
        return in;
    }
}
