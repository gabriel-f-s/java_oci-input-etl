package com.gabriel_f_s.oci.input.crawler.mapper;

import com.gabriel_f_s.oci.input.crawler.dto.csv.CnaeCsvDTO;
import com.gabriel_f_s.oci.input.crawler.entity.Cnae;
import org.springframework.stereotype.Component;

@Component
public class CnaeMapper implements CSVMapper<Cnae, CnaeCsvDTO> {
    @Override
    public Cnae mapTo(CnaeCsvDTO cnaeCsvDTO) {
         Cnae cnae = new Cnae();
         cnae.setCodigo(cnaeCsvDTO.getCodigo());
         cnae.setDescricao(cnaeCsvDTO.getDescricao());
         return cnae;
    }

    @Override
    public CnaeCsvDTO unmapFrom(Cnae cnae) {
        return CnaeCsvDTO.builder()
                .codigo(cnae.getCodigo())
                .descricao(cnae.getDescricao())
                .build();
    }
}
