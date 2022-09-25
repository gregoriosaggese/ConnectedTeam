package com.prd.ConnectedTeam;

import com.prd.ConnectedTeam.entity.Account;
import com.prd.ConnectedTeam.entity.Partita;
import com.prd.ConnectedTeam.entity.Punteggio;
import com.prd.ConnectedTeam.entityRepository.AccountRepository;
import com.prd.ConnectedTeam.entityRepository.PartitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.Timestamp;

@SpringBootApplication
public class App {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PartitaRepository partitaRepository;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner initializeData() {
        return args -> {

            Account gregorio=new Account("Gregorio", "gregorio@gmail.com", "gregorio","0");
            Account luigi=new Account("Luigi", "luigi@gmail.com", "luigi","0");
            Account francesca=new Account("Francesca", "francesca@gmail.com", "francesca","1");
            Account antonio=new Account("Antonio", "antonio@gmail.com", "antonio","0");
            Account gianluca=new Account("Gianluca", "gianluca@gmail.com", "gianluca","0");
            Account michela=new Account("Michela", "michela@gmail.com", "michela","1");
            Account simone = new Account("Simone", "simone@gmail.com", "simone","0");

            accountRepository.save(simone);
            accountRepository.save(luigi);
            accountRepository.save(gregorio);
            accountRepository.save(francesca);
            accountRepository.save(antonio);
            accountRepository.save(gianluca);
            accountRepository.save(michela);

            Partita partita2= new Partita(Timestamp.valueOf("2019-05-03 11:20:09"),"Guess");
            partita2.addPunteggio(new Punteggio(luigi,10));
            partita2.addPunteggio(new Punteggio(gregorio,70));
            partita2.addPunteggio(new Punteggio(michela,30));
            partita2.addPunteggio(new Punteggio(francesca,60));
            partitaRepository.save(partita2);

            Partita partita4= new Partita(Timestamp.valueOf("2019-03-21 16:20:09"),"Guess");
            partita4.addPunteggio(new Punteggio(luigi,40));
            partita4.addPunteggio(new Punteggio(gregorio,10));
            partita4.addPunteggio(new Punteggio(michela,30));
            partita4.addPunteggio(new Punteggio(francesca,60));
            partitaRepository.save(partita4);

            Partita partita5= new Partita(Timestamp.valueOf("2019-01-04 22:17:02"),"Guess");
            partita5.addPunteggio(new Punteggio(luigi,20));
            partita5.addPunteggio(new Punteggio(gregorio,70));
            partita5.addPunteggio(new Punteggio(michela,30));
            partita5.addPunteggio(new Punteggio(francesca,60));
            partitaRepository.save(partita5);

            Partita partita6= new Partita(Timestamp.valueOf("2019-05-04 11:20:09"),"Guess");
            partita6.addPunteggio(new Punteggio(luigi,10));
            partita6.addPunteggio(new Punteggio(gregorio,100));
            partita6.addPunteggio(new Punteggio(michela,30));
            partita6.addPunteggio(new Punteggio(francesca,60));
            partitaRepository.save(partita6);

            Partita partita7= new Partita(Timestamp.valueOf("2019-02-21 16:20:09"),"Guess");
            partita7.addPunteggio(new Punteggio(luigi,40));
            partita7.addPunteggio(new Punteggio(gregorio,10));
            partita7.addPunteggio(new Punteggio(michela,30));
            partita7.addPunteggio(new Punteggio(francesca,60));
            partitaRepository.save(partita7);

            Partita partita8= new Partita(Timestamp.valueOf("2019-06-01 12:17:02"),"Guess");
            partita8.addPunteggio(new Punteggio(luigi,20));
            partita8.addPunteggio(new Punteggio(gregorio,70));
            partita8.addPunteggio(new Punteggio(michela,30));
            partita8.addPunteggio(new Punteggio(francesca,60));
            partitaRepository.save(partita8);

            Partita partita9= new Partita(Timestamp.valueOf("2019-06-01 12:07:02"),"Guess");
            partita9.addPunteggio(new Punteggio(luigi,20));
            partita9.addPunteggio(new Punteggio(gregorio,70));
            partita9.addPunteggio(new Punteggio(michela,30));
            partita9.addPunteggio(new Punteggio(francesca,60));
            partitaRepository.save(partita9);

            Partita partita10= new Partita(Timestamp.valueOf("2019-06-01 12:18:02"),"Guess");
            partita10.addPunteggio(new Punteggio(luigi,20));
            partita10.addPunteggio(new Punteggio(gregorio,70));
            partita10.addPunteggio(new Punteggio(michela,30));
            partita10.addPunteggio(new Punteggio(francesca,60));
            partitaRepository.save(partita10);

            Partita partita11= new Partita(Timestamp.valueOf("2019-06-01 12:25:02"),"Guess");
            partita11.addPunteggio(new Punteggio(luigi,20));
            partita11.addPunteggio(new Punteggio(gregorio,70));
            partita11.addPunteggio(new Punteggio(michela,30));
            partita11.addPunteggio(new Punteggio(francesca,60));
            partitaRepository.save(partita11);

            Partita partita12= new Partita(Timestamp.valueOf("2019-06-01 12:45:02"),"Guess");
            partita12.addPunteggio(new Punteggio(luigi,20));
            partita12.addPunteggio(new Punteggio(gregorio,70));
            partita12.addPunteggio(new Punteggio(michela,30));
            partita12.addPunteggio(new Punteggio(francesca,60));
            partitaRepository.save(partita12);

            accountRepository.findAll().forEach(account -> {
                System.out.println(account.getEmail());
            });
        };
    }
}
