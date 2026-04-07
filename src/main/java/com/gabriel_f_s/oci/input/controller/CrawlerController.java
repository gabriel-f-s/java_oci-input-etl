package com.gabriel_f_s.oci.input.controller;

import com.gabriel_f_s.oci.input.dto.Response;
import com.gabriel_f_s.oci.input.service.ValidationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CrawlerController {

    private ValidationService service;

    CrawlerController(ValidationService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Response> verify() {
        Response response = service.check();
        return ResponseEntity.ok(response);
    }
}
