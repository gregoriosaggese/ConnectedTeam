package com.prd.ConnectedTeam.guess.gamesMenagemet.backend;

import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.db.Item;
import com.prd.ConnectedTeam.guess.gamesMenagemet.backend.db.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class GuessPopulator {

 @Autowired
 private ItemRepository repositoryI;

 @EventListener
 public void onApplicationEvent(ContextRefreshedEvent event) {
  System.out.println("Sono GUESS e sono stato chiamato");

  Item item1= new Item("Eneide");
  item1.addIndizio(0,"Tra il 29 A.C e il 19 A.C");
  item1.addIndizio(1,"fra Napoli e Roma");
  item1.addIndizio(2,"in latino");
  item1.addIndizio(3,"per narrare le gesta di Enea");
  repositoryI.save(item1);

  Item item= new Item("rivoluzione francese");
  item.addIndizio(0,"Nel 1789");
  item.addIndizio(1,"in Francia");
  item.addIndizio(2,"assaltando la Bastiglia");
  item.addIndizio(3,"per il malcontento sociale");
  repositoryI.save(item);

  Item item2= new Item("scoperta della gravità");
  item2.addIndizio(0,"Nel 1966");
  item2.addIndizio(1,"sotto un melo");
  item2.addIndizio(2,"mentre stava seduto");
  item2.addIndizio(3,"perché una mela gli cadde in testa");
  repositoryI.save(item2);

  Item item3= new Item("Torre Eiffel");
  item3.addIndizio(0,"Tra il 1887 e il 1889");
  item3.addIndizio(1,"a Parigi");
  item3.addIndizio(2,"in ferro battuto");
  item3.addIndizio(3,"per la commemorazione del centenario della Rivoluzione Francese");
  repositoryI.save(item3);

  /*Item item4= new Item("carota");
  item4.addIndizio(0,"dopo una grande nevicata");
  item4.addIndizio(1,"in mezzo a due bottoni");
  item4.addIndizio(2,"arancione");
  item4.addIndizio(3,"per fare il naso al pupazzo di neve");
  repositoryI.save(item4);

  Item item5= new Item("Pasqua");
  item5.addIndizio(0,"una domnica di primavera");
  item5.addIndizio(1,"lontano da casa");
  item5.addIndizio(2,"con chi vuoi");
  item5.addIndizio(3,"perchè non è come il Natale che lo passi con i tuoi");
  repositoryI.save(item5);

  Item item6= new Item("camionista");
  item6.addIndizio(0,"dieci ore al giorno");
  item6.addIndizio(1,"seduto in cabina");
  item6.addIndizio(2,"con un braccio abbronzato");
  item6.addIndizio(3,"per trasportare il cemento da ROma  Milano");
  repositoryI.save(item6);*/

  repositoryI.findAll().forEach(System.out::println);

 }
}
