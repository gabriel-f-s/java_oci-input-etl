package com.gabriel_f_s.oci.input.mapper;

import com.gabriel_f_s.oci.input.dto.csv.PaisCsvDTO;
import com.gabriel_f_s.oci.input.entity.Pais;
import org.springframework.stereotype.Component;

@Component
public class PaisMapper implements CSVMapper<Pais, PaisCsvDTO> {
    @Override
    public Pais mapTo(PaisCsvDTO paisCsvDTO) {
        Pais pais = new Pais();
        pais.setCodigo(paisCsvDTO.getCodigo());
        pais.setDescricao(paisCsvDTO.getDescricao());
        return pais;
    }

    @Override
    public PaisCsvDTO unmapFrom(Pais pais) {
        return PaisCsvDTO.builder()
                .codigo(pais.getCodigo())
                .descricao(pais.getDescricao())
                .build();
    }
}
