package com.example.backend;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyInfluxConfig {

    @Value("${influxdb.url}")
    private String influxUrl;

    @Value("${influxdb.user}")
    private String influxUser;

    @Value("${influxdb.password}")
    private String influxPassword;

    @Value("${influxdb.database}")
    private String influxDatabase;

    @Bean
    public InfluxDB customInfluxDB() {
        InfluxDB influxDB = InfluxDBFactory.connect(influxUrl, influxUser, influxPassword);
        influxDB.setDatabase(influxDatabase);
        return influxDB;
    }
}