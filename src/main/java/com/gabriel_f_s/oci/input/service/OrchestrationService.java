package com.gabriel_f_s.oci.input.service;

import com.gabriel_f_s.oci.input.dto.csv.*;
import com.gabriel_f_s.oci.input.entity.*;
import com.gabriel_f_s.oci.input.exception.*;
import com.gabriel_f_s.oci.input.mapper.*;
import com.gabriel_f_s.oci.input.repository.*;
import com.gabriel_f_s.oci.input.dto.csv.*;
import com.gabriel_f_s.oci.input.entity.*;
import com.gabriel_f_s.oci.input.exception.DatabaseException;
import com.gabriel_f_s.oci.input.exception.FileException;
import com.gabriel_f_s.oci.input.exception.ZipDownloadFailedException;
import com.gabriel_f_s.oci.input.exception.ZipProcessingFailedException;
import com.gabriel_f_s.oci.input.mapper.*;
import com.gabriel_f_s.oci.input.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
@RequiredArgsConstructor
public class OrchestrationService {

    private static final Logger logger = LoggerFactory.getLogger(OrchestrationService.class);

    private final RestClient restClient = RestClient.create();

    private final CsvParserService csvParserService;
    private final PersistenceService persistenceService;
    private final WebScrapingService webScrapingService;

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

    private final CnaeRepository cnaeRepository;
    private final MotivoRepository motivoRepository;
    private final MunicipioRepository municipioRepository;
    private final NaturezaRepository naturezaRepository;
    private final PaisRepository paisRepository;
    private final QualificacaoRepository qualificacaoRepository;


    /**
     * Download and process the file.
     * @param file
     *          The file name.
     **/
    public void downloadFileAndProcess(String file) {
        String uri = webScrapingService.getLastMonthUri() + "/" + file;
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
                    } catch (Exception e) {
                        throw new RuntimeException("Failed: " + e);
                    }
                    return null;
                });
    }

    /**
     * Processes the file, unpacking the zip archive.
     * @param input
     *          The input stream returned by the website.
     **/
    protected void processFileAndSave(InputStream input) {
        try (ZipInputStream zip = new ZipInputStream(input)) {
            ZipEntry entry = zip.getNextEntry();

            while (entry != null) {
                String fileName = entry.getName();

                logger.info("Processing the {} file.", fileName);

                if (fileName.contains("CNAE")) {
                    csvParserService.extractDataAndSave(zip, CnaeCsvDTO.class, batch -> {
                        List<Cnae> cnaes = batch.stream()
                                .map(cnaeMapper::mapTo)
                                .toList();
                        persistenceService.insertCnaes(cnaes);
                    });
                } else if (fileName.contains("MOTI")) {
                    csvParserService.extractDataAndSave(zip, MotivoCsvDTO.class, batch -> {
                        List<Motivo> motivos = batch.stream()
                                .map(motivoMapper::mapTo)
                                .toList();
                        persistenceService.insertMotivos(motivos);
                    });
                } else if (fileName.contains("MUNIC")) {
                    csvParserService.extractDataAndSave(zip, MunicipioCsvDTO.class, batch -> {
                        List<Municipio> municipios = batch.stream()
                                .map(municipioMapper::mapTo)
                                .toList();
                        persistenceService.insertMunicipios(municipios);
                    });
                } else if (fileName.contains("NATJU")) {
                    csvParserService.extractDataAndSave(zip, NaturezaCsvDTO.class, batch -> {
                        List<Natureza> naturezas = batch.stream()
                                .map(naturezaMapper::mapTo)
                                .toList();
                        persistenceService.insertNaturezas(naturezas);
                    });
                } else if (fileName.contains("PAIS")) {
                    csvParserService.extractDataAndSave(zip, PaisCsvDTO.class, batch -> {
                        List<Pais> paises = batch.stream()
                                .map(paisMapper::mapTo)
                                .toList();
                        persistenceService.insertPaises(paises);
                    });
                } else if (fileName.contains("QUALS")) {
                    csvParserService.extractDataAndSave(zip, QualificacaoCsvDTO.class, batch -> {
                        List<Qualificacao> qualificacoes = batch.stream()
                                .map(qualificacaoMapper::mapTo)
                                .toList();
                        persistenceService.insertQualificacoes(qualificacoes);
                    });
                } else if (fileName.contains("EMPRE")) {
                    Map<String, Long> naturezasMap = generateReferenceMap(naturezaRepository);
                    Map<String, Long> qualificacoesMap = generateReferenceMap(qualificacaoRepository);

                    csvParserService.extractDataAndSave(zip, EmpresaCsvDTO.class, batch -> {
                        List<Empresa> empresas = batch.stream()
                                .map(dto -> empresaMapper.mapTo(dto, naturezasMap, qualificacoesMap))
                                .toList();
                        persistenceService.insertEmpresas(empresas);
                    });
                } else if (fileName.contains("ESTABELE")) {
                    Map<String, Long> motivosMap = generateReferenceMap(motivoRepository);
                    Map<String, Long> paisesMap = generateReferenceMap(paisRepository);
                    Map<String, Long> cnaesMap = generateReferenceMap(cnaeRepository);
                    Map<String, Long> municipiosMap = generateReferenceMap(municipioRepository);

                    csvParserService.extractDataAndSave(zip, EstabelecimentoCsvDTO.class, batch -> {
                        List<Estabelecimento> estabelecimentos = batch.stream()
                                .map(dto -> estabelecimentoMapper.mapTo(
                                        dto,
                                        motivosMap,
                                        paisesMap,
                                        cnaesMap,
                                        municipiosMap
                                ))
                                .toList();
                        persistenceService.insertEstabelecimentos(estabelecimentos);
                    });
                } else if (fileName.contains("SIMPLES")) {
                    csvParserService.extractDataAndSave(zip, SimplesCsvDTO.class, batch -> {
                        List<Simples> simples = batch.stream()
                                .map(simplesMapper::mapTo)
                                .toList();
                        persistenceService.insertSimples(simples);
                    });
                } else if (fileName.contains("SOCIO")) {
                    csvParserService.extractDataAndSave(zip, SocioCsvDTO.class, batch -> {
                        List<Socio> socios = batch.stream()
                                .map(socioMapper::mapTo)
                                .toList();
                        persistenceService.insertSocios(socios);
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

    private <T extends DomainEntity> Map<String, Long> generateReferenceMap(DomainEntitiesRepository<T> repository) {
        return repository.findAll()
                .stream()
                .collect(Collectors.toMap(
                        DomainEntity::getCodigo, DomainEntity::getId
                ));
    }
}
