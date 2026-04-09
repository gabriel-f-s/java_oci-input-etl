package com.gabriel_f_s.oci.input.service;

import com.gabriel_f_s.oci.input.entity.AuditLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class WorkflowManagerService {

    private static final Logger logger = LoggerFactory.getLogger(WorkflowManagerService.class);

    private final WebScrapingService webScrapingService;
    private final LoggingService loggingService;
    private final OrchestrationService orchestrationService;

    public WorkflowManagerService(WebScrapingService webScrapingService, LoggingService loggingService, OrchestrationService orchestrationService) {
        this.webScrapingService = webScrapingService;
        this.loggingService = loggingService;
        this.orchestrationService = orchestrationService;
    }

    /**
     * Starts the ETL.
     */
    @Async()
    protected void start() {
        logger.info("Starting processing from the beginning.");
        AuditLog log = loggingService.createNewLog();
        startProcessing(log);
    }

    /**
     * Resumes the ETL.
     */
    @Async
    public void resume() {
        logger.info("Continuing processing from the last log.");
        AuditLog log = loggingService.findLastCanceledLogFilesProcessed();
        loggingService.updateCanceledLog(log);
        startProcessing(log);
    }

    /**
     * Performs file verification and begins processing file by file.
     * @param log
     *      Log to keep a record of the application.
     */
    private void startProcessing(AuditLog log) {
        List<String> webFiles = webScrapingService.getAllFilesNameFromLastFile();
        List<String> files = loggingService.checkProcessedFiles(log, webFiles);

        for (String file : files) {
            loggingService.updateCurrentFile(log, file);
            logger.info("Starting the {} file.", file);
            orchestrationService.downloadFileAndProcess(file, log);
            loggingService.updateProcessedFiles(log, file);
        }
        logger.info("Data extraction completed successfully at {}.", LocalDateTime.now());
        loggingService.finishLog(log);
    }
}
