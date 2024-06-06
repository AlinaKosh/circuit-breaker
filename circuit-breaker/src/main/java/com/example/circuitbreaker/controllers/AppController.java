package com.example.circuitbreaker.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Random;


@RestController
@RequestMapping("/")
public class AppController {

    /*
    private String[] data = {"cucumber", "chocolate"};
    private Random random = new Random();

    @GetMapping("/")
    public String helloWorld() {
        return "HEEEEEYYEYEYEYEY";
    }

    @GetMapping("/fault")
    public ResponseEntity<Object> faultyEndpoint() {
        int r = random.nextInt(2);
        if (r == 0) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return ResponseEntity.status(500).body("{\"msg\": \"I've failed.\"}");
    }

    @GetMapping("/randfail")
    public ResponseEntity<Object> failRandomlyEndpoint() {
        int r = random.nextInt(2);
        if (r == 0) {
            return ResponseEntity.ok(data);
        }
        return ResponseEntity.status(500).body("{\"msg\": \"I will fail (sometimes).\"}");
    }

     */
}
