package com.gabriel_f_s.oci.input.mapper;

import com.gabriel_f_s.oci.input.dto.csv.NaturezaCsvDTO;
import com.gabriel_f_s.oci.input.entity.Natureza;
import org.springframework.stereotype.Component;

@Component
public class NaturezaMapper implements CSVMapper<Natureza, NaturezaCsvDTO> {
    @Override
    public Natureza mapTo(NaturezaCsvDTO naturezaCsvDTO) {
        Natureza natureza = new Natureza();
        natureza.setCodigo(naturezaCsvDTO.getCodigo());
        natureza.setDescricao(naturezaCsvDTO.getDescricao());
        return natureza;
    }

    @Override
    public NaturezaCsvDTO unmapFrom(Natureza natureza) {
        return NaturezaCsvDTO.builder()
                .codigo(natureza.getCodigo())
                .descricao(natureza.getDescricao())
                .build();
    }
}
