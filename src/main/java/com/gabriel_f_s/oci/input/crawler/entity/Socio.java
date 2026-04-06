package com.gabriel_f_s.oci.input.crawler.entity;

import com.gabriel_f_s.oci.input.crawler.entity.enums.IdentificadorSocio;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private IdentificadorSocio identificadorDeSocio;
    @Column(name = "nome_socio")
    private String nomeSocio;
    @Column(name = "cnpj_cpf_do_socio")
    private String cnpjCpfDoSocio;
    @ManyToOne
    @JoinColumn(name = "qualificacao_socio_id")
    private Qualificacao qualificacaoSocio;
    @Column(name = "data_entrada_sociedade")
    private LocalDateTime dataEntradaSociedade;
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
    private String faixaEtaria;
}
