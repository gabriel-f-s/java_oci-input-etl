package com.gabriel_f_s.oci.input.service;

import com.gabriel_f_s.oci.input.entity.AuditLog;
import com.gabriel_f_s.oci.input.entity.enums.ProgressStatus;
import com.gabriel_f_s.oci.input.exception.EntityNotFoundException;
import com.gabriel_f_s.oci.input.repository.AuditLogRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class LoggingService {

    private final WebScrapingService webScrapingService;
    private final AuditLogRepository logRepository;
    private final JdbcTemplate jdbcTemplate;

    public LoggingService(WebScrapingService webScrapingService, AuditLogRepository logRepository, JdbcTemplate jdbcTemplate) {
        this.logRepository = logRepository;
        this.webScrapingService = webScrapingService;
        this.jdbcTemplate = jdbcTemplate;
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
            if (logEndDate == null) return false;

            return logEndDate.isBefore(LocalDateTime.now());
        }
        return false;
    }

    /**
     * Check if there are any logs with an in_progress status within the last 24 hours.
     */
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
        List<String> processedFilesInLog = log.getFilesProcessed();

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
            auditLog.setLastHeartbeat(LocalDateTime.now());
            auditLog.setEndDate(null);
            return auditLog;
        }
        return null;
    }

    /**
     * Updates last current file.
     * @param log
     *       Log to update.
     * @param file
     *      File name to insert in log (filename.zip)
     */
    public void updateCurrentFile(AuditLog log, String file) {
        log.setCurrentFileName(file);
        log.setLastHeartbeat(LocalDateTime.now());
        logRepository.save(log);
    }

    /**
     * Updates last current file.
     * @param logId
     *       Id of a log to update.
     * @param record
     *      File name to insert in log (filename.zip)
     */
    public void updateRecordsInserted(Long logId, int record) {
        String selectSql = String.format("SELECT records_inserted FROM audit_logs WHERE id = %d", logId);
        Long actualRecord = jdbcTemplate.queryForObject(
                    selectSql,
                    Long.class
                );
        actualRecord += record;

        String sql = "UPDATE audit_logs SET records_inserted = ?, last_heartbeat = NOW() WHERE id = ?";
        jdbcTemplate.update(sql, actualRecord, logId);
    }

    /**
     * Updates last processed date.
     * @param log
     *      Log to update.
     * @param file
     *      File name to insert in log (filename.zip)
     */
    public void updateProcessedFiles(AuditLog log, String file) {
        if (!log.getFilesProcessed().contains(file)) {
            log.addFileProcessed(file);
            log.setRecordInserted(0L);
        }
        log.setLastHeartbeat(LocalDateTime.now());
        logRepository.save(log);
    }

    /**
     * Updates a canceled log, changing his status to in progress.
     * @param log
     *      Log to update.
     */
    public void updateCanceledLog(AuditLog log) {
        log.setStatus(ProgressStatus.IN_PROGRESS);
        log.setError(null);
        log.setLastHeartbeat(LocalDateTime.now());
        logRepository.save(log);
    }

    /**
     * Updates a in_progress log to error, in case of error.
     * @param error
     *      Log to update.
     */
    public void updateLogInCaseOfError(String error) {
        Optional<AuditLog> optionalLog = logRepository.findFirstByStatusOrderByStartDate(ProgressStatus.IN_PROGRESS);
        if (optionalLog.isEmpty()) return;

        AuditLog log = optionalLog.get();
        int timesStopped = log.getNumberOfTimesItStopped();
        log.setStatus(ProgressStatus.ERROR);
        log.setEndDate(LocalDateTime.now());
        log.setLastHeartbeat(LocalDateTime.now());
        log.setNumberOfTimesItStopped(timesStopped + 1);
        log.setError(error);
    }

    /**
     * Create a new log.
     */
    public AuditLog createNewLog() {
        AuditLog log = new AuditLog(
                ProgressStatus.IN_PROGRESS,
                LocalDateTime.now(),
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
        log.setLastHeartbeat(LocalDateTime.now());
        log.setEndDate(LocalDateTime.now());
        log.setTotalTimeSpentInMs(ChronoUnit.MILLIS.between(log.getStartDate(), log.getEndDate()));
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
