package br.com.challenge.service;

import br.com.challenge.model.Player;
import br.com.challenge.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public String createPlayer(Player newPlayer) {
        String response = "";
        try {
            this.playerRepository.save(newPlayer);
            response = "player successfully saved!";

            return response;

        } catch (Exception e) {
            response = "an error occurred while trying to save a player: " + e.getMessage();
            return response;
        }
    }

    public List<Player> getPlayersList() {
        Iterable<Player> playerIterable = this.playerRepository.findAll();
        return Streamable.of(playerIterable).toList();
    }

    public Player getPlayersId(Long id) {
        Optional<Player> playerOptional = this.playerRepository.findById(id);
        Player player = playerOptional.get();
        return player;
    }

    public String updatePlayer(Player player, Long id) {
        String response = "";

        try {
            response = "could not find and update the mentioned player.";

            Optional<Player> oldPlayer = this.playerRepository.findById(id);

            Player playerUpdated = oldPlayer.get();

            if (playerUpdated.getId() != null) {

                playerUpdated.setName(player.getName());
                playerUpdated.setCreatureId(player.getCreatureId());

                this.playerRepository.save(playerUpdated);

                response = "player successfully updated!";
            }

            return response;

        } catch (Exception e) {
            response = "an error occurred while trying to update a player: " + e.getMessage();
            return response;
        }
    }

    public String deletePlayer(Long id) {
        String response = "";
        try {
            response = "could not find and delete the mentioned player.";

            Optional<Player> oldPlayer = this.playerRepository.findById(id);

            Player playerUpdated = oldPlayer.get();
            if (playerUpdated.getId() != null) {
                this.playerRepository.deleteById(id);
                response = "player successfully deleted!";
            }

            return response;

        } catch (Exception e) {
            response = "an error occurred while trying to delete a player: " + e.getMessage();
            return response;
        }
    }
}
