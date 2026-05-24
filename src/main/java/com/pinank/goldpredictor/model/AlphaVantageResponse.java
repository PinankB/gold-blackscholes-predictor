package com.pinank.goldpredictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlphaVantageResponse {
    @JsonProperty("chart")
    private Chart chart;

    public Chart getChart() {
        return chart;
    }

    public void setChart(Chart chart) {
        this.chart = chart;
    }
}