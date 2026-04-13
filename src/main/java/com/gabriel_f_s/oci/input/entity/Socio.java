package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "socios", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cnpj_basico")
})
public class Socio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cnpj_basico")
    private String cnpjBasico;
    @Column(name = "identificador_de_socio")
    private Integer identificadorDeSocio;
    @Column(name = "nome_socio")
    private String nomeSocio;
    @Column(name = "cnpj_cpf_do_socio")
    private String cnpjCpfDoSocio;
    @ManyToOne
    @JoinColumn(name = "qualificacao_socio_id")
    private Qualificacao qualificacaoSocio;
    @Column(name = "data_entrada_sociedade")
    private LocalDate dataEntradaSociedade;
    @ManyToOne
    @JoinColumn(name = "pais_id")
    private Pais pais;
    @Column(name = "representante_legal")
    private String representanteLegal;
    @Column(name = "nome_do_representante")
    private String nomeDoRepresentante;
    @ManyToOne
    @JoinColumn(name = "qualificacao_representante_legal_id")
    private Qualificacao qualificacaoRepresentanteLegal;
    @Column(name = "faixa_etaria")
    private Integer faixaEtaria;
}
