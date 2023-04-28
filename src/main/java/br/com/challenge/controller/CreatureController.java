package br.com.challenge.controller;

import br.com.challenge.model.Creature;
import br.com.challenge.service.CreatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class CreatureController {

    @Autowired
    private CreatureService creatureService;

    @PostMapping("/creature")
    public String createCreature(@RequestBody Creature newCreature) {
        return this.creatureService.createCreature(newCreature);
    }

    @GetMapping("/creature")
    public List<Creature> getCreaturesList() {
        return this.creatureService.getCreaturesList();
    }

    @GetMapping("/creature/{id}")
    public Creature getCreaturesId(@PathVariable Long id) {
        return this.creatureService.getCreaturesId(id);
    }

    @PutMapping("/creature/{id}")
    public String updateCreature(@RequestBody Creature creature, @PathVariable Long id) {
        return this.creatureService.updateCreature(creature, id);
    }

    @DeleteMapping("/creature/{id}")
    public String deleteCreature(@PathVariable Long id) {
        return this.creatureService.deleteCreature(id);
    }
}