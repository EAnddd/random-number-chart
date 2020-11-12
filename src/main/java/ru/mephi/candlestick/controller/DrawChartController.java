package ru.mephi.candlestick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DrawChartController {

    @GetMapping("/chart")
    public String getChart() {
        return "chart";
    }
}
