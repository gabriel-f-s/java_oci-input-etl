package com.gabriel_f_s.oci.input.crawler.service;

import com.gabriel_f_s.oci.input.crawler.dto.csv.*;
import com.gabriel_f_s.oci.input.crawler.entity.*;
import com.gabriel_f_s.oci.input.crawler.exception.*;
import com.gabriel_f_s.oci.input.crawler.mapper.*;
import com.gabriel_f_s.oci.input.crawler.repository.NaturezaRepository;
import com.gabriel_f_s.oci.input.crawler.repository.QualificacaoRepository;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@RequiredArgsConstructor
public class ProcessService {

    private static final Logger logger = LoggerFactory.getLogger(ProcessService.class);

    private final RestClient restClient = RestClient.create();

    private final ConnectionService connectionService;
    private final CsvService csvService;
    private final DataIngestionService dataIngestionService;
    private final IOService ioService;

    private final CnaeMapper cnaeMapper;
    private final MotivoMapper motivoMapper;
    private final MunicipioMapper municipioMapper;
    private final NaturezaMapper naturezaMapper;
    private final PaisMapper paisMapper;
    private final QualificacaoMapper qualificacaoMapper;
    private final EmpresaMapper empresaMapper;
    private final EstabelecimentoMapper estabelecimentoMapper;
    private final SimplesMapper simplesMapper;
    private final SocioMapper socioMapper;

    private final NaturezaRepository naturezaRepository;
    private final QualificacaoRepository qualificacaoRepository;

    /**
     * Assemble the URI from the last month and start the download.
     * @param uri
     *          The connection URI from the main page.
     **/
    @Async
    @Transactional
    protected void start(String uri) {
        String lastMonth = connectionService.getLastMonth();
        String lastMonthUri = uri + lastMonth + "/";

        List<String> files = connectionService.getAllFilesNameFromLastFile(lastMonthUri);

        for (String file : files) {
            logger.info("Starting the {} file.", file);
            String downloadFileUri = lastMonthUri + file;
            downloadFileAndProcess(downloadFileUri);
            ioService.updateProcessedFiles(file);
        }
        logger.info("Data extraction completed successfully at {}.", LocalDateTime.now());
        ioService.updateLastProcessedDate(lastMonth);
    }

    /**
     * Download and process the file.
     * @param uri
     *          The connection URI from the main page.
     **/
    @Transactional
    public void downloadFileAndProcess(String uri) {
        restClient.get()
                .uri(uri)
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .exchange((request, response) -> {
                    if (response.getStatusCode().isError())
                        throw new FileException("Failed to download file: " + response.getStatusCode());

                    try (InputStream input = response.getBody()) {
                        processFileAndSave(input);
                    } catch (IOException e) {
                        throw new ZipDownloadFailedException("Failed to download zip file: " + e);
                    }
                    return null;
                });
    }

    /**
     * Processes the file, unpacking the zip archive.
     * @param input
     *          The input stream returned by the website.
     **/
    @Transactional
    protected void processFileAndSave(InputStream input) {
        try (ZipInputStream zip = new ZipInputStream(input)) {
            ZipEntry entry = zip.getNextEntry();

            while (entry != null) {
                String fileName = entry.getName();

                logger.info("Processing the {} file.", fileName);

                if (fileName.contains("CNAE")) {
                    csvService.extractDataAndSave(zip, CnaeCsvDTO.class, batch -> {
                        List<Cnae> cnaes = batch.stream()
                                .map(cnaeMapper::mapTo)
                                .toList();
                        dataIngestionService.insertCnaes(cnaes);
                    });
                } else if (fileName.contains("MOTI")) {
                    csvService.extractDataAndSave(zip, MotivoCsvDTO.class, batch -> {
                        List<Motivo> motivos = batch.stream()
                                .map(motivoMapper::mapTo)
                                .toList();
                        dataIngestionService.insertMotivos(motivos);
                    });
                } else if (fileName.contains("MUNIC")) {
                    csvService.extractDataAndSave(zip, MunicipioCsvDTO.class, batch -> {
                        List<Municipio> municipios = batch.stream()
                                .map(municipioMapper::mapTo)
                                .toList();
                        dataIngestionService.insertMunicipios(municipios);
                    });
                } else if (fileName.contains("NATJU")) {
                    csvService.extractDataAndSave(zip, NaturezaCsvDTO.class, batch -> {
                        List<Natureza> naturezas = batch.stream()
                                .map(naturezaMapper::mapTo)
                                .toList();
                        dataIngestionService.insertNaturezas(naturezas);
                    });
                } else if (fileName.contains("PAIS")) {
                    csvService.extractDataAndSave(zip, PaisCsvDTO.class, batch -> {
                        List<Pais> paises = batch.stream()
                                .map(paisMapper::mapTo)
                                .toList();
                        dataIngestionService.insertPaises(paises);
                    });
                } else if (fileName.contains("QUALS")) {
                    csvService.extractDataAndSave(zip, QualificacaoCsvDTO.class, batch -> {
                        List<Qualificacao> qualificacoes = batch.stream()
                                .map(qualificacaoMapper::mapTo)
                                .toList();
                        dataIngestionService.insertQualificacoes(qualificacoes);
                    });
                } else if (fileName.contains("EMPRE")) {
                    Map<String, Long> naturezasMap = naturezaRepository.findAll()
                            .stream()
                            .collect(Collectors.toMap(Natureza::getCodigo, Natureza::getId));
                    Map<String, Long> qualificacoesMap = qualificacaoRepository.findAll()
                            .stream()
                            .collect(Collectors.toMap(Qualificacao::getCodigo, Qualificacao::getId));;

                    csvService.extractDataAndSave(zip, EmpresaCsvDTO.class, batch -> {
                        List<Empresa> empresas = batch.stream()
                                .map(dto -> empresaMapper.mapTo(dto, naturezasMap, qualificacoesMap))
                                .toList();
                        dataIngestionService.insertEmpresas(empresas);
                    });
                } else if (fileName.contains("ESTABELE")) {
                    csvService.extractDataAndSave(zip, EstabelecimentoCsvDTO.class, batch -> {
                        List<Estabelecimento> estabelecimentos = batch.stream()
                                .map(estabelecimentoMapper::mapTo)
                                .toList();
                        dataIngestionService.insertEstabelecimentos(estabelecimentos);
                    });
                } else if (fileName.contains("SIMPLES")) {
                    csvService.extractDataAndSave(zip, SimplesCsvDTO.class, batch -> {
                        List<Simples> simples = batch.stream()
                                .map(simplesMapper::mapTo)
                                .toList();
                        dataIngestionService.insertSimples(simples);
                    });
                } else if (fileName.contains("SOCIO")) {
                    csvService.extractDataAndSave(zip, SocioCsvDTO.class, batch -> {
                        List<Socio> socios = batch.stream()
                                .map(socioMapper::mapTo)
                                .toList();
                        dataIngestionService.insertSocios(socios);
                    });
                }
                zip.closeEntry();
                entry = zip.getNextEntry();
            }

        } catch (IOException e) {
            throw new ZipProcessingFailedException("Failed to process the zip file: " + e);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Database error: " + e);
        }
    }
}
