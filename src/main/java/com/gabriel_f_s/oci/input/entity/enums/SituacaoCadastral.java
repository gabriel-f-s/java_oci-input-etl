package com.gabriel_f_s.oci.input.entity.enums;

import lombok.Getter;

@Getter
public enum SituacaoCadastral {
    NULA(1),
    ATIVA(2),
    SUSPENSA(3),
    INAPTA(4),
    BAIXADA(8);

    private final int value;

    SituacaoCadastral(int value) {
        this.value = value;
    }
}
