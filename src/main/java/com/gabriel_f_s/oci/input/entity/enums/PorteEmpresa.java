package com.gabriel_f_s.oci.input.entity.enums;

import lombok.Getter;

@Getter
public enum PorteEmpresa {
    NAO_INFORMADO(0),
    MICRO_EMPRESA(1),
    EMPRESA_DE_PEQUENO_PORTE(3),
    DEMAIS(5);

    private final int value;

    PorteEmpresa(int value) {
        this.value = value;
    }
}
