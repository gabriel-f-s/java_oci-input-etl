package com.gabriel_f_s.oci.input.crawler.mapper;

import com.gabriel_f_s.oci.input.crawler.dto.csv.EmpresaCsvDTO;
import com.gabriel_f_s.oci.input.crawler.entity.Empresa;
import com.gabriel_f_s.oci.input.crawler.entity.Natureza;
import com.gabriel_f_s.oci.input.crawler.entity.Qualificacao;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class EmpresaMapper implements CSVMapper<Empresa, EmpresaCsvDTO> {
    @Override
    public Empresa mapTo(EmpresaCsvDTO empresaCsvDTO) {
        return null;
    }

    public Empresa mapTo(EmpresaCsvDTO empresaCsvDTO, Map<String, Long> naturezasMap, Map<String, Long> qualificacoesMap) {
        Empresa empresa = new Empresa();
        empresa.setCnpjBasico(empresaCsvDTO.getCnpjBasico());
        empresa.setRazaoSocial(empresaCsvDTO.getRazaoSocial());
        Long naturezaId = naturezasMap.get(empresaCsvDTO.getNaturezaJuridica());
        if (naturezaId != null) {
            Natureza n = new Natureza();
            n.setId(naturezaId);
            empresa.setNaturezaJuridica(n);
        }
        Long qualid = qualificacoesMap.get(empresaCsvDTO.getQualificacaoResponsavel());
        if (qualid != null) {
            Qualificacao q = new Qualificacao();
            q.setId(qualid);
            empresa.setQualificacaoResponsavel(q);
        }
        empresa.setCapitalSocial(empresaCsvDTO.getCapitalSocial());
        empresa.setPorteEmpresa(empresaCsvDTO.getPorteEmpresa());
        empresa.setEnteFederativoResponsavel(empresaCsvDTO.getEnteFederativoResponsavel());
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
