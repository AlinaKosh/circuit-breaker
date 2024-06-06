package com.example.circuitbreaker.controllers;

import com.example.circuitbreaker.helper.Request;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/")
class CheckController {
    private final AtomicInteger failureCounter = new AtomicInteger(0);
    private LocalDateTime failureTime = null;
    private final Duration timeForRecovery = Duration.ofSeconds(5);
    private final String randomStatusEndpoint = "http://localhost:8081/randfail";
    private final String successEndpoint = "http://localhost:8081/data";
    private final String faultyEndpoint = "http://localhost:8081/fault";

    @GetMapping("/status")
    public ResponseEntity<?> makeRequest() {

        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Object> response = Request.retryRequest(restTemplate, randomStatusEndpoint, Object.class); // Выполнение запроса с механизмом повтора в случае ошибки
            if (response.getBody() != null) {
                return ResponseEntity.ok(response.getBody());
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getBody());
            }
        } catch (HttpClientErrorException e) {
            int counter = failureCounter.incrementAndGet(); // Увеличение счетчика ошибок на 1
            if (counter >= 5) {
                failureTime = LocalDateTime.now(); // Запись текущего времени как времени ошибки
                return ResponseEntity.status(503).body(new StatusResponse("Not available")); // Возврат ошибки "Сервис недоступен"
            }
            return ResponseEntity.status(501).body(new StatusResponse("Failed to make the request")); // Возврат ошибки "Не удалось выполнить запрос"
        } catch (Exception e) {
            return ResponseEntity.status(501).body(new StatusResponse("Failed to make the request")); // Возврат ошибки "Не удалось выполнить запрос"
        }
    }

    private static class StatusResponse {
        private final String status;

        public StatusResponse(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}
