package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "empresas", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cnpj_basico")
})
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cnpj_basico")
    private String cnpjBasico;
    @Column(name = "razao_social")
    private String razaoSocial;
    @ManyToOne
    @JoinColumn(name = "natureza_juridica_id")
    private Natureza naturezaJuridica;
    @ManyToOne
    @JoinColumn(name = "qualificacao_responsavel_id")
    private Qualificacao qualificacaoResponsavel;
    @Column(name = "capital_social")
    private BigDecimal capitalSocial;
    @Column(name = "porte_empresa")
    private Integer porteEmpresa;
    @Column(name = "ente_federativo_responsavel")
    private String enteFederativoResponsavel;
}
