package com.bank.history.servise;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.History;
import com.bank.history.exeption.HistoryNotFoundException;

import java.util.List;

public interface HistoryServise {
    List<HistoryDto> getHistoryList();

    HistoryDto getHistoryById(Long id) throws HistoryNotFoundException;

    public void delete(Long id);

    HistoryDto create(HistoryDto history);

    HistoryDto update(Long id, HistoryDto history);
}
