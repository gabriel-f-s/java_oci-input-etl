package com.gabriel_f_s.oci.input.crawler.service;

import com.gabriel_f_s.oci.input.crawler.entity.*;
import com.gabriel_f_s.oci.input.crawler.exception.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.function.BiConsumer;

@Service
public class DataIngestionService {

    private final int BATCH_SIZE = 5000;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private <T> void executeBatch(String sql, List<T> items, BiConsumer<PreparedStatement, T> binder) {
        jdbcTemplate.batchUpdate(sql, items, BATCH_SIZE, (ps, item) -> {
            binder.accept(ps, item);
        });
    }

    public void insertCnaes(List<Cnae> cnaes) {
        String sql = "INSERT INTO cnaes (codigo, descricao) VALUES (?, ?) ON CONFLICT (codigo) DO NOTHING";
        executeBatch(sql, cnaes, (ps, cnae) -> {
            try {
                ps.setString(1, cnae.getCodigo());
                ps.setString(2, cnae.getDescricao());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Cnaes': " + e);
            }
        });
    }

    public void insertMotivos(List<Motivo> motivos) {
        String sql = "INSERT INTO motivos (codigo, descricao) VALUES (?, ?) ON CONFLICT (codigo) DO NOTHING";
        executeBatch(sql, motivos, (ps, motivo) -> {
            try {
                ps.setString(1, motivo.getCodigo());
                ps.setString(2, motivo.getDescricao());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Motivos': " + e);
            }
        });
    }

    public void insertMunicipios(List<Municipio> municipios) {
        String sql = "INSERT INTO municipios (codigo, descricao) VALUES (?, ?) ON CONFLICT (codigo) DO NOTHING";
        executeBatch(sql, municipios, (ps, municipio) -> {
            try {
                ps.setString(1, municipio.getCodigo());
                ps.setString(2, municipio.getDescricao());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Municipios': " + e);
            }

        });
    }

    public void insertNaturezas(List<Natureza> naturezas) {
        String sql = "INSERT INTO naturezas (codigo, descricao) VALUES (?, ?) ON CONFLICT (codigo) DO NOTHING";
        executeBatch(sql, naturezas, (ps, natureza) -> {
            try {
                ps.setString(1, natureza.getCodigo());
                ps.setString(2, natureza.getDescricao());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Naturezas Jurídicas': " + e);
            }
        });
    }

    public void insertPaises(List<Pais> paises) {
        String sql = "INSERT INTO paises (codigo, descricao) VALUES (?, ?) ON CONFLICT (codigo) DO NOTHING";
        executeBatch(sql, paises, (ps, pais) -> {
            try {
                ps.setString(1, pais.getCodigo());
                ps.setString(2, pais.getDescricao());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Países': " + e);
            }
        });
    }

    public void insertQualificacoes(List<Qualificacao> qualificacoes) {
        String sql = "INSERT INTO qualificacoes (codigo, descricao) VALUES (?, ?) ON CONFLICT (codigo) DO NOTHING";
        executeBatch(sql, qualificacoes, (ps, qualificacao) -> {
            try {
                ps.setString(1, qualificacao.getCodigo());
                ps.setString(2, qualificacao.getDescricao());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Qualificações': " + e);
            }
        });
    }

    public void insertEmpresas(List<Empresa> empresas) {
        String sql = "INSERT INTO empresas " +
                "(cnpj_basico, razao_social, natureza_juridica_id, qualificacao_responsavel_id, capital_social, porte_empresa, ente_federativo_responsavel) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT (cnpj_basico) DO NOTHING";
        executeBatch(sql, empresas, (ps, empresa) -> {
            try {
                ps.setString(1, empresa.getCnpjBasico());
                ps.setString(2, empresa.getRazaoSocial());
                if (empresa.getNaturezaJuridica() != null && empresa.getNaturezaJuridica().getId() != null) {
                    ps.setLong(3, empresa.getNaturezaJuridica().getId());
                } else {
                    ps.setNull(3, java.sql.Types.BIGINT);
                }
                if (empresa.getQualificacaoResponsavel() != null && empresa.getQualificacaoResponsavel().getId() != null) {
                    ps.setLong(4, empresa.getQualificacaoResponsavel().getId());
                } else {
                    ps.setNull(4, java.sql.Types.BIGINT);
                }
                ps.setBigDecimal(5, empresa.getCapitalSocial());
                if (empresa.getPorteEmpresa() != null) {
                    ps.setString(6, empresa.getPorteEmpresa().name());
                } else {
                    ps.setNull(6, java.sql.Types.VARCHAR);
                }                ps.setString(7, empresa.getEnteFederativoResponsavel());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Empresas': " + e);
            }
        });
    }

