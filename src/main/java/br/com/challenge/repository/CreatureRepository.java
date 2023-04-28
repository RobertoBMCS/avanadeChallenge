package br.com.challenge.repository;

import br.com.challenge.model.Creature;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreatureRepository extends CrudRepository<Creature, Long> {

    @Query(nativeQuery = true, value = "select * from creature c where c.type_creature = 'Monster' and c.id = :enemyId")
    List<Creature> findEnemyId(@Param("enemyId") Long EnemyId);

    @Query(nativeQuery = true, value = "select * from creature c where c.type_creature = 'Monster'")
    List<Creature> findAllEnemys();
}
