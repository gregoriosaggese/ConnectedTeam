package com.prd.ConnectedTeam.guess.gamesMenagemet.backend.db;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Lazy
public interface ItemRepository extends JpaRepository<Item, Integer> {

    @Query("SELECT count(*) FROM Item i")
    int numeroRighe();

    Item findOneById(int id);
}
