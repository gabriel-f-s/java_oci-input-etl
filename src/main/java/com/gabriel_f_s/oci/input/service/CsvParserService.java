package com.gabriel_f_s.oci.input.service;

import com.gabriel_f_s.oci.input.entity.AuditLog;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.enums.CSVReaderNullFieldIndicator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.ZipInputStream;

@Service
public class CsvParserService {

    private static final Logger logger = LoggerFactory.getLogger(CsvParserService.class);
    private final int BATCH_SIZE = 5000;
    private final LoggingService loggingService;

    public CsvParserService(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    public <T> void extractDataAndSave(
            ZipInputStream zip,
            Class<T> dtoType,
            AuditLog log,
            Consumer<List<T>> databaseSaveAction
    ) {
        InputStreamReader input = new InputStreamReader(zip, StandardCharsets.ISO_8859_1);

        long checkpoint = log.getRecordInserted();

        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(input)
                .withCSVParser(parser)
                .withSkipLines(Math.toIntExact(checkpoint))
                .build();

        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(csvReader)
                .withType(dtoType)
                .withFieldAsNull(CSVReaderNullFieldIndicator.BOTH)
                .withIgnoreLeadingWhiteSpace(true)
                .withIgnoreEmptyLine(true)
                .withExceptionHandler(e -> {
                    logger.warn("The line number {}, containing the content {}, could not be read. Error: {}", e.getLine(), e.getLineNumber(), e.getMessage());
                    return null;
                })
                .build();

        List<T> batch = new ArrayList<>();

        for (T dto : csvToBean) {
            batch.add(dto);
            if (batch.size() >= BATCH_SIZE) {
                databaseSaveAction.accept(batch);
                loggingService.updateRecordsInserted(log.getId(), batch.size());
                batch.clear();
            }
        }
        if (!batch.isEmpty()) {
            databaseSaveAction.accept(batch);
            loggingService.updateRecordsInserted(log.getId(), batch.size());
            batch.clear();
        }
    }
}
