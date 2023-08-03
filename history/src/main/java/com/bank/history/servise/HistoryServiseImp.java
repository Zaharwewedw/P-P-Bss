package com.bank.history.servise;

import com.bank.history.dto.HistoryDto;
import com.bank.history.entity.History;
import com.bank.history.exeption.HistoryNotFoundException;
import com.bank.history.mapper.HistoryMapper;
import com.bank.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class HistoryServiseImp implements HistoryServise {
    private final HistoryRepository historyRepository;
    private final HistoryMapper historyMapper;

    @Override
    public List<HistoryDto> getHistoryList() {
        List<History> historyList = historyRepository.findAll();
        return historyMapper.toDtoList(historyList);
    }

    @Override
    public HistoryDto getHistoryById(Long id) throws HistoryNotFoundException {
        return historyMapper.toHistoryDto(historyRepository.findById(id).orElseThrow(HistoryNotFoundException::new));

    }

    @Override
    public void delete(Long id) {
        historyRepository.deleteById(id);

    }

    @Override
    public HistoryDto create(HistoryDto historyDto) {
        History history = historyRepository.save(historyMapper.toEntity(historyDto));
        return historyMapper.toHistoryDto(history);

    }

    @Override
    public HistoryDto update(Long id, HistoryDto historyDto) {
        History history = historyRepository.findById(id).orElseThrow(HistoryNotFoundException::new);
        History updateHistory = historyRepository.save(historyMapper.toEntity(historyDto));
        return historyMapper.toHistoryDto(updateHistory);


    }
}
