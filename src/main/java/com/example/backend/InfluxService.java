package com.example.backend;

import lombok.RequiredArgsConstructor;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class InfluxService {
    private final InfluxDB influxDB;

    public void write(SensorData data) {
        if (data.getTemperature() != null) {
            Point point = Point.measurement("temperature")
                    .time(System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                    .tag("sensor_id", data.getSensorId() != null ? data.getSensorId() : "unknown")
                    .addField("value", data.getTemperature())
                    .build();
            influxDB.write(point);
        }
    }

    public SensorData getLatestTemperature() {
        String database = "iot_data";
        // Query from 'temperature' measurement as per spec
        String command = "SELECT last(\"value\"), \"sensor_id\" FROM \"temperature\"";

        QueryResult result = influxDB.query(new Query(command, database));

        if (result.getResults() != null && !result.getResults().isEmpty() && result.getResults().get(0).getSeries() != null) {
            // Series 0: [time, last, sensor_id]
            // values.get(0): [time_str, temp_value, sensor_id_str]
            List<Object> values = result.getResults().get(0).getSeries().get(0).getValues().get(0);
            
            // value is at index 1 (last(value))
            Double tempVal = values.get(1) != null ? ((Number) values.get(1)).doubleValue() : 0.0;
            String sensorId = values.get(2) != null ? values.get(2).toString() : "unknown";
            String timeStr = values.get(0).toString();

            return SensorData.builder()
                    .time(Instant.parse(timeStr))
                    .temperature(tempVal)
                    .humidity(60.0) // Default/Mock for now
                    .soilMoisture(45.0) // Default/Mock
                    .co2(450) // Default/Mock
                    .lightIntensity(800) // Default/Mock
                    .sensorId(sensorId)
                    .build();
        }
        // Return default if no data
        return SensorData.builder()
                .temperature(0.0)
                .humidity(0.0)
                .soilMoisture(0.0)
                .co2(0)
                .lightIntensity(0)
                .build();
    }
}