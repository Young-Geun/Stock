package com.example.stock.service.stock;

import com.example.stock.domain.stock.Stock;
import com.example.stock.domain.stock.StockReq;
import com.example.stock.domain.stock.StockRes;
import com.example.stock.repository.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StockService {

    private final StockRepository stockRepository;

    public List<StockRes> findStocks() {
        return stockRepository.findAll().stream()
                .map(StockRes::new)
                .collect(Collectors.toList());
    }

    public StockRes findStock(Long stockId) {
        return new StockRes(stockRepository.findById(stockId).orElseGet(Stock::new));
    }

    @Transactional
    public StockRes save(StockReq stockReq) {
        return new StockRes(stockRepository.save(stockReq.toEntity()));
    }

    @Transactional
    public void remove(Long stockId) {
        stockRepository.deleteById(stockId);
    }

}
