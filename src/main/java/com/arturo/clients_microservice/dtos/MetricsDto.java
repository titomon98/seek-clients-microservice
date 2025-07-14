package com.arturo.clients_microservice.dtos;

public class MetricsDto {
    private Double average;
    private Double deviation;

    public MetricsDto(Double average, Double deviation) {
        this.average = average;
        this.deviation = deviation;
    }

    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getDeviation() {
        return deviation;
    }

    public void setDeviation(Double deviation) {
        this.deviation = deviation;
    }
}
