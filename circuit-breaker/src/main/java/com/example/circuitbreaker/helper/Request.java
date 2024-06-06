package com.example.circuitbreaker.helper;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class Request {
    public static <T> ResponseEntity<T> retryRequest(RestTemplate restTemplate, String url, Class<T> responseType) {
        int retryCount = 0;// Счетчик повторных попыток
        while (retryCount < 3) { // Цикл повторных попыток выполнения запроса (максимум 3 попытки)
            try {
                ResponseEntity<T> response = restTemplate.getForEntity(url, responseType); // Выполнение HTTP-запроса
                if (response.getStatusCode().is2xxSuccessful()) {
                    return response; // Возврат успешного ответа
                }
            }
            catch (HttpClientErrorException e) {
                if(e.getStatusCode().is5xxServerError())
                    retryCount++; // Увеличение счетчика повторных попыток на 1
                else
                    return new ResponseEntity<>(e.getStatusCode());
            }
        }
        throw new RuntimeException("Failed to make a successful request after multiple retries."); // Генерация исключения в случае неудачи повторных попыток выполнения запроса
    }
}
