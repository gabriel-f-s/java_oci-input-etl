package com.gabriel_f_s.oci.input.crawler.controller;

import com.gabriel_f_s.oci.input.crawler.dto.Response;
import com.gabriel_f_s.oci.input.crawler.service.VerifyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class CrawlerController {

    private VerifyService service;

    CrawlerController(VerifyService service) {
        this.service = service;
    }

    @GetMapping("/")
    public ResponseEntity<Response> verify() {
        Response response = service.verify();
        return ResponseEntity.ok(response);
    }
}
