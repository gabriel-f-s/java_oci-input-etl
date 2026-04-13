package com.gabriel_f_s.oci.input.repository;

import com.gabriel_f_s.oci.input.entity.AuditLog;
import com.gabriel_f_s.oci.input.entity.enums.ProgressStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    Optional<AuditLog> findByLastFolderProcessed(String lastFolderProcessed);
    Optional<AuditLog> findByStatus(ProgressStatus status);
    Optional<AuditLog> findFirstByStatusOrderByStartDate(ProgressStatus status);
}
