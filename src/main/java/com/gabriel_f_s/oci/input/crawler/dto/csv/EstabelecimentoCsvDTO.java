package com.gabriel_f_s.oci.input.crawler.dto.csv;

import com.gabriel_f_s.oci.input.crawler.entity.Cnae;
import com.gabriel_f_s.oci.input.crawler.entity.Motivo;
import com.gabriel_f_s.oci.input.crawler.entity.Municipio;
import com.gabriel_f_s.oci.input.crawler.entity.Pais;
import com.opencsv.bean.CsvBindByPosition;
import com.opencsv.bean.CsvRecurse;
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
    @CsvBindByPosition(position = 3)
    private String identificadorMatrizFilial;
    @CsvBindByPosition(position = 4)
    private String nomeFantasia;
    @CsvBindByPosition(position = 5)
    private String situacaoCadastral;
    @CsvBindByPosition(position = 6)
    private String dataSituacaoCadastral;
    @CsvBindByPosition(position = 7)
    private Motivo motivoSituacaoCadastral;
    @CsvBindByPosition(position = 8)
    private String nomeCidadeExterior;
    @CsvRecurse
    @CsvBindByPosition(position = 9)
    private Pais pais;
    @CsvBindByPosition(position = 10)
    private String dataInicioAtividade;
    @CsvRecurse
    @CsvBindByPosition(position = 11)
    private Cnae cnaeFiscalPrincipal;
    @CsvRecurse
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
    @CsvRecurse
    private Municipio municipio;
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
