package com.gabriel_f_s.oci.input.mapper;

import com.gabriel_f_s.oci.input.dto.csv.SimplesCsvDTO;
import com.gabriel_f_s.oci.input.entity.Simples;
import com.gabriel_f_s.oci.input.mapper.utils.ParsingUtils;
import org.springframework.stereotype.Component;

@Component
public class SimplesMapper implements CSVMapper<Simples, SimplesCsvDTO> {
    @Override
    public Simples mapTo(SimplesCsvDTO dto) {
        Simples s = new Simples();
        s.setCnpjBasico(dto.getCnpjBasico());
        s.setOpcaoPeloSimples(dto.getOpcaoPeloSimples());
        s.setDataOpcaoSimples(ParsingUtils.parseLocalDate(dto.getDataOpcaoSimples()));
        s.setDataExclusaoSimples(ParsingUtils.parseLocalDate(dto.getDataExclusaoSimples()));
        s.setOpcaoPeloMei(dto.getOpcaoPeloMei());
        s.setDataOpcaoMei(ParsingUtils.parseLocalDate(dto.getDataOpcaoMei()));
        s.setDataExclusaoMei(ParsingUtils.parseLocalDate(dto.getDataExclusaoMei()));
        return s;
    }

    @Override
    public SimplesCsvDTO unmapFrom(Simples s) {
        return SimplesCsvDTO.builder()
                .cnpjBasico(s.getCnpjBasico())
                .opcaoPeloSimples(s.getOpcaoPeloSimples())
                .dataOpcaoSimples(s.getDataOpcaoSimples().toString())
                .dataExclusaoSimples(s.getDataExclusaoSimples().toString())
                .opcaoPeloMei(s.getOpcaoPeloMei())
                .dataOpcaoMei(s.getDataOpcaoMei().toString())
                .dataExclusaoMei(s.getDataExclusaoMei().toString())
                .build();
    }
}
