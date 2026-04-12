package com.gabriel_f_s.oci.input.service;

import com.gabriel_f_s.oci.input.dto.LinkEstabelecimentoCnae;
import com.gabriel_f_s.oci.input.entity.*;
import com.gabriel_f_s.oci.input.exception.DatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.function.BiConsumer;

@Service
public class PersistenceService {

    private static final Logger logger = LoggerFactory.getLogger(PersistenceService.class);

    private final int BATCH_SIZE = 500;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * Performs the insertion of entities into the database.
     * @param sql
     *      SQL query.
     * @param items
     *      List of items to insert.
     * @param binder
     *      Binder with table definition logic
     */
    @Transactional
    protected <T> void executeBatch(String sql, List<T> items, BiConsumer<PreparedStatement, T> binder) {
        jdbcTemplate.batchUpdate(sql, items, BATCH_SIZE, binder::accept);
    }

    /**
     * Define the SQL query for insertion and apply the bind.
     * @param cnaes
     *      List of Cnae for insertion.
     */
    @Transactional
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

    /**
     * Define the SQL query for insertion and apply the bind.
     * @param motivos
     *      List of Motivo for insertion.
     */
    @Transactional
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

    /**
     * Define the SQL query for insertion and apply the bind.
     * @param municipios
     *      List of Municipio for insertion.
     */
    @Transactional
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

    /**
     * Define the SQL query for insertion and apply the bind.
     * @param naturezas
     *      List of Natureza for insertion.
     */
    @Transactional
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

    /**
     * Define the SQL query for insertion and apply the bind.
     * @param paises
     *      List of Pais for insertion.
     */
    @Transactional
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

    /**
     * Define the SQL query for insertion and apply the bind.
     * @param qualificacoes
     *      List of Qualificacao for insertion.
     */
    @Transactional
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

