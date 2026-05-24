package com.pinank.goldpredictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Result {
    @JsonProperty("meta")
    private Meta meta;

    @JsonProperty("timestamp")
    private List<Long> timestamp;

    @JsonProperty("indicators")
    private Indicators indicators;

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<Long> getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(List<Long> timestamp) {
        this.timestamp = timestamp;
    }

    public Indicators getIndicators() {
        return indicators;
    }

    public void setIndicators(Indicators indicators) {
        this.indicators = indicators;
    }
}
