package com.gabriel_f_s.oci.input.mapper;

import com.gabriel_f_s.oci.input.dto.csv.SocioCsvDTO;
import com.gabriel_f_s.oci.input.entity.Pais;
import com.gabriel_f_s.oci.input.entity.Qualificacao;
import com.gabriel_f_s.oci.input.entity.Socio;
import com.gabriel_f_s.oci.input.mapper.utils.ParsingUtils;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SocioMapper implements CSVMapper<Socio, SocioCsvDTO> {
    @Override
    public Socio mapTo(SocioCsvDTO dto) { return null; }

    public Socio mapTo(
            SocioCsvDTO dto,
            Map<String, Long> qualificacoesMap,
            Map<String, Long> paisesMap
    ) {
        Socio s = new Socio();
        s.setCnpjBasico(ParsingUtils.stringProcessing(dto.getCnpjBasico(), 8));
        s.setIdentificadorDeSocio(ParsingUtils.parseInteger(dto.getIdentificadorDeSocio()));
        s.setNomeSocio(ParsingUtils.stringProcessing(dto.getNomeSocio(), 255));
        s.setCnpjCpfDoSocio(ParsingUtils.stringProcessing(dto.getCnpjCpfDoSocio(), 14));
        Long qualificacaoId = qualificacoesMap.get(dto.getQualificacaoSocio());
        if (qualificacaoId != null) {
            Qualificacao qualificacao = new Qualificacao();
            qualificacao.setId(qualificacaoId);
            s.setQualificacaoSocio(qualificacao);
        }
        s.setDataEntradaSociedade(ParsingUtils.parseLocalDate(dto.getDataEntradaSociedade()));
        Long paisId = paisesMap.get(dto.getPais());
        if (paisId != null) {
            Pais pais = new Pais();
            pais.setId(paisId);
            s.setPais(pais);
        }
        s.setRepresentanteLegal(ParsingUtils.stringProcessing(dto.getRepresentanteLegal(), 14));
        s.setNomeDoRepresentante(ParsingUtils.stringProcessing(dto.getNomeDoRepresentante(), 255));
        Long qualificacaoRepresentanteId = qualificacoesMap.get(dto.getQualificacaoRepresentanteLegal());
        if (qualificacaoRepresentanteId != null) {
            Qualificacao qualificacao = new Qualificacao();
            qualificacao.setId(qualificacaoRepresentanteId);
            s.setQualificacaoRepresentanteLegal(qualificacao);
        }
        s.setFaixaEtaria(Integer.parseInt(dto.getFaixaEtaria()));
        return s;
    }

    @Override
    public SocioCsvDTO unmapFrom(Socio s) {
        return SocioCsvDTO.builder()
                .cnpjBasico(s.getCnpjBasico())
                .identificadorDeSocio(s.getIdentificadorDeSocio().toString())
                .nomeSocio(s.getNomeSocio())
                .cnpjCpfDoSocio(s.getCnpjCpfDoSocio())
                .qualificacaoSocio(s.getQualificacaoSocio().toString())
                .dataEntradaSociedade(s.getDataEntradaSociedade().toString())
                .pais(s.getPais().toString())
                .representanteLegal(s.getRepresentanteLegal())
                .nomeDoRepresentante(s.getNomeDoRepresentante())
                .qualificacaoRepresentanteLegal(s.getQualificacaoRepresentanteLegal().toString())
                .faixaEtaria(s.getFaixaEtaria().toString())
                .build();
    }
}
