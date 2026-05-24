package com.pinank.goldpredictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {
    @JsonProperty("regularMarketPrice")
    private double regularMarketPrice;

    public double getRegularMarketPrice() {
        return regularMarketPrice;
    }

    public void setRegularMarketPrice(double regularMarketPrice) {
        this.regularMarketPrice = regularMarketPrice;
    }
}
