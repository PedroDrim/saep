/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.controller;

import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.ResolucaoRepository;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceResolucaoDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceTipoDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.service.BasicResolucaoDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.service.BasicTipoDAO;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Classe responsável por implementar a interface {@code ResolucaoRepository}.
 *
 * @author Pedro Henrique Silva Farias
 * @see ResolucaoRepository
 */
public class BasicResolucaoRepository implements ResolucaoRepository {

    /**
     * Instancia de {@code InterfaceTipoDAO} para manipular os Tipos.
     *
     * @see Tipo
     * @see InderfaceTipoDAO
     */
    private InterfaceTipoDAO tipoDAO;

    /**
     * Instancia de {@code InterfaceResolucaoDAO} para manipular as Resoluções.
     *
     * @see Resolucao
     * @see InderfaceResolucaoDAO
     */
    private InterfaceResolucaoDAO resolucaoDAO;

    /**
     * Construtor responsável por inicializar a interfaces:
     * {@code InterfaceResolucaoDAO} e {@code InterfaceTipoDAO}
     *
     * @param mongodatabase Instancia referente ao banco de dados mongo
     * utilizado.
     * @see InterfaceResolucaoDAO
     * @see InterfaceTipoDAO
     * @see ResolucaoRepository
     * @see Tipo
     * @see Resolucao
     */
    public BasicResolucaoRepository(MongoDatabase mongodatabase) {
        this.tipoDAO = new BasicTipoDAO(mongodatabase, "Tipo");
        this.resolucaoDAO = new BasicResolucaoDAO(mongodatabase, "Resolucao");
    }

    /**
     * A descrição do método está na interface {@code ResolucaoRepository}
     *
     * @see ResolucaoRepository
     */
    @Override
    public Resolucao byId(String id) {
        Resolucao resolucao;

        try {
            resolucao = this.resolucaoDAO.findResolucaoById(id);
        } catch (IdentificadorDesconhecido e) {
            resolucao = null;
        }
        return (resolucao);
    }

    /**
     * A descrição do método está na interface {@code ResolucaoRepository}
     *
     * @see ResolucaoRepository
     */
    @Override
    public String persiste(Resolucao resolucao) {
        String id;

        try {
            this.resolucaoDAO.insertResolucao(resolucao);
            id = resolucao.getId();
        } catch (IdentificadorExistente e) {
            id = null;
        }
        return (id);
    }

    /**
     * A descrição do método está na interface {@code ResolucaoRepository}
     *
     * @see ResolucaoRepository
     */
    @Override
    public boolean remove(String identificador) {
        boolean resposta;

        try {
            this.resolucaoDAO.deleteResolucaoById(identificador);
            resposta = true;
        } catch (IdentificadorDesconhecido e) {
            resposta = false;
        }
        return (resposta);
    }

    /**
     * A descrição do método está na interface {@code ResolucaoRepository}
     *
     * @see ResolucaoRepository
     */
    @Override
    public List<String> resolucoes() {
        Set<Resolucao> setResolucao = this.resolucaoDAO.findAllResolucao();
        List<String> listId = new ArrayList<>();

        for (Resolucao resolucao : setResolucao) {
            listId.add(resolucao.getId());
        }

        return (listId);
    }

    /**
     * A descrição do método está na interface {@code ResolucaoRepository}
     *
     * @see ResolucaoRepository
     */
    @Override
    public void persisteTipo(Tipo tipo) {
        this.tipoDAO.insertTipo(tipo);
    }

    /**
     * A descrição do método está na interface {@code ResolucaoRepository}
     *
     * @see ResolucaoRepository
     */
    @Override
    public void removeTipo(String codigo) {
        this.tipoDAO.deleteTipoById(codigo);
    }

    /**
     * A descrição do método está na interface {@code ResolucaoRepository}
     *
     * @see ResolucaoRepository
     */
    @Override
    public Tipo tipoPeloCodigo(String codigo) {
        Tipo tipo;

        try {
            tipo = this.tipoDAO.findTipoById(codigo);
        } catch (IdentificadorDesconhecido e) {
            tipo = null;
        }
        return (tipo);
    }

    /**
     * A descrição do método está na interface {@code ResolucaoRepository}
     *
     * @see ResolucaoRepository
     */
    @Override
    public List<Tipo> tiposPeloNome(String nome) {
        List<Tipo> listTipo = new ArrayList<>();

        for (Tipo tipo : this.tipoDAO.findAllTipo()) {
            if (tipo.getId().contains(nome)) {
                listTipo.add(tipo);
            }
        }
        return (listTipo);
    }

}
