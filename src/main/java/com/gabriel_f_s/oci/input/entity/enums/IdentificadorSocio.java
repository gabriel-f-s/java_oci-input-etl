package com.gabriel_f_s.oci.input.entity.enums;

import lombok.Getter;

@Getter
public enum IdentificadorSocio {
    PESSOA_JURIDICA(1),
    PESSOA_FISICA(2),
    ESTRANGEIRO(3);

    private final int value;

    IdentificadorSocio(int value) {
        this.value = value;
    }
}
