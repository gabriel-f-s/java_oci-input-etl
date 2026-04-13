package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "municipios", uniqueConstraints = {
        @UniqueConstraint(columnNames = "codigo")
})
@RequiredArgsConstructor
public class Municipio extends DomainEntity {}
