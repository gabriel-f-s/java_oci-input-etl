package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "cnaes", uniqueConstraints = {
    @UniqueConstraint(columnNames = "codigo")
})
public class Cnae extends DomainEntity {}
