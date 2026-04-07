package com.gabriel_f_s.oci.input.entity;

import com.gabriel_f_s.oci.input.entity.enums.ProgressStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private ProgressStatus status;
    @Column(name = "last_folder_processed")
    private String lastFolderProcessed;
    @Column(name = "files_processed")
    private List<String> filesProcessed = new ArrayList<>();
    @Column(name = "start_date")
    private LocalDateTime startDate;
    @Column(name = "end_date")
    private LocalDateTime endDate;
    @Column(name = "time_spent_in_hours")
    private Long timeSpentInHours;
    private String error;

    public AuditLog(ProgressStatus status, LocalDateTime startDate) {
        this.status = status;
        this.startDate = startDate;
    }

    public void addFileProcessed(String file) {
        this.filesProcessed.add(file);
    }
}
