package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "qualificacoes", uniqueConstraints = {
        @UniqueConstraint(columnNames = "codigo")
})
public class Qualificacao extends DomainEntity {}
