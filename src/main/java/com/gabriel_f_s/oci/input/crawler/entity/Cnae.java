package com.gabriel_f_s.oci.input.crawler.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cnaes", uniqueConstraints = {
    @UniqueConstraint(columnNames = "codigo")
})
public class Cnae {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String codigo;
    private String descricao;
}
