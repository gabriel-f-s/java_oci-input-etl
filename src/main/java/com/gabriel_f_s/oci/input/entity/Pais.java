package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "paises", uniqueConstraints = {
        @UniqueConstraint(columnNames = "codigo")
})
public class Pais extends DomainEntity {}
