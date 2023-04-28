package br.com.challenge.service;

import br.com.challenge.model.Creature;
import br.com.challenge.repository.CreatureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CreatureService {
    @Autowired
    private CreatureRepository creatureRepository;

    public String createCreature(Creature newCreature) {
        String response = "";
        try {
            this.creatureRepository.save(newCreature);
            response = "creature successfully saved!";

            return response;

        } catch (Exception e) {
            response = "an error occurred while trying to save a creature: " + e.getMessage();
            return response;
        }
    }

    public List<Creature> getCreaturesList() {
        Iterable<Creature> creatureIterable = this.creatureRepository.findAll();
        return Streamable.of(creatureIterable).toList();
    }

    public Creature getCreaturesId(Long id) {
        Optional<Creature> creatureOptional = this.creatureRepository.findById(id);
        Creature creature = creatureOptional.get();
        return creature;
    }

    public String updateCreature(Creature creature, Long id) {
        String response = "";

        try {
            response = "could not find and update the mentioned creature.";

            Optional<Creature> oldCreature = this.creatureRepository.findById(id);

            Creature creatureUpdated = oldCreature.get();

            if (creatureUpdated.getId() != null) {
                creatureUpdated.setTypeCreature(creature.getTypeCreature());
                creatureUpdated.setClassType(creature.getClassType());
                creatureUpdated.setName(creature.getName());
                creatureUpdated.setLifePoints(creature.getLifePoints());
                creatureUpdated.setStrengthPoints(creature.getStrengthPoints());
                creatureUpdated.setDefensePoints(creature.getDefensePoints());
                creatureUpdated.setDexterityPoints(creature.getDexterityPoints());
                creatureUpdated.setDiceAmount(creature.getDiceAmount());
                creatureUpdated.setDiceFace(creature.getDiceFace());

                this.creatureRepository.save(creatureUpdated);

                response = "creature successfully updated!";
            }

            return response;

        } catch (Exception e) {
            response = "an error occurred while trying to update a creature: " + e.getMessage();
            return response;
        }
    }

    public String deleteCreature(Long id) {
        String response = "";
        try {
            response = "could not find and delete the mentioned creature.";

            Optional<Creature> oldCreature = this.creatureRepository.findById(id);

            Creature creatureUpdated = oldCreature.get();
            if (creatureUpdated.getId() != null) {
                this.creatureRepository.deleteById(id);
                response = "creature successfully deleted!";
            }

            return response;

        } catch (Exception e) {
            response = "an error occurred while trying to delete a creature: " + e.getMessage();
            return response;
        }
    }
}
