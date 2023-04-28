package br.com.challenge.service;

import br.com.challenge.model.Battle;
import br.com.challenge.model.History;
import br.com.challenge.repository.HistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    public void saveHistoryBattle(String action, Battle battle) {
        History history = new History();
        history.setBattleId(battle);
        history.setAction(action);
        this.historyRepository.save(history);
    }

    public List<History> getHistorysList() {
        Iterable<History> historyIterable = this.historyRepository.findAll();
        return Streamable.of(historyIterable).toList();
    }

    public History getHistoryId(Long id) {
        Optional<History> historyOptional = this.historyRepository.findById(id);
        History history = historyOptional.get();
        return history;
    }

    public String deleteHistory(Long id) {
        String response = "";
        try {
            response = "could not find and delete the mentioned history.";

            Optional<History> oldHistory = this.historyRepository.findById(id);

            History historyUpdated = oldHistory.get();
            if (historyUpdated.getId() != null) {
                this.historyRepository.deleteById(id);
                response = "history successfully deleted!";
            }

            return response;

        } catch (Exception e) {
            response = "an error occurred while trying to delete a history: " + e.getMessage();
            return response;
        }
    }

    @Transactional
    public List<History> findHistorysByBattle(Long id) {
        return this.historyRepository.findHistorysByBattle(id);
    }
}
