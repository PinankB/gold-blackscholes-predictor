package com.pinank.goldpredictor.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Chart {
    @JsonProperty("result")
    private List<Result> result;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }
}