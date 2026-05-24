package com.pinank.goldpredictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Quote {
    @JsonProperty("close")
    private List<Double> close;

    public List<Double> getClose() {
        return close;
    }

    public void setClose(List<Double> close) {
        this.close = close;
    }
}