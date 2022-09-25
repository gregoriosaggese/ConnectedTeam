package com.prd.ConnectedTeam.maty.gameMenagement.backend.db;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Lazy
public interface ItemRepositoryMaty extends JpaRepository<ItemMaty, Integer> {

    @Query("SELECT count(*) FROM ItemMaty i")
    int numeroRighe();

    ItemMaty findOneById(int id);
}