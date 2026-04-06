package com.gabriel_f_s.oci.input.crawler.service;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gabriel_f_s.oci.input.crawler.dto.Status;
import com.gabriel_f_s.oci.input.crawler.exception.FileException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

@Service
public class IOService {

    private static final Logger logger = LoggerFactory.getLogger(IOService.class);
    private final String FILE_PATH = "data/status.json";

    private final ObjectMapper mapper;
    private final ConnectionService connectionService;

    public IOService(ConnectionService connectionService) {
        this.mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        this.connectionService = connectionService;
    }

    /**
     * Checks if the site has new files and compares them with
     * the status.json file in the resources.
     *
     */
    public boolean isLastProcessedDateUpToDate() {
        String lastMonth = connectionService.getLastMonth();
        Status json = loadFile();

        return json != null && lastMonth.equals(json.getLastProcessedDate());
    }

    /**
     * Updates last processed date.
     * @param lastMonth
     *      Month to insert into the status file
     */
    public void updateLastProcessedDate(String lastMonth) {
        Status json = loadFile();
        json.setLastProcessedDate(lastMonth);
        saveFile(json);
    }

    /**
     * Updates processed files
     * @param fileName
     *      File name to insert into the status file
     */
    public void updateProcessedFiles(String fileName) {
        Status json = loadFile();
        json.addFile(fileName);
        saveFile(json);
    }

    /**
     * Updates file progress.
     * @param file
     *      File name
     * @param progress
     *      Progress
     */
    public void updateFileProgress(String file, Long progress) {
        Status json = loadFile();
        if (!json.getFileProgress().isEmpty()) json.getFileProgress().clear();
        json.addFileProgress(file, progress);
    }

    /**
     * Load the status.json file.
     */
    private Status loadFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return createInitialFile(file);
        }
        try {
            return mapper.readValue(file, Status.class);
        } catch (IOException exception) {
            logger.warn("Failed to read file.");
            throw new FileException("Failed to read file: " + exception);
        }
    }

    /**
     * Saves the status.json file.
     */
    private void saveFile(Status status) {
        try {
            mapper.writeValue(new File(FILE_PATH), status);
        } catch (IOException e) {
            throw new FileException("Failed to save file: " + e);
        }
    }

    /**
     * Creates the status.json file.
     */
    private Status createInitialFile(File file) {
        try {
            if (file.getParentFile() != null) file.getParentFile().mkdirs();

            Status newStatus = new Status("", new ArrayList<>(), new HashMap<>());
            mapper.writeValue(file, newStatus);
            return newStatus;
        } catch (IOException e) {
            throw new FileException("Failed to create file: " + e.getMessage());
        }
    }
}
