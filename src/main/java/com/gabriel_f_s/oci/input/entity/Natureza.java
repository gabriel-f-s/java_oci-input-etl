package com.gabriel_f_s.oci.input.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "naturezas", uniqueConstraints = {
        @UniqueConstraint(columnNames = "codigo")
})
public class Natureza extends DomainEntity {}