    public void insertEstabelecimentos(List<Estabelecimento> estabelecimentos) {
        String sql = "INSERT INTO public.estabelecimentos" +
                "(cnpj_basico, cnpj_ordem, cnpj_dv, identificador_matriz_filial, nome_fantasia, situacao_cadastral, data_situacao_cadastral, motivo_situacao_cadastral_id, nome_cidade_exterior, pais_id, data_inicio_atividade, cnae_fiscal_principal_id, tipo_logradouro, logradouro, numero, complemento, bairro, cep, uf, municipio_id, ddd1, telefone1, ddd2, telefone2, ddd_fax, fax, correio_eletronico, situacao_especial, data_situacao_especial) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (cnpj_basico, cnpj_ordem, cnpj_dv) DO NOTHING";
        executeBatch(sql, estabelecimentos, (ps, estabelecimento) -> {
            try {
                ps.setString(1, estabelecimento.getCnpjBasico());
                ps.setString(2, estabelecimento.getCnpjOrdem());
                ps.setString(3, estabelecimento.getCnpjDv());
                ps.setInt(4, estabelecimento.getIdentificadorMatrizFilial().getValue());
                ps.setString(5, estabelecimento.getNomeFantasia());
                ps.setObject(6, estabelecimento.getSituacaoCadastral());
                ps.setObject(7, estabelecimento.getDataSituacaoEspecial());
                ps.setObject(8, estabelecimento.getMotivoSituacaoCadastral().getId() != null ? estabelecimento.getMotivoSituacaoCadastral().getId() : null);
                ps.setString(9, estabelecimento.getNomeCidadeExterior());
                ps.setObject(10, estabelecimento.getPais().getId() != null ? estabelecimento.getPais().getId() : null);
                ps.setObject(11, estabelecimento.getDataInicioAtividade());
                ps.setObject(12, estabelecimento.getCnaeFiscalPrincipal().getId() != null ? estabelecimento.getCnaeFiscalPrincipal().getId() : null);
                ps.setString(13, estabelecimento.getTipoLogradouro());
                ps.setString(14, estabelecimento.getLogradouro());
                ps.setString(15, estabelecimento.getNumero());
                ps.setString(16, estabelecimento.getComplemento());
                ps.setString(17, estabelecimento.getBairro());
                ps.setString(18, estabelecimento.getCep());
                ps.setString(19, estabelecimento.getUf());
                ps.setObject(20, estabelecimento.getMunicipio().getId() != null ? estabelecimento.getMunicipio().getId() : null);
                ps.setString(21, estabelecimento.getDdd1());
                ps.setString(22, estabelecimento.getTelefone1());
                ps.setString(23, estabelecimento.getDdd2());
                ps.setString(24, estabelecimento.getTelefone2());
                ps.setString(25, estabelecimento.getDddFax());
                ps.setString(26, estabelecimento.getFax());
                ps.setString(27, estabelecimento.getCorreioEletronico());
                ps.setString(28, estabelecimento.getSituacaoEspecial());
                ps.setObject(29, estabelecimento.getDataSituacaoEspecial());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Estabelecimentos': " + e);
            }
        });
    }

    public void insertSimples(List<Simples> simples) {
        String sql = "INSERT INTO simples " +
                "(cnpj_basico, opcao_pelo_simples, data_opcao_simples, data_exclusao_simples, opcao_pelo_mei, data_opcao_mei, data_exclusao_mei) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT (cnpj_basico) DO NOTHING";
        executeBatch(sql, simples, (ps, simples1) -> {
            try {
                ps.setString(1, simples1.getCnpjBasico());
                ps.setString(2, simples1.getOpcaoPeloSimples().name());
                ps.setObject(3, simples1.getDataOpcaoSimples());
                ps.setObject(4, simples1.getDataExclusaoSimples());
                ps.setString(5, simples1.getOpcaoPeloMei().name());
                ps.setObject(6, simples1.getDataOpcaoMei());
                ps.setObject(7, simples1.getDataExclusaoMei());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Simples': " + e);
            }
        });
    }

    public void insertSocios(List<Socio> socios) {
        String sql = "INSERT INTO public.socios " +
                "(cnpj_basico, identificador_de_socio, nome_socio, cnpj_cpf_do_socio, qualificacao_socio_id, data_entrada_sociedade, pais_id, representante_legal, nome_do_representante, qualificacao_representante_legal_id, faixa_etaria)\n" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        executeBatch(sql, socios, (ps, socio) -> {
            try {
                ps.setString(1, socio.getCnpjBasico());
                ps.setInt(2, socio.getIdentificadorDeSocio().getValue());
                ps.setString(3, socio.getNomeSocio());
                ps.setString(4, socio.getCnpjCpfDoSocio());
                ps.setObject(5, socio.getQualificacaoSocio().getId() != null ? socio.getQualificacaoSocio().getId() : null);
                ps.setObject(6, socio.getDataEntradaSociedade());
                ps.setObject(7, socio.getPais().getId() != null ? socio.getPais().getId() : null);
                ps.setString(8, socio.getRepresentanteLegal());
                ps.setString(9, socio.getNomeDoRepresentante());
                ps.setObject(10, socio.getQualificacaoRepresentanteLegal().getId() != null ? socio.getQualificacaoRepresentanteLegal().getId() : null);
                ps.setString(11, socio.getFaixaEtaria());

            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Estabelecimentos': " + e);
            }
        });
    }
}
