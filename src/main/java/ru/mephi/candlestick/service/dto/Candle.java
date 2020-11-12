package ru.mephi.candlestick.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Candle {
    private Float leftBorder;
    private Float rightBorder;
    private Float average;
    private Long highLine;
    private Long lowLine;
}
