package com.gabriel_f_s.oci.input.crawler.service;

import com.gabriel_f_s.oci.input.crawler.dto.Response;
import com.gabriel_f_s.oci.input.crawler.exception.FileException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class VerifyService {

    private static final Logger logger = LoggerFactory.getLogger(VerifyService.class);

    private final String URI = "https://dados-abertos-rf-cnpj.casadosdados.com.br/arquivos/";

    private final ConnectionService connectionService;
    private final ProcessService processService;
    private final IOService ioService;

    public Response verify() {
        if (ioService.isLastProcessedDateUpToDate()) {
            return new Response(
                    true,
                    "Up-to-date information.",
                    LocalDateTime.now()
            );
        } else {
            processService.start(URI);
            return new Response(
                    false,
                    "Outdated information, extraction process initiating.",
                    LocalDateTime.now()
            );
        }
    }
}
