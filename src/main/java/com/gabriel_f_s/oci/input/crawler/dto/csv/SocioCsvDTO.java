package com.gabriel_f_s.oci.input.crawler.dto.csv;

import com.gabriel_f_s.oci.input.crawler.entity.Pais;
import com.gabriel_f_s.oci.input.crawler.entity.Qualificacao;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvRecurse;
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
    @CsvRecurse
    @CsvBindByPosition(position = 4)
    private Qualificacao qualificacaoSocio;
    @CsvBindByPosition(position = 5)
    private String dataEntradaSociedade;
    @CsvRecurse
    @CsvBindByPosition(position = 6)
    private Pais pais;
    @CsvBindByPosition(position = 7)
    private String representanteLegal;
    @CsvBindByPosition(position = 8)
    private String nomeDoRepresentante;
    @CsvRecurse
    @CsvBindByPosition(position = 9)
    private Qualificacao qualificacaoRepresentanteLegal;
    @CsvBindByPosition(position = 10)
    private String faixaEtaria;
}
