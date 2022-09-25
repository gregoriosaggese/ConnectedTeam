package com.prd.ConnectedTeam.nuovoGioco.gameManagement.database;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;

@Lazy
public interface ItemRepositoryNuovoGioco extends JpaRepository<ItemNuovoGioco, Integer> {
    ItemNuovoGioco findOneById(int id);
}