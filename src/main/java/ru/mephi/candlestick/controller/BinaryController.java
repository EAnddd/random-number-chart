package ru.mephi.candlestick.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.mephi.candlestick.service.Extractor;
import ru.mephi.candlestick.service.dto.Candle;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class BinaryController {

    private final Extractor extractor;

    @GetMapping("/candle/{file}")
    public List<Candle> getCandles(@PathVariable String file, @RequestParam("size") Integer size) {
        return extractor.getCandlesFromBinary(file, size, 8);
    }
}
