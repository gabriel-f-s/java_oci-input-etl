package com.gabriel_f_s.oci.input.dto.csv.converter;

import com.gabriel_f_s.oci.input.entity.enums.IdentificadorMatrizFilial;
import com.gabriel_f_s.oci.input.entity.enums.SituacaoCadastral;
import com.opencsv.bean.AbstractBeanField;

public class SituacaoCadastralConverter extends AbstractBeanField<IdentificadorMatrizFilial, String> {
    @Override
    protected Object convert(String value) {
        for (SituacaoCadastral s : SituacaoCadastral.values()) {
            if (s.getValue() == Integer.parseInt(value)) return s;
        }
        return null;
    }
}
