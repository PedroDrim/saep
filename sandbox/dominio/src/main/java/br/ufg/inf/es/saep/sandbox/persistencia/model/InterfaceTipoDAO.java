/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.model;

import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import java.util.Set;

/**
 *
 * @author Pedro Henrique Silva Farias
 *
 * Interface Referente as funcionalidades de CRUD de um tipo.
 */
public interface InterfaceTipoDAO {

    /**
     * Método responsável por buscar uma instancia de {@code tipo} referente a
     * um {@code id}.
     *
     * @param id identificador da {@code tipo} a ser buscada.
     * @return {@code tipo} referente ao identificador inserido como entrada.
     * @throws IdentificadorDesconhecido caso o identificador não exista.
     * @see Tipo
     */
    public Tipo findTipoById(String id);

    /**
     * Método responsável por buscar todas as instancias de {@code tipo}
     * existentes no banco de dados.
     *
     * @return {@code Set<tipo>} referente a uma coleção contendo todas os tipos
     * do banco.
     * @see Tipo
     */
    public Set<Tipo> findAllTipo();

    /**
     * Método responsável por inserir uma instancia de {@code tipo}.
     *
     * @param tipo {@code tipo} a ser inserida no banco de dados.
     * @throws IdentificadorExistente caso o identificador já exista no banco de
     * dados.
     * @see Tipo
     */
    public void insertTipo(Tipo tipo);

    /**
     * Método responsável por atualizar uma instancia de {@code tipo} com base
     * em um {@code  id}.
     *
     * @param tipo {@code tipo} a ser inserida no banco de dados.
     * @param id {@code id} referente a tipo que será atualizada no banco de
     * dados.
     * @throws IdentificadorExistente caso o identificador já exista no banco de
     * dados.
     * @throws IdentificadorDesconhecido caso o identificador não exista no
     * banco de dados.
     * @see Tipo
     */
    public void updateTipoById(String id, Tipo tipo);

    /**
     * Método responsável por remover uma instancia de {@code tipo} existente no
     * banco referente a um {@code id}.
     *
     * @param id identificador da {@code tipo} a ser removido.
     * @throws IdentificadorDesconhecido caso o identificador não exista.
     */
    public void deleteTipoById(String id);

    /**
     * Método responsável por excluir a coleção referente á {@code tipo}.
     */
    public void deleteAllTipo();
}
