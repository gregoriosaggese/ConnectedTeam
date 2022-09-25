package com.prd.ConnectedTeam.maty.gameMenagement.backend;


import com.prd.ConnectedTeam.entity.Account;
import com.prd.ConnectedTeam.entity.Partita;
import com.prd.ConnectedTeam.entity.Punteggio;
import com.prd.ConnectedTeam.entityRepository.PartitaRepository;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.broadcaster.BroadcasterMaty;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.db.ItemMaty;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.db.ItemRepositoryMaty;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
@VaadinSessionScope
@Lazy
public class MatyController {

    private ItemRepositoryMaty itemRepository;
    private ItemMaty item;
    private Random random= new Random();
    private PartitaThread partitaThread;
    private List<Account> accounts;
    private int i;
    private int totTime;
    boolean vinta = false;

    private PartitaRepository partitaRepository;
    protected Partita partita;

    public MatyController(PartitaRepository partitaRepository){
        this.partitaRepository= partitaRepository;
    }

    protected void addPunteggio(Punteggio punteggio){
        partita.addPunteggio(punteggio);
    }

    public void startGame(Partita parita){

        this.partita = parita;

        itemRepository = (ItemRepositoryMaty) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("itemRepositoryMaty");
        partitaRepository = (PartitaRepository) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("partitaRepository");

        int tot = itemRepository.numeroRighe();
        int rand = random.nextInt(tot)+1;
        item = itemRepository.findOneById(rand);
        if(partitaThread!=null){
            partitaThread.interrupt();
            partitaThread.stopTimer();
        }
        partitaThread=new PartitaThread();
        partitaThread.start();
    }

    public ItemMaty getItem() {
        return item;
    }

    public boolean partitaVincente(String parolaVincente,ItemMaty item){

        if(parolaVincente.equalsIgnoreCase(item.getParola())) {
            vinta = true;
            partita = new Partita(new Timestamp(new Date().getTime()), "Maty");
            partita.setVinta(true);
            accounts = BroadcasterMaty.getAccountList();
            for (Account a : accounts) {
                if (BroadcasterMaty.getIndiziRicevuti() == 0) {
                    addPunteggio(new Punteggio(a, 120));
                } else if (BroadcasterMaty.getIndiziRicevuti() == 1) {
                    addPunteggio(new Punteggio(a, 100));
                } else if (BroadcasterMaty.getIndiziRicevuti() == 2) {
                    addPunteggio(new Punteggio(a, 60));
                } else if (BroadcasterMaty.getIndiziRicevuti() == 3) {
                    addPunteggio(new Punteggio(a, 30));
                } else if (BroadcasterMaty.getIndiziRicevuti() == 4) {
                    addPunteggio(new Punteggio(a, 10));
                }
            }
            partitaRepository.save(partita);
            System.out.println("Sono in controller true"+BroadcasterMaty.getIndiziRicevuti());
            BroadcasterMaty.setIndiziRicevuti(0);
        }else {
            vinta = false;
            partita= new Partita(new Timestamp(new Date().getTime()), "Maty");
            partita.setVinta(false);
            accounts = BroadcasterMaty.getAccountList();
            for (Account a: accounts) {
                addPunteggio(new Punteggio(a,0));
            }
            partitaRepository.save(partita);
            System.out.println("Sono in controller false"+BroadcasterMaty.getIndiziRicevuti());

        }



        return vinta;
    }

    public PartitaThread getPartitaThread() {
        return partitaThread;
    }

    public class PartitaThread extends Thread{
        private Timer timer;
        @Override
        public void run() {
            timer = new Timer();
            i=0;
            String indizio = item.getIndizio(i);
            //BroadcasterMaty.riceveIndizio(indizio);
            i++;
            totTime = 300;

            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    String time = String.format("%02d:%02d", totTime / 60, totTime % 60);
                    System.out.println(time);
                    BroadcasterMaty.countDown(time);
                    /*if (totTime == 0 && i<3) {
                        String indizio = item.getIndizio(i);
                        //BroadcasterMaty.riceveIndizio(indizio);
                        totTime=10;
                        i++;
                    }else if(i==3 && totTime==0){
                        String indizio = item.getIndizio(i);
                        //BroadcasterMaty.riceveIndizio(indizio);
                        totTime=10;
                        i++;
                    }*/
                    totTime--;
                    if (totTime < 0) {
                        timer.cancel();
                        terminaPartita();
                    }
                }
            }, 0, 1000);

            return;
        }

        public void terminaPartita(){
            partita= new Partita(new Timestamp(new Date().getTime()), "Maty");
            accounts = BroadcasterMaty.getAccountList();
            BroadcasterMaty.setIndiziRicevuti(0);
            for (Account a: accounts) {
                addPunteggio(new Punteggio(a,5));
            }
            partitaRepository.save(partita);
            System.out.println("Fine Partita");
            partitaThread.interrupt();
            partitaThread.stopTimer();
            BroadcasterMaty.setIndiziRicevuti(0);
            BroadcasterMaty.partitanonVincente();
        }

        public void stopTimer(){
            timer.cancel();
        }
    }


}
