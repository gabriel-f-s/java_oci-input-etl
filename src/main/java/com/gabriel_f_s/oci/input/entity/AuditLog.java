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

    @Column(name = "current_file_name")
    private String currentFileName;

    @Column(name = "files_processed")
    private List<String> filesProcessed = new ArrayList<>();

    @Column(name = "records_inserted")
    private Long recordInserted = 0L;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "last_heartbeat")
    private LocalDateTime lastHeartbeat;

    @Column(name = "number_of_times_it_stopped")
    private Integer numberOfTimesItStopped = 0;

    @Column(name = "total_time_spent_in_ms")
    private Long totalTimeSpentInMs;

    @Column(columnDefinition = "TEXT")
    private String error;

    public AuditLog(ProgressStatus status, LocalDateTime startDate, LocalDateTime lastHeartbeat) {
        this.status = status;
        this.startDate = startDate;
        this.lastHeartbeat = lastHeartbeat;
    }

    public void addFileProcessed(String file) {
        this.filesProcessed.add(file);
    }

    public void addNumberOfTimesItStopped() {
        this.numberOfTimesItStopped += 1;
    }
}
