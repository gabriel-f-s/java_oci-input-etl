package com.gabriel_f_s.oci.input.dto.csv;

import com.opencsv.bean.CsvBindByPosition;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MotivoCsvDTO {
    @CsvBindByPosition(position = 0)
    private String codigo;
    @CsvBindByPosition(position = 1)
    private String descricao;
}
