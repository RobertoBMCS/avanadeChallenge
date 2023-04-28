package br.com.challenge.repository;

import br.com.challenge.model.History;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Long> {
    @Query(nativeQuery = true, value = "select * from history h where battle_id = :battleId")
    List<History> findHistorysByBattle(@Param("battleId") Long battleId);
}
