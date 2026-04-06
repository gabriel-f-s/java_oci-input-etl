package com.gabriel_f_s.oci.input.crawler.mapper;

import com.gabriel_f_s.oci.input.crawler.dto.csv.EstabelecimentoCsvDTO;
import com.gabriel_f_s.oci.input.crawler.entity.Estabelecimento;
import com.gabriel_f_s.oci.input.crawler.util.CsvImportParse;
import org.springframework.stereotype.Component;

@Component
public class EstabelecimentoMapper implements CSVMapper<Estabelecimento, EstabelecimentoCsvDTO> {
    @Override
    public Estabelecimento mapTo(EstabelecimentoCsvDTO dto) {
        Estabelecimento e = new Estabelecimento();
        e.setCnpjBasico(dto.getCnpjBasico());
        e.setCnpjOrdem(dto.getCnpjOrdem());
        e.setCnpjDv(dto.getCnpjDv());
        e.setIdentificadorMatrizFilial(CsvImportParse.parseIdentificadorMatrizFilial(dto.getIdentificadorMatrizFilial()));
        e.setNomeFantasia(dto.getNomeFantasia());
        e.setSituacaoCadastral(CsvImportParse.parseSituacaoCadastral(dto.getSituacaoCadastral()));
        e.setDataSituacaoCadastral(CsvImportParse.parseData(dto.getDataSituacaoCadastral()));
        e.setMotivoSituacaoCadastral(dto.getMotivoSituacaoCadastral());
        e.setNomeCidadeExterior(dto.getNomeCidadeExterior());
        e.setPais(dto.getPais());
        e.setDataInicioAtividade(CsvImportParse.parseData(dto.getDataInicioAtividade()));
        e.setCnaeFiscalPrincipal(dto.getCnaeFiscalPrincipal());
        e.setCnaeFiscalSecundaria(CsvImportParse.parseCnaeSecundarios(dto.getCnaeFiscalSecundaria()));
        e.setTipoLogradouro(dto.getTipoLogradouro());
        e.setLogradouro(dto.getLogradouro());
        e.setNumero(dto.getNumero());
        e.setComplemento(dto.getComplemento());
        e.setBairro(dto.getBairro());
        e.setCep(dto.getCep());
        e.setUf(dto.getUf());
        e.setMunicipio(dto.getMunicipio());
        e.setDdd1(dto.getDdd1());
        e.setTelefone1(dto.getTelefone1());
        e.setDdd2(dto.getDdd2());
        e.setTelefone2(dto.getTelefone2());
        e.setDddFax(dto.getDddFax());
        e.setFax(dto.getFax());
        e.setCorreioEletronico(dto.getCorreioEletronico());
        e.setSituacaoEspecial(dto.getSituacaoEspecial());
        e.setDataSituacaoEspecial(CsvImportParse.parseData(dto.getDataSituacaoEspecial()));
        return e;
    }

    @Override
    public EstabelecimentoCsvDTO unmapFrom(Estabelecimento e) {
        return EstabelecimentoCsvDTO.builder()
                .cnpjBasico(e.getCnpjBasico())
                .cnpjOrdem(e.getCnpjOrdem())
                .cnpjDv(e.getCnpjDv())
                .identificadorMatrizFilial(CsvImportParse.formatIdentificadorMatrizFilial(e.getIdentificadorMatrizFilial()))
                .nomeFantasia(e.getNomeFantasia())
                .situacaoCadastral(CsvImportParse.formatSituacaoCadastral(e.getSituacaoCadastral()))
                .dataSituacaoCadastral(CsvImportParse.formatData(e.getDataSituacaoCadastral()))
                .motivoSituacaoCadastral(e.getMotivoSituacaoCadastral())
                .nomeCidadeExterior(e.getNomeCidadeExterior())
                .pais(e.getPais())
                .dataInicioAtividade(CsvImportParse.formatData(e.getDataInicioAtividade()))
                .cnaeFiscalPrincipal(e.getCnaeFiscalPrincipal())
                .cnaeFiscalSecundaria(CsvImportParse.formatCnaeSecundarios(e.getCnaeFiscalSecundaria()))
                .tipoLogradouro(e.getTipoLogradouro())
                .logradouro(e.getLogradouro())
                .numero(e.getNumero())
                .complemento(e.getComplemento())
                .bairro(e.getBairro())
                .cep(e.getCep())
                .uf(e.getUf())
                .municipio(e.getMunicipio())
                .ddd1(e.getDdd1())
                .telefone1(e.getTelefone1())
                .ddd2(e.getDdd2())
                .telefone2(e.getTelefone2())
                .dddFax(e.getDddFax())
                .fax(e.getFax())
                .correioEletronico(e.getCorreioEletronico())
                .situacaoEspecial(e.getSituacaoEspecial())
                .dataSituacaoEspecial(CsvImportParse.formatData(e.getDataSituacaoEspecial()))
                .build();
    }
}
