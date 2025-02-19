package com.fin.controller;

import com.fin.dto.GenerateTestRequest;
import com.fin.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/generate")
@RequiredArgsConstructor
public class GeneratorController {

    private final GeneratorService generatorService;

    @PostMapping
    public ResponseEntity<String> generateTests(@RequestBody GenerateTestRequest request) {
        log.info("Received generation request: {}", request);
        boolean success = generatorService.generateTests(request);
        if(success) {
            log.info("Test generation succeeded for taskId: {}", request.getTaskId());
            return ResponseEntity.ok("OK");
        } else {
            log.error("Test generation failed for taskId: {}", request.getTaskId());
            return ResponseEntity.status(500).body("BAD");
        }
    }
}
