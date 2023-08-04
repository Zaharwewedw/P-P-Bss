package com.bank.history.controller;

import com.bank.history.dto.HistoryDto;
import com.bank.history.exeption.HistoryNotFoundException;
import com.bank.history.servise.HistoryServise;
import com.bank.history.servise.HistoryServiseImp;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping()
public class HistoryController {
    private final HistoryServise historyServise;

    @GetMapping
    public ResponseEntity<?> getAllHistoryList() {
        List<HistoryDto> historyDtoList = historyServise.getHistoryList();
        return ResponseEntity.ok(historyDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHistoryById(@PathVariable Long id) throws HistoryNotFoundException {
        return ResponseEntity.ok(historyServise.getHistoryById(id));
    }

    @PostMapping
    public ResponseEntity<?> createHistory(@RequestBody HistoryDto historyDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(historyServise.create(historyDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateHistory(@PathVariable Long id, @RequestBody HistoryDto historyDto) {
        return ResponseEntity.ok(historyServise.update(id, historyDto));
    }

    @DeleteMapping("/{id}")
    public void deleteHistory(@PathVariable Long id) {
        historyServise.delete(id);

    }
}
