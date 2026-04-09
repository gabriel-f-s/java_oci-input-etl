package com.gabriel_f_s.oci.input.dto.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocioCsvDTO {
    @CsvBindByPosition(position = 0)
    private String cnpjBasico;
    @CsvBindByPosition(position = 1)
    private String identificadorDeSocio;
    @CsvBindByPosition(position = 2)
    private String nomeSocio;
    @CsvBindByPosition(position = 3)
    private String cnpjCpfDoSocio;
    @CsvBindByPosition(position = 4)
    private String qualificacaoSocio;
    @CsvBindByPosition(position = 5)
    private String dataEntradaSociedade;
    @CsvBindByPosition(position = 6)
    private String pais;
    @CsvBindByPosition(position = 7)
    private String representanteLegal;
    @CsvBindByPosition(position = 8)
    private String nomeDoRepresentante;
    @CsvBindByPosition(position = 9)
    private String qualificacaoRepresentanteLegal;
    @CsvBindByPosition(position = 10)
    private String faixaEtaria;
}
