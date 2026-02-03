package com.example.backend;

import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyInfluxConfig { // 클래스 이름을 MyInfluxConfig로 변경

    @Bean
    public InfluxDB customInfluxDB() { // 메서드 이름을 customInfluxDB로 변경
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
        influxDB.setDatabase("mydb");
        return influxDB;
    }
}