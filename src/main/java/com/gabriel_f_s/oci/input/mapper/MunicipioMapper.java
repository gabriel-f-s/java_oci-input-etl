package com.gabriel_f_s.oci.input.mapper;

import com.gabriel_f_s.oci.input.dto.csv.MunicipioCsvDTO;
import com.gabriel_f_s.oci.input.entity.Municipio;
import org.springframework.stereotype.Component;

@Component
public class MunicipioMapper implements CSVMapper<Municipio, MunicipioCsvDTO> {
    @Override
    public Municipio mapTo(MunicipioCsvDTO municipioCsvDTO) {
        Municipio municipio = new Municipio();
        municipio.setCodigo(municipioCsvDTO.getCodigo());
        municipio.setDescricao(municipioCsvDTO.getDescricao());
        return municipio;
    }

    @Override
    public MunicipioCsvDTO unmapFrom(Municipio municipio) {
        return MunicipioCsvDTO.builder()
                .codigo(municipio.getCodigo())
                .descricao(municipio.getDescricao())
                .build();
    }
}
