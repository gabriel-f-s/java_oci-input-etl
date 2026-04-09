package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "cnaes", uniqueConstraints = {
    @UniqueConstraint(columnNames = "codigo")
})
@AllArgsConstructor
public class Cnae extends DomainEntity {}
