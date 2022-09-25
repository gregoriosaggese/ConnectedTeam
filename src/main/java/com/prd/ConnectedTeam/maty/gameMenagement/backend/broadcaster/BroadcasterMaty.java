package com.prd.ConnectedTeam.maty.gameMenagement.backend.broadcaster;

import com.prd.ConnectedTeam.entity.Account;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.MatyController;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.db.ItemMaty;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.listeners.BroadcastListenerMaty;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.shared.Registration;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BroadcasterMaty implements Serializable {

    static Executor executor = Executors.newSingleThreadExecutor();
    static Map<Account, BroadcastListenerMaty> listeners = new HashMap();
    static List<Account> accountList = new ArrayList<>();
    static ArrayList<String> strings = new ArrayList<>();
    static int indiziRicevuti = 0;
    static ArrayList<ItemMaty> items = new ArrayList<>();
    static List<MatyController.PartitaThread> partiteThread = new ArrayList<>();
    static int in = 0;
    static ArrayList<Integer> integers = new ArrayList<>();
    static ArrayList<Integer> contClick = new ArrayList<>();

    public static ArrayList<Integer> getIntegers() {
        return integers;
    }

    public static ArrayList<Integer> getContClick() {
        return contClick;
    }

    public static synchronized void addContClick(){
        contClick.add(new Integer(1));
    }

    public static synchronized void addIntegers(Integer integer){
        integers.add(integer);
    }

    public static synchronized void numeroInserito(String operazione) {
        try {

            listeners.forEach((account, broadcastListener) -> {
                executor.execute(() -> {
                    broadcastListener.numeroInserito(operazione);
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

    public static synchronized Registration register(Account account, BroadcastListenerMaty broadcastListener) {
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
            synchronized (BroadcasterMaty.class) {
                listeners.remove(account);
            }
        };
    }

    public static synchronized void unregister(Account account, BroadcastListenerMaty broadcastListener){
        System.out.println(listeners.size());
        listeners.remove(account,broadcastListener);
        accountList.remove(account);
        System.out.println(listeners.size());
    }

    public static synchronized void startGame(MatyController.PartitaThread partitaThread, ItemMaty item){
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

    public static synchronized void numeroDaSottrarre(String numero,String numOriginale){
        listeners.forEach((aLong, broadcastListener) -> {
            executor.execute(()-> {
                broadcastListener.numeroDaSotrarre(numero,numOriginale);
            });
        });
    }

    public static synchronized void numeroDASommare(String numOriginale){
        listeners.forEach((aLong, broadcastListener) -> {
            executor.execute(()-> {
                broadcastListener.numeroDaSommare(numOriginale);
            });
        });
    }

    public static synchronized void countDown(String time){
        listeners.forEach((aLong, broadcastListener) -> {
            executor.execute(()-> {
                broadcastListener.countDown(time);
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

    public static Map<Account, BroadcastListenerMaty> getListeners() {
        return listeners;
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

    public static int getIndiziRicevuti() {
        return indiziRicevuti;
    }

    public static void setIndiziRicevuti(int indiziRicevuti) {
        BroadcasterMaty.indiziRicevuti = indiziRicevuti;
    }

    public static ArrayList<ItemMaty> getItems() {
        return items;
    }

    public static List<MatyController.PartitaThread> getPartiteThread() {
        return partiteThread;
    }

    public static int getIn() {
        return in;
    }
}
