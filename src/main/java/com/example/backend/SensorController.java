package com.example.backend;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SensorController {
    // 로그를 찍기 위한 설정
    private static final Logger logger = LoggerFactory.getLogger(SensorController.class);
    private final InfluxService influxService;

    @GetMapping("/log-test")
    public String logTest() {
        logger.info("로그 테스트 API가 호출되었습니다! (8000번 포트)");
        return "Log test success!";
    }

    @GetMapping("/api/sensors/current")
    public SensorData getCurrentSensorData() {
        return influxService.getLatestTemperature();
    }
}