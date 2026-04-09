package com.gabriel_f_s.oci.input.dto.csv;

import com.opencsv.bean.CsvBindByPosition;
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
    @CsvBindByPosition(position = 5)
    private String porteEmpresa;
    @CsvBindByPosition(position = 6)
    private String enteFederativoResponsavel;
}
