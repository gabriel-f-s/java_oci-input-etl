package com.gabriel_f_s.oci.input.dto;

public record LinkEstabelecimentoCnae(
        String cnpjBasico,
        String cnpjOrdem,
        String cnpjDv,
        Long cnaeId
) {}
