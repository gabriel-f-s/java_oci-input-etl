package com.gabriel_f_s.oci.input.crawler.entity;

import com.gabriel_f_s.oci.input.crawler.entity.enums.IndicadorOpcao;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

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
    @Enumerated(value = EnumType.STRING)
    private IndicadorOpcao opcaoPeloSimples;
    @Column(name = "data_opcao_simples")
    private LocalDateTime dataOpcaoSimples;
    @Column(name = "data_exclusao_simples")
    private LocalDateTime dataExclusaoSimples;
    @Column(name = "opcao_pelo_mei")
    @Enumerated(value = EnumType.STRING)
    private IndicadorOpcao opcaoPeloMei;
    @Column(name = "data_opcao_mei")
    private LocalDateTime dataOpcaoMei;
    @Column(name = "data_exclusao_mei")
    private LocalDateTime dataExclusaoMei;
}
