package com.example.backend;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorData {
    @JsonProperty("sensor_id")
    @JsonAlias("sensorId")
    private String sensorId;

    @JsonProperty("temperature")
    @JsonAlias("temp")
    private Double temperature;

    // Frontend expects 'humidity'
    private Double humidity;

    @JsonProperty("soilMoisture")
    private Double soilMoisture;

    // Frontend expects 'co2'
    private Integer co2;

    @JsonProperty("lightIntensity")
    @JsonAlias("light")
    private Integer lightIntensity;

    private Instant time;
}