package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "motivos", uniqueConstraints = {
        @UniqueConstraint(columnNames = "codigo")
})
public class Motivo extends DomainEntity {}
