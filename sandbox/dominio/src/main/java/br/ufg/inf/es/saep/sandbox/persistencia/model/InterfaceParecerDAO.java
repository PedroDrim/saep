/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.model;

import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import java.util.Set;

/**
 *
 * @author Pedro Henrique Silva Farias
 *
 * Interface Referente as funcionalidades de CRUD de um parecer.
 */
public interface InterfaceParecerDAO {
    
    /**
     * Método responsável por buscar uma instancia de {@code parecer} referente a
     * um {@code id}.
     *
     * @param id identificador da {@code parecer} a ser buscada.
     * @return {@code parecer} referente ao identificador inserido como entrada.
     * @throws IdentificadorDesconhecido caso o identificador não exista.
     * @see Parecer
     */
    public Parecer findParecerById(String id);

    /**
     * Método responsável por buscar todas as instancias de {@code parecer}
     * existentes no banco de dados.
     *
     * @return {@code Set<parecer>} referente a uma coleção contendo todas os parecers
     * do banco.
     * @see Parecer
     */
    public Set<Parecer> findAllParecer();

    /**
     * Método responsável por inserir uma instancia de {@code parecer}.
     *
     * @param parecer {@code parecer} a ser inserida no banco de dados.
     * @throws IdentificadorExistente caso o identificador já exista no banco de
     * dados.
     * @see Parecer
     */
    public void insertParecer(Parecer parecer);

    /**
     * Método responsável por atualizar uma instancia de {@code parecer} com base
     * em um {@code  id}.
     *
     * @param parecer {@code parecer} a ser inserida no banco de dados.
     * @param id {@code id} referente a parecer que será atualizada no banco de
     * dados.
     * @throws IdentificadorExistente caso o identificador já exista no banco de
     * dados.
     * @throws IdentificadorDesconhecido caso o identificador não exista no
     * banco de dados.
     * @see Parecer
     */
    public void updateParecerById(String id, Parecer parecer);

    /**
     * Método responsável por remover uma instancia de {@code parecer} existente no
     * banco referente a um {@code id}.
     *
     * @param id identificador da {@code parecer} a ser removido.
     * @throws IdentificadorDesconhecido caso o identificador não exista.
     */
    public void deleteParecerById(String id);

    /**
     * Método responsável por excluir a coleção referente á {@code parecer}.
     */
    public void deleteAllParecer();

}