    /**
     * Define the SQL query for insertion and apply the bind.
     * @param empresas
     *      List of Empresa for insertion.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
                ps.setString(5, empresa.getCapitalSocial());
                if (empresa.getPorteEmpresa() != null) {
                    ps.setInt(6, empresa.getPorteEmpresa());
                } else {
                    ps.setNull(6, Types.SMALLINT);
                }
                ps.setString(7, empresa.getEnteFederativoResponsavel());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Empresas': " + e);
            }
        });
    }

    /**
     * Define the SQL query for insertion and apply the bind.
     * @param estabelecimentos
     *      List of Estabelecimento for insertion.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertEstabelecimentos(List<Estabelecimento> estabelecimentos) {
        String sql = "INSERT INTO estabelecimentos" +
                "(cnpj_basico, cnpj_ordem, cnpj_dv, identificador_matriz_filial, nome_fantasia, situacao_cadastral, data_situacao_cadastral, motivo_situacao_cadastral_id, nome_cidade_exterior, pais_id, data_inicio_atividade, cnae_fiscal_principal_id, tipo_logradouro, logradouro, numero, complemento, bairro, cep, uf, municipio_id, ddd1, telefone1, ddd2, telefone2, ddd_fax, fax, correio_eletronico, situacao_especial, data_situacao_especial) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (cnpj_basico, cnpj_ordem, cnpj_dv) DO NOTHING";
        executeBatch(sql, estabelecimentos, (ps, estabelecimento) -> {
            try {
                ps.setString(1, estabelecimento.getCnpjBasico());
                ps.setString(2, estabelecimento.getCnpjOrdem());
                ps.setString(3, estabelecimento.getCnpjDv());
                if (estabelecimento.getIdentificadorMatrizFilial() != null) {
                    ps.setInt(4, estabelecimento.getIdentificadorMatrizFilial());
                } else {
                    ps.setNull(4, Types.SMALLINT);
                }
                ps.setString(5, estabelecimento.getNomeFantasia());
                if (estabelecimento.getSituacaoCadastral() != null) {
                    ps.setInt(6, estabelecimento.getSituacaoCadastral());
                } else {
                    ps.setNull(6, java.sql.Types.SMALLINT);
                }
                ps.setObject(7, estabelecimento.getDataSituacaoCadastral());
                if (estabelecimento.getMotivoSituacaoCadastral() != null && estabelecimento.getMotivoSituacaoCadastral().getId() != null) {
                    ps.setLong(8, estabelecimento.getMotivoSituacaoCadastral().getId());
                } else {
                    ps.setNull(8, java.sql.Types.BIGINT);
                }
                ps.setString(9, estabelecimento.getNomeCidadeExterior());
                if (estabelecimento.getPais() != null && estabelecimento.getPais().getId() != null) {
                    ps.setLong(10, estabelecimento.getPais().getId());
                } else {
                    ps.setNull(10, java.sql.Types.BIGINT);
                }
                ps.setObject(11, estabelecimento.getDataInicioAtividade());
                if (estabelecimento.getCnaeFiscalPrincipal() != null && estabelecimento.getCnaeFiscalPrincipal().getId() != null) {
                    ps.setLong(12, estabelecimento.getCnaeFiscalPrincipal().getId());
                } else {
                    ps.setNull(12, java.sql.Types.BIGINT);
                }
                ps.setString(13, estabelecimento.getTipoLogradouro());
                ps.setString(14, estabelecimento.getLogradouro());
                ps.setString(15, estabelecimento.getNumero());
                ps.setString(16, estabelecimento.getComplemento());
                ps.setString(17, estabelecimento.getBairro());
                ps.setString(18, estabelecimento.getCep());
                ps.setString(19, estabelecimento.getUf());
                if (estabelecimento.getMunicipio() != null && estabelecimento.getMunicipio().getId() != null) {
                    ps.setLong(20, estabelecimento.getMunicipio().getId());
                } else {
                    ps.setNull(20, java.sql.Types.BIGINT);
                }
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

    /**
     * Define the SQL query for insertion and apply the bind.
     * @param estabelecimentos
     *      List of Estabelecimento for insertion in estabelecimento_cnae table.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    protected void insertEstabelecimentosCnaes(List<Estabelecimento> estabelecimentos) {
        String sql = "INSERT INTO estabelecimento_cnae (estabelecimento_id, cnae_id) " +
                "SELECT e.id, ? " +
                "FROM estabelecimentos e " +
                "WHERE e.cnpj_basico = ? AND e.cnpj_ordem = ? AND e.cnpj_dv = ? " +
                "ON CONFLICT DO NOTHING";
        List<LinkEstabelecimentoCnae> links = estabelecimentos.stream()
                .flatMap(e -> e.getCnaeFiscalSecundaria().stream()
                        .map(cnae -> new LinkEstabelecimentoCnae(
                                e.getCnpjBasico(),
                                e.getCnpjOrdem(),
                                e.getCnpjDv(),
                                cnae.getId()
                        )))
                .toList();
        if (links.isEmpty()) return;
        executeBatch(sql, links, (ps, link) -> {
            try {
                ps.setLong(1, link.cnaeId());
                ps.setString(2, link.cnpjBasico());
                ps.setString(3, link.cnpjOrdem());
                ps.setString(4, link.cnpjDv());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Estabelecimentos - Cnaes': " + e);
            }
        } );
    }

    /**
     * Define the SQL query for insertion and apply the bind.
     * @param simples
     *      List of Simples for insertion.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertSimples(List<Simples> simples) {
        String sql = "INSERT INTO simples " +
                "(cnpj_basico, opcao_pelo_simples, data_opcao_simples, data_exclusao_simples, opcao_pelo_mei, data_opcao_mei, data_exclusao_mei) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) ON CONFLICT (cnpj_basico) DO NOTHING";
        executeBatch(sql, simples, (ps, simples1) -> {
            try {
                ps.setString(1, simples1.getCnpjBasico());
                if (simples1.getOpcaoPeloSimples() != null) {
                    ps.setString(2, simples1.getOpcaoPeloSimples());
                } else {
                    ps.setString(2, "OUTROS");
                }
                ps.setObject(3, simples1.getDataOpcaoSimples());
                ps.setObject(4, simples1.getDataExclusaoSimples());
                if (simples1.getOpcaoPeloMei() != null) {
                    ps.setString(5, simples1.getOpcaoPeloMei());
                } else {
                    ps.setString(5, "OUTROS");
                }
                ps.setObject(6, simples1.getDataOpcaoMei());
                ps.setObject(7, simples1.getDataExclusaoMei());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Simples': " + e);
            }
        });
    }

    /**
     * Define the SQL query for insertion and apply the bind.
     * @param socios
     *      List of Socio for insertion.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void insertSocios(List<Socio> socios) {
        String sql = "INSERT INTO socios" +
                "(cnpj_basico, identificador_de_socio, nome_socio, cnpj_cpf_do_socio, qualificacao_socio_id, data_entrada_sociedade, pais_id, representante_legal, nome_do_representante, qualificacao_representante_legal_id, faixa_etaria) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON CONFLICT (cnpj_basico) DO NOTHING";
        executeBatch(sql, socios, (ps, socio) -> {
            try {
                ps.setString(1, socio.getCnpjBasico());
                ps.setInt(2, socio.getIdentificadorDeSocio());
                ps.setString(3, socio.getNomeSocio());
                ps.setString(4, socio.getCnpjCpfDoSocio());
                if (socio.getQualificacaoSocio() != null && socio.getQualificacaoSocio().getId() != null) {
                    ps.setObject(5, socio.getQualificacaoSocio().getId());
                } else {
                    ps.setNull(5, java.sql.Types.BIGINT);
                }
                ps.setObject(6, socio.getDataEntradaSociedade());
                if (socio.getPais() != null && socio.getPais().getId() != null) {
                    ps.setObject(7, socio.getPais().getId());
                } else {
                    ps.setNull(7, java.sql.Types.BIGINT);
                }
                ps.setString(8, socio.getRepresentanteLegal());
                ps.setString(9, socio.getNomeDoRepresentante());
                if (socio.getQualificacaoRepresentanteLegal() != null && socio.getQualificacaoRepresentanteLegal().getId() != null) {
                    ps.setObject(10, socio.getQualificacaoRepresentanteLegal().getId());
                } else {
                    ps.setNull(10, java.sql.Types.BIGINT);
                }
                ps.setInt(11, socio.getFaixaEtaria());
            } catch (SQLException e) {
                throw new DatabaseException("Error mapping 'Socios': " + e);
            }
        });
    }
}
