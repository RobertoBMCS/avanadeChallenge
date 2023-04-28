package br.com.challenge.controller;

import br.com.challenge.model.Player;
import br.com.challenge.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerController {
    @Autowired
    private PlayerService playerService;

    @PostMapping("/player")
    public String createPlayer(@RequestBody Player newPlayer) {
        return this.playerService.createPlayer(newPlayer);
    }

    @GetMapping("/player")
    public List<Player> getPlayersList() {
        return this.playerService.getPlayersList();
    }

    @GetMapping("/player/{id}")
    public Player getPlayersId(@PathVariable Long id) {
        return this.playerService.getPlayersId(id);
    }

    @PutMapping("/player/{id}")
    public String updatePlayer(@RequestBody Player player, @PathVariable Long id) {
        return this.playerService.updatePlayer(player, id);
    }

    @DeleteMapping("/player/{id}")
    public String deletePlayer(@PathVariable Long id) {
        return this.playerService.deletePlayer(id);
    }
}
