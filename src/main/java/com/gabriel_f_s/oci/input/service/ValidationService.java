package com.gabriel_f_s.oci.input.service;

import com.gabriel_f_s.oci.input.dto.Response;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class ValidationService {

    private final LoggingService loggingService;
    private final WorkflowManagerService workflowManagerService;

    /**
     * Check if the data is up to date. If it is not, start the ETL process.
     */
    public Response check() {
        if (loggingService.hasLogInProgressUnderADay()) return new Response(
                false,
                "The extraction process is already underway.",
                LocalDateTime.now()
        );
        if (loggingService.hasTheLastFolderAlreadyBeenProcessed()) {
            return new Response(
                    true,
                    "Up-to-date information.",
                    LocalDateTime.now()
            );
        } else if (loggingService.hasCanceledStatusUnderADay()) {
            workflowManagerService.resume();
            return new Response(
                    false,
                    "An unfinished process was found that is less than a day old. Proceeding with the same.",
                    LocalDateTime.now()
            );
        } else {
            workflowManagerService.start();
            return new Response(
                    false,
                    "Outdated information, extraction process initiating.",
                    LocalDateTime.now()
            );
        }
    }
}
