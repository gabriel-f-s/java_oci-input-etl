package com.gabriel_f_s.oci.input.crawler.mapper;

import com.gabriel_f_s.oci.input.crawler.dto.csv.MotivoCsvDTO;
import com.gabriel_f_s.oci.input.crawler.entity.Motivo;
import org.springframework.stereotype.Component;

@Component
public class MotivoMapper implements CSVMapper<Motivo, MotivoCsvDTO> {
    @Override
    public Motivo mapTo(MotivoCsvDTO motivoCsvDTO) {
        Motivo motivo = new Motivo();
        motivo.setCodigo(motivoCsvDTO.getCodigo());
        motivo.setDescricao(motivoCsvDTO.getDescricao());
        return motivo;
    }

    @Override
    public MotivoCsvDTO unmapFrom(Motivo motivo) {
        return MotivoCsvDTO.builder()
                .codigo(motivo.getCodigo())
                .descricao(motivo.getDescricao())
                .build();
    }
}
