package com.gabriel_f_s.oci.input.service;

import com.gabriel_f_s.oci.input.entity.AuditLog;
import com.gabriel_f_s.oci.input.entity.enums.ProgressStatus;
import com.gabriel_f_s.oci.input.exception.EntityNotFoundException;
import com.gabriel_f_s.oci.input.repository.AuditLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LoggingService {

    private final WebScrapingService webScrapingService;
    private final AuditLogRepository logRepository;

    public LoggingService(WebScrapingService webScrapingService, AuditLogRepository logRepository) {
        this.logRepository = logRepository;
        this.webScrapingService = webScrapingService;
    }

    /**
     * Checks if the site has new files and compares them with the logs.
     */
    public boolean hasTheLastFolderAlreadyBeenProcessed() {
        String lastMonth = webScrapingService.getLastMonth();
        return logRepository.findByLastFolderProcessed(lastMonth).isPresent();
    }

    /**
     * Check if there are any logs with a canceled status within the last 24 hours.
     */
    public boolean hasCanceledStatusUnderADay() {
        Optional<AuditLog> optionalAuditLog = logRepository.findFirstByStatusOrderByStartDate(ProgressStatus.CANCELED);
        if (optionalAuditLog.isPresent()) {
            AuditLog auditLog = optionalAuditLog
                    .orElseThrow(() -> new EntityNotFoundException("No log could be found"));
            LocalDateTime logEndDate = auditLog.getEndDate();
            return logEndDate.isBefore(LocalDateTime.now());
        }
        return false;
    }

    public boolean hasLogInProgressUnderADay() {
        Optional<AuditLog> optionalAuditLog = logRepository.findFirstByStatusOrderByStartDate(ProgressStatus.IN_PROGRESS);
        return optionalAuditLog.isPresent();
    }

    /**
     * Check the most recently processed files in a log.
     * @param log
     *      Log to update
     * @param files
     *      Files to compare and verify if they match the log.
     */
    public List<String> checkProcessedFiles(AuditLog log, List<String> files) {
        AuditLog auditLog = findAuditLogById(log);
        List<String> processedFilesInLog = auditLog.getFilesProcessed();

        if (processedFilesInLog.isEmpty()) return files;

        files.removeAll(processedFilesInLog);
        return files;
    }

    /**
     * Returns a log with a canceled status within the last 24 hours.
     */
    public AuditLog findLastCanceledLogFilesProcessed() {
        Optional<AuditLog> optionalAuditLog = logRepository.findFirstByStatusOrderByStartDate(ProgressStatus.CANCELED);
        if (optionalAuditLog.isPresent()) {
            AuditLog auditLog = optionalAuditLog
                    .orElseThrow(() -> new EntityNotFoundException("No log could be found"));
            auditLog.setStatus(ProgressStatus.IN_PROGRESS);
            auditLog.setStartDate(LocalDateTime.now());
            auditLog.setEndDate(null);
            return auditLog;
        }
        return null;
    }

    /**
     * Updates last processed date.
     * @param log
     *      Log to update.
     * @param file
     *      File name to insert in log (filename.zip)
     */
    public void updateProcessedFiles(AuditLog log, String file) {
        AuditLog auditLog = findAuditLogById(log);
        if (!auditLog.getFilesProcessed().contains(file))
            auditLog.addFileProcessed(file);
        logRepository.save(auditLog);
    }

    /**
     * Updates a canceled log, changing his status to in progress.
     * @param log
     *      Log to update.
     */
    public void updateCanceledLog(AuditLog log) {
        log.setStatus(ProgressStatus.IN_PROGRESS);
        log.setError(null);
        logRepository.save(log);
    }

    /**
     * Create a new log.
     */
    public AuditLog createNewLog() {
        AuditLog log = new AuditLog(
                ProgressStatus.IN_PROGRESS,
                LocalDateTime.now()
        );
        logRepository.save(log);
        return log;
    }

    /**
     * Finalizes an existing log.
     * @param log
     *      Log to finish
     */
    public void finishLog(AuditLog log) {
        log.setLastFolderProcessed(webScrapingService.getLastMonth());
        log.setStatus(ProgressStatus.FINISHED);
        log.setEndDate(LocalDateTime.now());
        log.setTimeSpentInHours(ChronoUnit.HOURS.between(log.getStartDate(), log.getEndDate()));
        logRepository.save(log);
    }

    /**
     * Returns a log.
     */
    private AuditLog findAuditLogById(AuditLog log) {
        return logRepository.findById(log.getId())
                .orElseThrow(() -> new EntityNotFoundException("No log could be found with this ID: " + log.getId()));
    }
}
