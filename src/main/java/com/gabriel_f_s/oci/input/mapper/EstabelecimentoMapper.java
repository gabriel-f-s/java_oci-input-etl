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
        e.setCnpjBasico(ParsingUtils.stringProcessing(dto.getCnpjBasico(), 8));
        e.setCnpjOrdem(ParsingUtils.stringProcessing(dto.getCnpjOrdem(), 4));
        e.setCnpjDv(ParsingUtils.stringProcessing(dto.getCnpjDv(), 2));
        e.setIdentificadorMatrizFilial(ParsingUtils.parseInteger(dto.getIdentificadorMatrizFilial()));
        e.setNomeFantasia(ParsingUtils.stringProcessing(dto.getNomeFantasia(), 255));
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
            String[] cnaes = ParsingUtils.stringProcessing(dto.getCnaeFiscalSecundaria(), 255).split(",");
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
        e.setTipoLogradouro(ParsingUtils.stringProcessing(dto.getTipoLogradouro(), 50));
        e.setLogradouro(ParsingUtils.stringProcessing(dto.getLogradouro(), 255));
        e.setNumero(ParsingUtils.stringProcessing(dto.getNumero(), 50));
        e.setComplemento(ParsingUtils.stringProcessing(dto.getComplemento(), 255));
        e.setBairro(ParsingUtils.stringProcessing(dto.getBairro(), 60));
        e.setCep(ParsingUtils.stringProcessing(dto.getCep(), 8));
        e.setUf(ParsingUtils.stringProcessing(dto.getUf(), 2));
        Long municipioId = municipiosMap.get(dto.getMunicipio());
        if (municipioId != null) {
            Municipio municipio = new Municipio();
            municipio.setId(municipioId);
            e.setMunicipio(municipio);
        }
        e.setDdd1(ParsingUtils.stringProcessing(dto.getDdd1(), 4));
        e.setTelefone1(ParsingUtils.stringProcessing(dto.getTelefone1(), 15));
        e.setDdd2(ParsingUtils.stringProcessing(dto.getDdd2(), 4));
        e.setTelefone2(ParsingUtils.stringProcessing(dto.getTelefone2(), 15));
        e.setDddFax(ParsingUtils.stringProcessing(dto.getDddFax(), 4));
        e.setFax(ParsingUtils.stringProcessing(dto.getFax(), 15));
        e.setCorreioEletronico(ParsingUtils.stringProcessing(dto.getCorreioEletronico(), 255));
        e.setSituacaoEspecial(ParsingUtils.stringProcessing(dto.getSituacaoEspecial(), 255));
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
