package com.example.backend;

import lombok.RequiredArgsConstructor;
import org.influxdb.InfluxDB;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InfluxService {
    private final InfluxDB influxDB;

    public SensorData getLatestTemperature() {
        String database = "iot_data";
        String command = "SELECT last(temp), last(humidity), last(soil_moisture), last(co2), last(light), sensor_id FROM farm_data";

        QueryResult result = influxDB.query(new Query(command, database));

        if (!result.getResults().isEmpty() && result.getResults().get(0).getSeries() != null) {
            List<Object> values = result.getResults().get(0).getSeries().get(0).getValues().get(0);
            return SensorData.builder()
                    .time(Instant.parse(values.get(0).toString()))
                    .temp((Double) values.get(1))
                    .humidity((Double) values.get(2))
                    .soilMoisture((Double) values.get(3))
                    .co2(((Double) values.get(4)).intValue())
                    .light(((Double) values.get(5)).intValue())
                    .sensorId(values.get(6).toString())
                    .build();
        }
        return null;
    }
}