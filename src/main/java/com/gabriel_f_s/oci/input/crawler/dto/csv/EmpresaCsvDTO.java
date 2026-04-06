package com.gabriel_f_s.oci.input.crawler.dto.csv;

import com.gabriel_f_s.oci.input.crawler.dto.csv.converter.PorteEmpresaConverter;
import com.gabriel_f_s.oci.input.crawler.entity.Natureza;
import com.gabriel_f_s.oci.input.crawler.entity.Qualificacao;
import com.gabriel_f_s.oci.input.crawler.entity.enums.PorteEmpresa;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvCustomBindByPosition;
import com.opencsv.bean.CsvRecurse;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaCsvDTO {
    @CsvBindByPosition(position = 0)
    private String cnpjBasico;
    @CsvBindByPosition(position = 1)
    private String razaoSocial;
    @CsvBindByPosition(position = 2)
    private String naturezaJuridica;
    @CsvBindByPosition(position = 3)
    private String qualificacaoResponsavel;
    @CsvBindByPosition(position = 4, locale = "pt-BR")
    private BigDecimal capitalSocial;
    @CsvCustomBindByPosition(converter = PorteEmpresaConverter.class, position = 5)
    private PorteEmpresa porteEmpresa;
    @CsvBindByPosition(position = 6)
    private String enteFederativoResponsavel;
}
