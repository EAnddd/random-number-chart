package ru.mephi.candlestick.service;

import ru.mephi.candlestick.service.dto.Candle;

import java.util.List;

public interface Extractor {
    List<Candle> getCandlesFromBinary(String fileName, Integer size, Integer bitness);
}
