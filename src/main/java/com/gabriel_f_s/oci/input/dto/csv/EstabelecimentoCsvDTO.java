package com.gabriel_f_s.oci.input.dto.csv;

import com.gabriel_f_s.oci.input.dto.csv.converter.IdentificadorMatrizFilialConverter;
import com.gabriel_f_s.oci.input.dto.csv.converter.SituacaoCadastralConverter;
import com.gabriel_f_s.oci.input.entity.enums.IdentificadorMatrizFilial;
import com.gabriel_f_s.oci.input.entity.enums.SituacaoCadastral;
import com.opencsv.bean.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstabelecimentoCsvDTO {
    @CsvBindByPosition(position = 0)
    private String cnpjBasico;
    @CsvBindByPosition(position = 1)
    private String cnpjOrdem;
    @CsvBindByPosition(position = 2)
    private String cnpjDv;
    @CsvCustomBindByPosition(position = 3, converter = IdentificadorMatrizFilialConverter.class)
    private IdentificadorMatrizFilial identificadorMatrizFilial;
    @CsvBindByPosition(position = 4)
    private String nomeFantasia;
    @CsvCustomBindByPosition(position = 5, converter = SituacaoCadastralConverter.class)
    private SituacaoCadastral situacaoCadastral;
    @CsvBindByPosition(position = 6)
    private String dataSituacaoCadastral;
    @CsvBindByPosition(position = 7)
    private String motivoSituacaoCadastral;
    @CsvBindByPosition(position = 8)
    private String nomeCidadeExterior;
    @CsvBindByPosition(position = 9)
    private String pais;
    @CsvBindByPosition(position = 10)
    private String dataInicioAtividade;
    @CsvBindByPosition(position = 11)
    private String cnaeFiscalPrincipal;
    @CsvBindByPosition(position = 12)
    private String cnaeFiscalSecundaria;
    @CsvBindByPosition(position = 13)
    private String tipoLogradouro;
    @CsvBindByPosition(position = 14)
    private String logradouro;
    @CsvBindByPosition(position = 15)
    private String numero;
    @CsvBindByPosition(position = 16)
    private String complemento;
    @CsvBindByPosition(position = 17)
    private String bairro;
    @CsvBindByPosition(position = 18)
    private String cep;
    @CsvBindByPosition(position = 19)
    private String uf;
    @CsvBindByPosition(position = 20)
    private String municipio;
    @CsvBindByPosition(position = 21)
    private String ddd1;
    @CsvBindByPosition(position = 22)
    private String telefone1;
    @CsvBindByPosition(position = 23)
    private String ddd2;
    @CsvBindByPosition(position = 24)
    private String telefone2;
    @CsvBindByPosition(position = 25)
    private String dddFax;
    @CsvBindByPosition(position = 26)
    private String fax;
    @CsvBindByPosition(position = 27)
    private String correioEletronico;
    @CsvBindByPosition(position = 28)
    private String situacaoEspecial;
    @CsvBindByPosition(position = 29)
    private String dataSituacaoEspecial;
}
