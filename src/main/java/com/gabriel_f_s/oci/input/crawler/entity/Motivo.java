package com.gabriel_f_s.oci.input.crawler.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "motivos", uniqueConstraints = {
        @UniqueConstraint(columnNames = "codigo")
})
public class Motivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String descricao;
}
