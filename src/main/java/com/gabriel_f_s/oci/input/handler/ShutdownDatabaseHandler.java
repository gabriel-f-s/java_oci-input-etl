package com.gabriel_f_s.oci.input.handler;

import com.gabriel_f_s.oci.input.entity.enums.ProgressStatus;
import com.gabriel_f_s.oci.input.repository.AuditLogRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class ShutdownDatabaseHandler implements SmartLifecycle {

    private static final Logger logger = LoggerFactory.getLogger(ShutdownDatabaseHandler.class);
    private boolean isRunning = false;

    private final JdbcTemplate jdbcTemplate;

    public ShutdownDatabaseHandler(AuditLogRepository repository, JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void stop() {
        logger.info("Performing status cleanup before full shutdown...");
        try {
            int numberOfTimesItStopped = jdbcTemplate.queryForObject(
                    "SELECT number_of_times_it_stopped FROM public.audit_logs WHERE status = 'IN_PROGRESS';",
                    int.class
            );
            numberOfTimesItStopped += 1;

            String sql = "UPDATE audit_logs SET status = ?, end_date = ?, last_heartbeat = ?, number_of_times_it_stopped = ?, error = ? WHERE status = ?";
            int rows = jdbcTemplate.update(sql,
                    ProgressStatus.CANCELED.name(),
                    LocalDateTime.now(),
                    LocalDateTime.now(),
                    numberOfTimesItStopped,
                    "Application closed unexpectedly.",
                    ProgressStatus.IN_PROGRESS.name()
                    );
            if (rows != 0) logger.info("Status updated successfully! Rows affected: {}", rows);
        } catch (Exception e) {
            logger.error("Error updating status: {}", e.getMessage());
        }
        this.isRunning = false;
    }

    @Override
    public void start() { this.isRunning = true; }

    @Override
    public boolean isRunning() { return this.isRunning; }

    @Override
    public int getPhase() {
        return Integer.MIN_VALUE;
    }
}
