package com.prd.ConnectedTeam.entityRepository;


import com.prd.ConnectedTeam.entity.Account;
import com.prd.ConnectedTeam.entity.Partita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface PartitaRepository extends JpaRepository<Partita, Long> {

    Partita findOneById(Long id);

    @Query("SELECT p FROM Partita p JOIN p.array pa WHERE pa.account = ?1 ORDER BY p.gioco, p.timestamp DESC")
    //@Transactional(propagation= Propagation.REQUIRED, readOnly=true, noRollbackFor=Exception.class)
    List<Partita> cercaPartite(Account a);

    @Query("SELECT p FROM Partita p JOIN p.array pa WHERE pa.account = ?1 AND p.timestamp= (SELECT max(p1.timestamp) FROM Partita p1 JOIN p1.array pa1 WHERE pa1.account = ?1)")
    Partita lastPartita(Account a);

    @Query("SELECT p FROM Partita p JOIN p.array pa WHERE pa.account = ?1 AND p.gioco=?2 ORDER BY p.timestamp ASC")
    List<Partita> cercaPerAccountEGioco(Account a, String gioco);

     @Query("SELECT p FROM Partita p JOIN p.array pa WHERE pa.account = ?1 ")
    List<Partita> cercaByAccount(Account account);

    @Query("SELECT p FROM Partita p JOIN p.array pa WHERE pa.account = ?1 AND p.gioco=?2")
    List<Partita> cercaByGioco(Account account, String gioco);

    @Transactional
    @Modifying
    @Query(value = " DELETE FROM PARTITA_ARRAY PA WHERE PA.ACCOUNT_ID=?1", nativeQuery = true)
    void deleteAccountsPartite(long id);

}
