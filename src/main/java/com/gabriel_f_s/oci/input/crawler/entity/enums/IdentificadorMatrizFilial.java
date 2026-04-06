package com.gabriel_f_s.oci.input.crawler.entity.enums;

import lombok.Getter;

@Getter
public enum IdentificadorMatrizFilial {
    MATRIZ(1),
    FILIAL(2);

    private final int value;

    IdentificadorMatrizFilial(int value) {
        this.value = value;
    }
}
