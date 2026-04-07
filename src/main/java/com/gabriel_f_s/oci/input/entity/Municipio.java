package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "municipios", uniqueConstraints = {
        @UniqueConstraint(columnNames = "codigo")
})
public class Municipio extends DomainEntity {}
