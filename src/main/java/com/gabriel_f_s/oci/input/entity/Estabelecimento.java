package com.gabriel_f_s.oci.input.entity;

import com.gabriel_f_s.oci.input.entity.enums.IdentificadorMatrizFilial;
import com.gabriel_f_s.oci.input.entity.enums.SituacaoCadastral;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
@Table(name = "estabelecimentos", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cnpj_basico")
})
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cnpj_basico")
    private String cnpjBasico;
    @Column(name = "cnpj_ordem")
    private String cnpjOrdem;
    @Column(name = "cnpj_dv")
    private String cnpjDv;
    @Column(name = "identificador_matriz_filial")
    private IdentificadorMatrizFilial identificadorMatrizFilial;
    @Column(name = "nome_fantasia")
    private String nomeFantasia;
    @Column(name = "situacao_cadastral")
    @Enumerated(value = EnumType.STRING)
    private SituacaoCadastral situacaoCadastral;
    @Column(name = "data_situacao_cadastral")
    private LocalDate dataSituacaoCadastral;
    @ManyToOne
    @JoinColumn(name = "motivo_situacao_cadastral_id")
    private Motivo motivoSituacaoCadastral;
    @Column(name = "nome_cidade_exterior")
    private String nomeCidadeExterior;
    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Pais pais;
    @Column(name = "data_inicio_atividade")
    private LocalDate dataInicioAtividade;
    @ManyToOne
    @JoinColumn(name = "cnae_fiscal_principal_id")
    private Cnae cnaeFiscalPrincipal;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "estabelecimento_cnae",
            joinColumns = @JoinColumn(name = "estabelecimento_id"),
            inverseJoinColumns = @JoinColumn(name = "cnae_id")
    )
    private List<Cnae> cnaeFiscalSecundaria = new ArrayList<>();
    @Column(name = "tipo_logradouro")
    private String tipoLogradouro;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cep;
    private String uf;
    @ManyToOne
    @JoinColumn(name = "municipio_id")
    private Municipio municipio;
    private String ddd1;
    private String telefone1;
    private String ddd2;
    private String telefone2;
    @Column(name = "ddd_fax")
    private String dddFax;
    private String fax;
    @Column(name = "correio_eletronico")
    private String correioEletronico;
    @Column(name = "situacao_especial")
    private String situacaoEspecial;
    @Column(name = "data_situacao_especial")
    private LocalDate dataSituacaoEspecial;
}
