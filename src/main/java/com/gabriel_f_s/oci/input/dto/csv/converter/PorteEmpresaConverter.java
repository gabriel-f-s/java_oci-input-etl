package com.gabriel_f_s.oci.input.dto.csv.converter;

import com.gabriel_f_s.oci.input.entity.enums.PorteEmpresa;
import com.opencsv.bean.AbstractBeanField;

public class PorteEmpresaConverter extends AbstractBeanField<PorteEmpresa, String> {
    @Override
    protected Object convert(String value) {
        for (PorteEmpresa s : PorteEmpresa.values()) {
            if (s.getValue() == Integer.parseInt(value)) return s;
        }
        return null;
    }
}
