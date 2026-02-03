package com.example.backend;

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
    private String sensorId;
    private Double temp;          // Float 대응
    private Double humidity;      // Float 대응
    private Double soilMoisture;  // Float 대응
    private Integer co2;          // Int 대응
    private Integer light;        // Int 대응
    private Instant time;
}