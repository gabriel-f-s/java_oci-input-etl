package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "qualificacoes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "codigo")
})
@RequiredArgsConstructor
public class Qualificacao extends DomainEntity {}
