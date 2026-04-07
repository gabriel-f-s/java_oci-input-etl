package com.gabriel_f_s.oci.input.mapper;

import com.gabriel_f_s.oci.input.dto.csv.SimplesCsvDTO;
import com.gabriel_f_s.oci.input.entity.Simples;
import org.springframework.stereotype.Component;

@Component
public class SimplesMapper implements CSVMapper<Simples, SimplesCsvDTO> {
    @Override
    public Simples mapTo(SimplesCsvDTO dto) {
        Simples s = new Simples();
        s.setCnpjBasico(dto.getCnpjBasico());
//        s.setOpcaoPeloSimples(CsvImportParse.parseIndicadorOpcao(dto.getOpcaoPeloSimples()));
//        s.setDataOpcaoSimples(CsvImportParse.parseData(dto.getDataOpcaoSimples()));
//        s.setDataExclusaoSimples(CsvImportParse.parseData(dto.getDataExclusaoSimples()));
//        s.setOpcaoPeloMei(CsvImportParse.parseIndicadorOpcao(dto.getOpcaoPeloMei()));
//        s.setDataOpcaoMei(CsvImportParse.parseData(dto.getDataOpcaoMei()));
//        s.setDataExclusaoMei(CsvImportParse.parseData(dto.getDataExclusaoMei()));
        return s;
    }

    @Override
    public SimplesCsvDTO unmapFrom(Simples s) {
        return SimplesCsvDTO.builder()
                .cnpjBasico(s.getCnpjBasico())
//                .opcaoPeloSimples(CsvImportParse.formatIndicadorOpcao(s.getOpcaoPeloSimples()))
//                .dataOpcaoSimples(CsvImportParse.formatData(s.getDataOpcaoSimples()))
//                .dataExclusaoSimples(CsvImportParse.formatData(s.getDataExclusaoSimples()))
//                .opcaoPeloMei(CsvImportParse.formatIndicadorOpcao(s.getOpcaoPeloMei()))
//                .dataOpcaoMei(CsvImportParse.formatData(s.getDataOpcaoMei()))
//                .dataExclusaoMei(CsvImportParse.formatData(s.getDataExclusaoMei()))
                .build();
    }
}
