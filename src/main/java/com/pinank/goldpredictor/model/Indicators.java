package com.pinank.goldpredictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Indicators {
    @JsonProperty("quote")
    private List<Quote> quote;

    public List<Quote> getQuote() {
        return quote;
    }

    public void setQuote(List<Quote> quote) {
        this.quote = quote;
    }
}