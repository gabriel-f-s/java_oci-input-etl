package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "simples", uniqueConstraints = {
        @UniqueConstraint(columnNames = "cnpj_basico")
})
public class Simples {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "cnpj_basico")
    private String cnpjBasico;
    @Column(name = "opcao_pelo_simples")
    private String opcaoPeloSimples;
    @Column(name = "data_opcao_simples")
    private LocalDate dataOpcaoSimples;
    @Column(name = "data_exclusao_simples")
    private LocalDate dataExclusaoSimples;
    @Column(name = "opcao_pelo_mei")
    private String opcaoPeloMei;
    @Column(name = "data_opcao_mei")
    private LocalDate dataOpcaoMei;
    @Column(name = "data_exclusao_mei")
    private LocalDate dataExclusaoMei;
}
