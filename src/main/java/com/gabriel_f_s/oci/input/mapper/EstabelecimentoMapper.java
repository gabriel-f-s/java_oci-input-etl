package com.gabriel_f_s.oci.input.mapper;

import com.gabriel_f_s.oci.input.dto.csv.EstabelecimentoCsvDTO;
import com.gabriel_f_s.oci.input.entity.*;
import com.gabriel_f_s.oci.input.mapper.utils.ParsingUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class EstabelecimentoMapper implements CSVMapper<Estabelecimento, EstabelecimentoCsvDTO> {

    @Override
    public Estabelecimento mapTo(EstabelecimentoCsvDTO dto) { return null; }

    public Estabelecimento mapTo(
            EstabelecimentoCsvDTO dto,
            Map<String, Long> motivosMap,
            Map<String, Long> paisesMap,
            Map<String, Long> cnaesMap,
            Map<String, Long> municipiosMap
    ) {
        Estabelecimento e = new Estabelecimento();
        e.setCnpjBasico(dto.getCnpjBasico());
        e.setCnpjOrdem(dto.getCnpjOrdem());
        e.setCnpjDv(dto.getCnpjDv());
        e.setIdentificadorMatrizFilial(ParsingUtils.parseInteger(dto.getIdentificadorMatrizFilial()));
        e.setNomeFantasia(dto.getNomeFantasia());
        e.setSituacaoCadastral(ParsingUtils.parseInteger(dto.getSituacaoCadastral()));
        e.setDataSituacaoCadastral(ParsingUtils.parseLocalDate(dto.getDataSituacaoCadastral()));
        Long motivoId = motivosMap.get(dto.getMotivoSituacaoCadastral());
        if (motivoId != null) {
            Motivo motivo = new Motivo();
            motivo.setId(motivoId);
            e.setMotivoSituacaoCadastral(motivo);
        }
        e.setNomeCidadeExterior(dto.getNomeCidadeExterior());
        Long paisId = paisesMap.get(dto.getPais());
        if (paisId != null) {
            Pais pais = new Pais();
            pais.setId(paisId);
            e.setPais(pais);
        }
        e.setDataInicioAtividade(ParsingUtils.parseLocalDate(dto.getDataInicioAtividade()));
        Long cnaeId = cnaesMap.get(dto.getCnaeFiscalPrincipal());
        if (cnaeId != null) {
            Cnae cnaePrincipal = new Cnae();
            cnaePrincipal.setId(cnaeId);
            e.setCnaeFiscalPrincipal(cnaePrincipal);
        }
        if (dto.getCnaeFiscalSecundaria() != null) {
            List<Cnae> cnaesSecudarios = new ArrayList<>();
            String[] cnaes = ParsingUtils.stringTreatment(dto.getCnaeFiscalSecundaria()).split(",");
            for (String codigo : cnaes) {
                Long id = cnaesMap.get(codigo);
                if (id != null) {
                    Cnae cnaeSecundario = new Cnae();
                    cnaeSecundario.setId(id);
                    cnaesSecudarios.add(cnaeSecundario);
                }
            }
            e.setCnaeFiscalSecundaria(cnaesSecudarios);
        }
        e.setTipoLogradouro(ParsingUtils.stringTreatment(dto.getTipoLogradouro()));
        e.setLogradouro(ParsingUtils.stringTreatment(dto.getLogradouro()));
        e.setNumero(ParsingUtils.stringTreatment(dto.getNumero()));
        e.setComplemento(ParsingUtils.stringTreatment(dto.getComplemento()));
        e.setBairro(dto.getBairro());
        e.setCep(dto.getCep());
        e.setUf(dto.getUf());
        Long municipioId = municipiosMap.get(dto.getMunicipio());
        if (municipioId != null) {
            Municipio municipio = new Municipio();
            municipio.setId(municipioId);
            e.setMunicipio(municipio);
        }
        e.setDdd1(dto.getDdd1());
        e.setTelefone1(dto.getTelefone1());
        e.setDdd2(dto.getDdd2());
        e.setTelefone2(dto.getTelefone2());
        e.setDddFax(dto.getDddFax());
        e.setFax(dto.getFax());
        e.setCorreioEletronico(ParsingUtils.stringTreatment(dto.getCorreioEletronico()));
        e.setSituacaoEspecial(ParsingUtils.stringTreatment(dto.getSituacaoEspecial()));
        e.setDataSituacaoEspecial(ParsingUtils.parseLocalDate(dto.getDataSituacaoEspecial()));
        return e;
    }

    @Override
    public EstabelecimentoCsvDTO unmapFrom(Estabelecimento e) {
        return EstabelecimentoCsvDTO.builder()
                .cnpjBasico(e.getCnpjBasico())
                .cnpjOrdem(e.getCnpjOrdem())
                .cnpjDv(e.getCnpjDv())
                .identificadorMatrizFilial(e.getIdentificadorMatrizFilial().toString())
                .nomeFantasia(e.getNomeFantasia())
                .situacaoCadastral(e.getSituacaoCadastral().toString())
                .dataSituacaoCadastral(e.getDataSituacaoCadastral().toString())
                .motivoSituacaoCadastral(e.getMotivoSituacaoCadastral().getCodigo())
                .nomeCidadeExterior(e.getNomeCidadeExterior())
                .pais(e.getPais().getCodigo())
                .dataInicioAtividade(e.getDataInicioAtividade().toString())
                .cnaeFiscalPrincipal(e.getCnaeFiscalPrincipal().getCodigo())
                .tipoLogradouro(e.getTipoLogradouro())
                .logradouro(e.getLogradouro())
                .numero(e.getNumero())
                .complemento(e.getComplemento())
                .bairro(e.getBairro())
                .cep(e.getCep())
                .uf(e.getUf())
                .municipio(e.getMunicipio().getCodigo())
                .ddd1(e.getDdd1())
                .telefone1(e.getTelefone1())
                .ddd2(e.getDdd2())
                .telefone2(e.getTelefone2())
                .dddFax(e.getDddFax())
                .fax(e.getFax())
                .correioEletronico(e.getCorreioEletronico())
                .situacaoEspecial(e.getSituacaoEspecial())
                .dataSituacaoEspecial(e.getDataSituacaoEspecial().toString())
                .build();
    }


}
