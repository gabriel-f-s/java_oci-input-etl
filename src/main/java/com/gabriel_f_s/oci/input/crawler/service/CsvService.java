package com.gabriel_f_s.oci.input.crawler.service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.zip.ZipInputStream;

@Service
public class CsvService {

    private static final Logger logger = LoggerFactory.getLogger(CsvService.class);

    private final int BATCH_SIZE = 5000;

    public <T> void extractDataAndSave(
            ZipInputStream zip,
            Class<T> dtoType,
            Consumer<List<T>> databaseSaveAction
    ) {
        InputStreamReader input = new InputStreamReader(zip, StandardCharsets.ISO_8859_1);
        CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(input)
                .withType(dtoType)
                .withSeparator(';')
                .withIgnoreLeadingWhiteSpace(true)
                .build();

        List<T> batch = new ArrayList<>();

        for (T dto : csvToBean) {
            batch.add(dto);

            if (batch.size() >= BATCH_SIZE) {
                databaseSaveAction.accept(batch);
                batch.clear();
            }
        }

        if (!batch.isEmpty()) {
            databaseSaveAction.accept(batch);
            batch.clear();
        }
    }
}
