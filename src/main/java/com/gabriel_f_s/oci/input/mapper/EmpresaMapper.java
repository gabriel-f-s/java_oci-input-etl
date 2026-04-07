package com.gabriel_f_s.oci.input.mapper;

import com.gabriel_f_s.oci.input.dto.csv.EmpresaCsvDTO;
import com.gabriel_f_s.oci.input.entity.Empresa;
import com.gabriel_f_s.oci.input.entity.Natureza;
import com.gabriel_f_s.oci.input.entity.Qualificacao;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmpresaMapper implements CSVMapper<Empresa, EmpresaCsvDTO> {
    @Override
    public Empresa mapTo(EmpresaCsvDTO dto) {
        return null;
    }

    public Empresa mapTo(EmpresaCsvDTO dto, Map<String, Long> naturezasMap, Map<String, Long> qualificacoesMap) {
        Empresa empresa = new Empresa();
        empresa.setCnpjBasico(dto.getCnpjBasico());
        empresa.setRazaoSocial(dto.getRazaoSocial());
        Long naturezaId = naturezasMap.get(dto.getNaturezaJuridica());
        if (naturezaId != null) {
            Natureza n = new Natureza();
            n.setId(naturezaId);
            empresa.setNaturezaJuridica(n);
        }
        Long qualid = qualificacoesMap.get(dto.getQualificacaoResponsavel());
        if (qualid != null) {
            Qualificacao q = new Qualificacao();
            q.setId(qualid);
            empresa.setQualificacaoResponsavel(q);
        }
        empresa.setCapitalSocial(dto.getCapitalSocial());
        empresa.setPorteEmpresa(dto.getPorteEmpresa());
        empresa.setEnteFederativoResponsavel(dto.getEnteFederativoResponsavel());
        return empresa;
    }

    @Override
    public EmpresaCsvDTO unmapFrom(Empresa empresa) {
        return EmpresaCsvDTO.builder()
                .cnpjBasico(empresa.getCnpjBasico())
                .razaoSocial(empresa.getRazaoSocial())
                .naturezaJuridica(empresa.getNaturezaJuridica().getCodigo())
                .qualificacaoResponsavel(empresa.getQualificacaoResponsavel().getCodigo())
                .capitalSocial(empresa.getCapitalSocial())
                .porteEmpresa(empresa.getPorteEmpresa())
                .enteFederativoResponsavel(empresa.getEnteFederativoResponsavel())
                .build();
    }
}
