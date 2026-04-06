package com.gabriel_f_s.oci.input.crawler.mapper;

import com.gabriel_f_s.oci.input.crawler.dto.csv.SocioCsvDTO;
import com.gabriel_f_s.oci.input.crawler.entity.Socio;
import com.gabriel_f_s.oci.input.crawler.util.CsvImportParse;
import org.springframework.stereotype.Component;

@Component
public class SocioMapper implements CSVMapper<Socio, SocioCsvDTO> {
    @Override
    public Socio mapTo(SocioCsvDTO dto) {
        Socio s = new Socio();
        s.setCnpjBasico(dto.getCnpjBasico());
        s.setIdentificadorDeSocio(CsvImportParse.parseIdentificadorSocio(dto.getIdentificadorDeSocio()));
        s.setNomeSocio(dto.getNomeSocio());
        s.setCnpjCpfDoSocio(dto.getCnpjCpfDoSocio());
        s.setQualificacaoSocio(dto.getQualificacaoSocio());
        s.setDataEntradaSociedade(CsvImportParse.parseData(dto.getDataEntradaSociedade()));
        s.setPais(dto.getPais());
        s.setRepresentanteLegal(dto.getRepresentanteLegal());
        s.setNomeDoRepresentante(dto.getNomeDoRepresentante());
        s.setQualificacaoRepresentanteLegal(dto.getQualificacaoRepresentanteLegal());
        s.setFaixaEtaria(dto.getFaixaEtaria());
        return s;
    }

    @Override
    public SocioCsvDTO unmapFrom(Socio s) {
        return SocioCsvDTO.builder()
                .cnpjBasico(s.getCnpjBasico())
                .identificadorDeSocio(CsvImportParse.formatIdentificadorSocio(s.getIdentificadorDeSocio()))
                .nomeSocio(s.getNomeSocio())
                .cnpjCpfDoSocio(s.getCnpjCpfDoSocio())
                .qualificacaoSocio(s.getQualificacaoSocio())
                .dataEntradaSociedade(CsvImportParse.formatData(s.getDataEntradaSociedade()))
                .pais(s.getPais())
                .representanteLegal(s.getRepresentanteLegal())
                .nomeDoRepresentante(s.getNomeDoRepresentante())
                .qualificacaoRepresentanteLegal(s.getQualificacaoRepresentanteLegal())
                .faixaEtaria(s.getFaixaEtaria())
                .build();
    }
}
