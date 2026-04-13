package com.gabriel_f_s.oci.input.dto.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SimplesCsvDTO {
    @CsvBindByPosition(position = 0)
    private String cnpjBasico;
    @CsvBindByPosition(position = 1)
    private String opcaoPeloSimples;
    @CsvBindByPosition(position = 2)
    private String dataOpcaoSimples;
    @CsvBindByPosition(position = 3)
    private String dataExclusaoSimples;
    @CsvBindByPosition(position = 4)
    private String opcaoPeloMei;
    @CsvBindByPosition(position = 5)
    private String dataOpcaoMei;
    @CsvBindByPosition(position = 6)
    private String dataExclusaoMei;
}
