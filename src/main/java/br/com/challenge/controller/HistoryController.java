package br.com.challenge.controller;

import br.com.challenge.model.Creature;
import br.com.challenge.model.History;
import br.com.challenge.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HistoryController {

    @Autowired
    private HistoryService historyService;
    @GetMapping("/history")
    public List<History> getHistorysList() {
        return this.historyService.getHistorysList();
    }

    @GetMapping("/history/{id}")
    public History getHistoryId(@PathVariable Long id) {
        return this.historyService.getHistoryId(id);
    }

    @GetMapping("/history/battle/{id}")
    public List<History> getCreaturesList(@PathVariable Long id) {
        return this.historyService.findHistorysByBattle(id);
    }

    @DeleteMapping("/history/{id}")
    public String deleteHistory(@PathVariable Long id) {
        return this.historyService.deleteHistory(id);
    }
}
