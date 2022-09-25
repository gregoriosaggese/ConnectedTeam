package com.prd.ConnectedTeam.maty.gameMenagement.backend;

import com.prd.ConnectedTeam.maty.gameMenagement.backend.db.ItemMaty;
import com.prd.ConnectedTeam.maty.gameMenagement.backend.db.ItemRepositoryMaty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MatyPopulator {

 @Autowired
 private ItemRepositoryMaty itemRepositoryMaty;

 @EventListener
 public void onApplicationEvent(ContextRefreshedEvent event) {

  ItemMaty item1= new ItemMaty("100","somma");
  item1.addIndizio(0,"Contiene 10 volte il numero 10");
  item1.addIndizio(1,"Contiene 4 volte il numero 25");
  item1.addIndizio(2,"Contiene 5 volte il numero 20");
  item1.addIndizio(3,"Contiene 2 volte il numero 50");
  itemRepositoryMaty.save(item1);

  ItemMaty item2= new ItemMaty("170","sottrazione");
  item2.addIndizio(0,"340 è uguale a 170 + 50 + 50 +50 + 20");
  item2.addIndizio(1,"Contiene 2 volte il numero 170");
  item2.addIndizio(2,"Parti da capo! Prova a sotrarre 70");
  item2.addIndizio(3,"Parti da capo! Sottrai 70 e poi sottrai 100");
  itemRepositoryMaty.save(item2);

  itemRepositoryMaty.findAll().forEach(System.out::println);

 }
}
