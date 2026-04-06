package com.gabriel_f_s.oci.input.crawler.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.gabriel_f_s.oci.input.crawler.dto.Status;
import com.gabriel_f_s.oci.input.crawler.exception.ConnectionFailedException;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
@AllArgsConstructor
public class ConnectionService {

    private final RestClient restClient = RestClient.create();
    private final String URI = "https://dados-abertos-rf-cnpj.casadosdados.com.br/arquivos/";

    /**
     * Returns a list of files available on the page.
     **/
    public List<String> getAllFilesNameFromLastFile(String uri) {
        List<String> files = new ArrayList<>();
        try {
            Document document = Jsoup.connect(uri).get();
            Elements links = document.getElementsByTag("a");

            List<String> largeFilesName = new ArrayList<>();
            for (Element file : links) {
                String fileName = file.text();
                if (fileName.matches("([^\"]+.zip)")) {
                    if (fileName.contains("Empresas")
                        || fileName.contains("Estabelecimentos")
                        || fileName.contains(("Simples"))
                        || fileName.contains("Socios")) {
                        largeFilesName.add(fileName);
                        continue;
                    }
                    files.add(fileName);
                }
            }
            for (String fileName : largeFilesName) {
                files.add(fileName);
            }
        } catch (IOException e) {
            throw new ConnectionFailedException("Unable to connect to the page: " + e.getMessage());
        }
        return files;
    }

    /**
     * Check which is the last available month in the HTML.
     **/
    public String getLastMonth() {
        String document = fetchHtml();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        String actualMonth = LocalDateTime.now().format(formatter);
        String lastMonth;

        int lastMonthIndexStart = document.indexOf(actualMonth);

        if (lastMonthIndexStart == -1) {
            String pastMonth = LocalDateTime.now().minusMonths(1).format(formatter);
            lastMonthIndexStart = document.indexOf(pastMonth);
            lastMonth = document.substring(lastMonthIndexStart, lastMonthIndexStart + 10);
        } else {
            lastMonth = document.substring(lastMonthIndexStart, lastMonthIndexStart + 10);
        }
        return lastMonth;
    }

    /**
     * Connect to the website.
     **/
    private String fetchHtml() {
        return restClient
                .get()
                .uri(URI)
                .retrieve()
                .body(String.class);
    }
}
