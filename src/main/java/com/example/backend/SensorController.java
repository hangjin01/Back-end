package com.example.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SensorController {
    // 로그를 찍기 위한 설정
    private static final Logger logger = LoggerFactory.getLogger(SensorController.class);

    @GetMapping("/log-test")
    public String logTest() {
        logger.info("로그 테스트 API가 호출되었습니다! (8000번 포트)");
        return "Log test success!";
    }
}