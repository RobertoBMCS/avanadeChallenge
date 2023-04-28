package br.com.challenge.service;

import br.com.challenge.model.Battle;
import br.com.challenge.model.Creature;
import br.com.challenge.model.Player;
import br.com.challenge.repository.BattleRepository;
import br.com.challenge.repository.CreatureRepository;
import br.com.challenge.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class BattleService {
    @Autowired
    private BattleRepository battleRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CreatureRepository creatureRepository;

    @Autowired
    private CreatureService creatureService;

    @Autowired
    private HistoryService historyService;

    public String whoFight(Battle newBattle) {
        Optional<Player> playerOptional = this.playerRepository.findById(newBattle.getPlayerId().getId());
        Player playerPlaying = playerOptional.get();

        Optional<Creature> enemyOptional = this.creatureRepository.findById(newBattle.getEnemyId().getId());
        Creature enemyPlaying = enemyOptional.get();

        Optional<Creature> playerCreatureOptional = this.creatureRepository.findById(newBattle.getPlayerId().getId());
        Creature playerCreature = playerCreatureOptional.get();

        newBattle.setPlayerLifePoints(playerCreature.getLifePoints());
        newBattle.setEnemyLifePoints(enemyPlaying.getLifePoints());

        newBattle.setPlayerIniciative(randomNumber(20));
        newBattle.setEnemyIniciative(randomNumber(20));

        while (newBattle.getPlayerIniciative() == newBattle.getEnemyIniciative()) {
            newBattle.setPlayerIniciative(randomNumber(20));
            newBattle.setEnemyIniciative(randomNumber(20));
        }

        Battle battleSaved = this.battleRepository.save(newBattle);

        Long battleId  = battleSaved.getId();

        return "Your battle is about to begin, now that we have decided who will fight the battle has been created. The ID of your battle is: " + battleId;
    }

    public List<Battle> getBattlesList() {
        Iterable<Battle> battleIterable = this.battleRepository.findAll();
        return Streamable.of(battleIterable).toList();
    }

    public Battle getBattlesId(Long id) {
        Optional<Battle> battleOptional = this.battleRepository.findById(id);
        Battle battle = battleOptional.get();
        return battle;
    }

    public String playNextActionBattle(Long battleId) {
        Battle battleNow = this.getBattlesId(battleId);
        int playerLifePointsNow = battleNow.getPlayerLifePoints();
        int enemyLifePointsNow = battleNow.getEnemyLifePoints();
        String statusBattle = "";
        String action = "";

        Creature playerCreature = this.creatureService.getCreaturesId(battleNow.getPlayerId().getCreatureId().getId());
        Creature enemyCreature = this.creatureService.getCreaturesId(battleNow.getEnemyId().getId());
        Player playerPlaying = this.playerService.getPlayersId(battleNow.getPlayerId().getId());

        //checks the initiative to see who will start attacking
        if (battleNow.getPlayerIniciative() > battleNow.getEnemyIniciative()) {

            //checks to see if you can hit the attack
            int hitAttackPlayer = this.randomNumber(12) + playerCreature.getStrengthPoints() + playerCreature.getDexterityPoints();
            int defenseAttackEnemy = this.randomNumber(12) + enemyCreature.getDefensePoints() + enemyCreature.getDexterityPoints();
            if (hitAttackPlayer > defenseAttackEnemy) {

                //save the History
                action = "The Player " + playerPlaying.getName() + " Hit Attack on The Enemy " + enemyCreature.getClassType();
                statusBattle = action;
                this.historyService.saveHistoryBattle(action, battleNow);

                int damageGiven = playerCreature.getDiceAmount() * randomNumber(playerCreature.getDiceFace());

                //save the History
                action = "The Player " + playerPlaying.getName() + " gave " + damageGiven + " Damage on The Enemy " + enemyCreature.getClassType();
                statusBattle += "\n" + action;
                this.historyService.saveHistoryBattle(action, battleNow);

                if (enemyLifePointsNow < damageGiven) {
                    //save the History
                    action = "The Player " + playerPlaying.getName() + " is the winner!";
                    statusBattle += "\n" + action;
                    this.historyService.saveHistoryBattle(action, battleNow);
                    return statusBattle += "\n" + "YOU WIN! Check on the History";
                }

                //removes the life you lost
                battleNow.setEnemyLifePoints(battleNow.getEnemyLifePoints() - damageGiven);

                //save the History
                action = "The Enemy " + enemyCreature.getClassType() + " Lost " + damageGiven + " of his life.";
                statusBattle += "\n" + action;
                this.historyService.saveHistoryBattle(action, battleNow);
            } else {
                //save the History
                action = "The Player " + playerPlaying.getName() + " Miss Attack on The Enemy " + enemyCreature.getClassType();
                statusBattle += "\n" + action;
                this.historyService.saveHistoryBattle(action, battleNow);
            }

            //checks to see if you can hit the attack
            int hitAttackEnemy = this.randomNumber(12) + enemyCreature.getStrengthPoints() + enemyCreature.getDexterityPoints();
            int defenseAttackPlayer = this.randomNumber(12) + playerCreature.getDefensePoints() + playerCreature.getDexterityPoints();
            if (hitAttackEnemy > defenseAttackPlayer) {

                //save the History
                action = "The Enemy " + enemyCreature.getClassType() + " Hit Attack on The Player " + playerPlaying.getName();
                statusBattle += action;
                this.historyService.saveHistoryBattle(action, battleNow);

                int damageGiven = enemyCreature.getDiceAmount() * randomNumber(enemyCreature.getDiceFace());

                //save the History
                action = "The Enemy " + enemyCreature.getClassType() + " gave " + damageGiven + " Damage on The Player " + playerPlaying.getName();
                statusBattle += "\n" + action;
                this.historyService.saveHistoryBattle(action, battleNow);

                if (playerLifePointsNow < damageGiven) {
                    //save the History
                    action = "The Enemy " + enemyCreature.getClassType() + " is the winner!";
                    statusBattle += "\n" + action;
                    this.historyService.saveHistoryBattle(action, battleNow);
                    return statusBattle += "\n" + "You Lose! Check on the History";
                }

                //removes the life you lost
                battleNow.setPlayerLifePoints(battleNow.getPlayerLifePoints() - damageGiven);

                //save the History
                action = "The Player " + playerPlaying.getName() + " Lost " + damageGiven + " of his life.";
                statusBattle += "\n" + action;
                this.historyService.saveHistoryBattle(action, battleNow);
            } else {
                //save the History
                action = "The Enemy " + enemyCreature.getClassType() + " Miss Attack on The Player " + playerPlaying.getName();
                statusBattle += "\n" + action;
                this.historyService.saveHistoryBattle(action, battleNow);
            }

        } else {

            //checks to see if you can hit the attack
            int hitAttackEnemy = this.randomNumber(12) + enemyCreature.getStrengthPoints() + enemyCreature.getDexterityPoints();
            int defenseAttackPlayer = this.randomNumber(12) + playerCreature.getDefensePoints() + playerCreature.getDexterityPoints();
            if (hitAttackEnemy > defenseAttackPlayer) {

                //save the History
                action = "The Enemy " + enemyCreature.getClassType() + " Hit Attack on The Player " + playerPlaying.getName();
                statusBattle = action;
                this.historyService.saveHistoryBattle(action, battleNow);

                int damageGiven = enemyCreature.getDiceAmount() * randomNumber(enemyCreature.getDiceFace());

                //save the History
                action = "The Enemy " + enemyCreature.getClassType() + " gave " + damageGiven + " Damage on The Player " + playerPlaying.getName();
                statusBattle += "\n" + action;
                this.historyService.saveHistoryBattle(action, battleNow);

                if (playerLifePointsNow < damageGiven) {
                    //save the History
                    action = "The Enemy " + enemyCreature.getClassType() + " is the winner!";
                    statusBattle += "\n" + action;
                    this.historyService.saveHistoryBattle(action, battleNow);
                    return statusBattle += "\n" + "You Lose! Check on the History";
                }

                //removes the life you lost
                battleNow.setPlayerLifePoints(battleNow.getPlayerLifePoints() - damageGiven);

                //save the History
                action = "The Player " + playerPlaying.getName() + " Lost " + damageGiven + " of his life.";
                statusBattle += "\n" + action;
                this.historyService.saveHistoryBattle(action, battleNow);
            } else {
                //save the History
                action = "The Enemy " + enemyCreature.getClassType() + " Miss Attack on The Player " + playerPlaying.getName();
                statusBattle += "\n" + action;
                this.historyService.saveHistoryBattle(action, battleNow);
            }

            //checks to see if you can hit the attack
            int hitAttackPlayer = this.randomNumber(12) + playerCreature.getStrengthPoints() + playerCreature.getDexterityPoints();
            int defenseAttackEnemy = this.randomNumber(12) + enemyCreature.getDefensePoints() + enemyCreature.getDexterityPoints();
            if (hitAttackPlayer > defenseAttackEnemy) {

                //save the History
                action = "The Player " + playerPlaying.getName() + " Hit Attack on The Enemy " + enemyCreature.getClassType();
                statusBattle += action;
                this.historyService.saveHistoryBattle(action, battleNow);

                int damageGiven = playerCreature.getDiceAmount() * randomNumber(playerCreature.getDiceFace());

                //save the History
                action = "The Player " + playerPlaying.getName() + " gave " + damageGiven + " Damage on The Enemy " + enemyCreature.getClassType();
                statusBattle += "\n" + action;
                this.historyService.saveHistoryBattle(action, battleNow);

                if (enemyLifePointsNow < damageGiven) {
                    //save the History
                    action = "The Player " + playerPlaying.getName() + " is the winner!";
                    statusBattle += "\n" + action;
                    this.historyService.saveHistoryBattle(action, battleNow);
                    return statusBattle += "\n" + "YOU WIN! Check on the History";
                }

                //removes the life you lost
                battleNow.setEnemyLifePoints(battleNow.getEnemyLifePoints() - damageGiven);

                //save the History
                action = "The Enemy " + enemyCreature.getClassType() + " Lost " + damageGiven + " of his life.";
                statusBattle += "\n" + action;
                this.historyService.saveHistoryBattle(action, battleNow);
            } else {
                //save the History
                action = "The Player " + playerPlaying.getName() + " Miss Attack on The Enemy " + enemyCreature.getClassType();
                statusBattle += "\n" + action;
                this.historyService.saveHistoryBattle(action, battleNow);
            }
        }

        battleNow.setTurn(battleNow.getTurn() + 1);

        this.battleRepository.save(battleNow);

        statusBattle += "\n" + "History Turn: " + battleNow.getTurn();

        return statusBattle;
    }

    public String deleteBattle(Long id) {
        String response = "";
        try {
            response = "could not find and delete the mentioned battle.";

            Optional<Battle> oldBattle = this.battleRepository.findById(id);

            Battle battleUpdated = oldBattle.get();
            if (battleUpdated.getId() != null) {
                this.battleRepository.deleteById(id);
                response = "battle successfully deleted!";
            }

            return response;

        } catch (Exception e) {
            response = "an error occurred while trying to delete a battle: " + e.getMessage();
            return response;
        }
    }

    public int randomNumber(int maxNum) {
        Random numberRandom = new Random();
        return numberRandom.nextInt(maxNum);
    }
}
