package com.prd.ConnectedTeam.guess.gamesMenagemet.backend;


import com.prd.ConnectedTeam.entity.Account;
import com.prd.ConnectedTeam.entity.Partita;
import com.prd.ConnectedTeam.entity.Punteggio;
import com.prd.ConnectedTeam.entityRepository.PartitaRepository;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.broadcaster.Broadcaster;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.db.Item;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.db.ItemRepository;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.spring.annotation.VaadinSessionScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.*;

@Component
@VaadinSessionScope
@Lazy
public class GuessController {

    private ItemRepository itemRepository;
    private Item item;
    private Random random= new Random();
    private PartitaThread partitaThread;
    private List<Account> accounts;
    private int i;
    private int totTime;
    boolean vinta = false;

    private PartitaRepository partitaRepository;
    protected Partita partita;

    public GuessController(PartitaRepository partitaRepository){
        this.partitaRepository= partitaRepository;
    }

    protected void addPunteggio(Punteggio punteggio){
        partita.addPunteggio(punteggio);
    }

    public void startGame(Partita parita){

        this.partita = parita;

        itemRepository = (ItemRepository) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("itemRepository");
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

    public Item getItem() {
        return item;
    }

    public boolean partitaVincente(String parolaVincente,Item item){
        if(parolaVincente.equalsIgnoreCase(item.getParola())){
            vinta = true;
            partita= new Partita(new Timestamp(new Date().getTime()), "Guess");
            partita.setVinta(true);
            accounts = Broadcaster.getAccountList();
            for (Account a: accounts) {
                if(Broadcaster.getIndiziRicevuti() == 1){
                    addPunteggio(new Punteggio(a,100));
                }else if(Broadcaster.getIndiziRicevuti() == 2){
                    addPunteggio(new Punteggio(a,60));
                }else if(Broadcaster.getIndiziRicevuti() == 3){
                    addPunteggio(new Punteggio(a,30));
                }else if(Broadcaster.getIndiziRicevuti() == 4){
                    addPunteggio(new Punteggio(a,10));
                }
            }
            partitaRepository.save(partita);
        }else {
            vinta = false;
            partita= new Partita(new Timestamp(new Date().getTime()), "Guess");
            partita.setVinta(false);
            accounts = Broadcaster.getAccountList();
            for (Account a: accounts) {
                addPunteggio(new Punteggio(a,0));
            }
            partitaRepository.save(partita);

        }
        Broadcaster.setIndiziRicevuti(0);
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
            Broadcaster.riceveIndizio(indizio);
            i++;
            totTime = 30;
            timer.scheduleAtFixedRate(new TimerTask() {
                public void run() {
                    String time = String.format("%02d", totTime % 60);
                    System.out.println(time);
                    Broadcaster.countDown(time);
                    if (totTime == 0 && i<3) {
                        String indizio = item.getIndizio(i);
                        Broadcaster.riceveIndizio(indizio);
                        totTime=30;
                        i++;
                    }else if(i==3 && totTime==0){
                        String indizio = item.getIndizio(i);
                        Broadcaster.riceveIndizio(indizio);
                        totTime=30;
                        i++;
                    }
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
            partita= new Partita(new Timestamp(new Date().getTime()), "Guess");
            accounts = Broadcaster.getAccountList();
            Broadcaster.setIndiziRicevuti(0);
            for (Account a: accounts) {
                addPunteggio(new Punteggio(a,0));
            }
            partitaRepository.save(partita);
            System.out.println("Fine Partita");
            partitaThread.interrupt();
            partitaThread.stopTimer();
            Broadcaster.partitanonVincente();
        }

        public void stopTimer(){
            timer.cancel();
        }
    }


}
