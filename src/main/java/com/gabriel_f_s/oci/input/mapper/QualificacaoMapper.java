package com.gabriel_f_s.oci.input.mapper;

import com.gabriel_f_s.oci.input.dto.csv.QualificacaoCsvDTO;
import com.gabriel_f_s.oci.input.entity.Qualificacao;
import org.springframework.stereotype.Component;

@Component
public class QualificacaoMapper implements CSVMapper<Qualificacao, QualificacaoCsvDTO> {
    @Override
    public Qualificacao mapTo(QualificacaoCsvDTO qualificacaoCsvDTO) {
        Qualificacao qualificacao = new Qualificacao();
        qualificacao.setCodigo(qualificacaoCsvDTO.getCodigo());
        qualificacao.setDescricao(qualificacaoCsvDTO.getDescricao());
        return qualificacao;
    }

    @Override
    public QualificacaoCsvDTO unmapFrom(Qualificacao qualificacao) {
        return QualificacaoCsvDTO.builder()
                .codigo(qualificacao.getCodigo())
                .descricao(qualificacao.getDescricao())
                .build();
    }
}
