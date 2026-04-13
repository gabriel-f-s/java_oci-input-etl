package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "motivos", uniqueConstraints = {
        @UniqueConstraint(columnNames = "codigo")
})
@RequiredArgsConstructor
public class Motivo extends DomainEntity {}
