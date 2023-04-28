package br.com.challenge.controller;

import br.com.challenge.model.Battle;
import br.com.challenge.service.BattleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BattleController {

    @Autowired
    private BattleService battleService;

    @PostMapping("/battle")
    public String whoFight(@RequestBody Battle newBattle) {
        return this.battleService.whoFight(newBattle);
    }

    @GetMapping("/battle")
    public List<Battle> getBattlesList() {
        return this.battleService.getBattlesList();
    }

    @GetMapping("/battle/{id}")
    public Battle getBattlesId(@PathVariable Long id) {
        return this.battleService.getBattlesId(id);
    }

    @PutMapping("/battle/{id}")
    public String updateBattle(@RequestBody Long battleId) {
        return this.battleService.playNextActionBattle(battleId);
    }

    @DeleteMapping("/battle/{id}")
    public String deleteBattle(@PathVariable Long id) {
        return this.battleService.deleteBattle(id);
    }
}
