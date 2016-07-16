/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.model;

import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import java.util.Set;

/**
 *
 * @author Pedro Henrique Silva Farias
 *
 * Interface Referente as funcionalidades de CRUD de um radoc.
 */
public interface InterfaceRadocDAO {
    
    /**
     * Método responsável por buscar uma instancia de {@code radoc} referente a
     * um {@code id}.
     *
     * @param id identificador da {@code radoc} a ser buscada.
     * @return {@code radoc} referente ao identificador inserido como entrada.
     * @throws IdentificadorDesconhecido caso o identificador não exista.
     * @see Radoc
     */
    public Radoc findRadocById(String id);

    /**
     * Método responsável por buscar todas as instancias de {@code radoc}
     * existentes no banco de dados.
     *
     * @return {@code Set<radoc>} referente a uma coleção contendo todas os radocs
     * do banco.
     * @see Radoc
     */
    public Set<Radoc> findAllRadoc();

    /**
     * Método responsável por inserir uma instancia de {@code radoc}.
     *
     * @param radoc {@code radoc} a ser inserida no banco de dados.
     * @throws IdentificadorExistente caso o identificador já exista no banco de
     * dados.
     * @see Radoc
     */
    public void insertRadoc(Radoc radoc);

    /**
     * Método responsável por atualizar uma instancia de {@code radoc} com base
     * em um {@code  id}.
     *
     * @param radoc {@code radoc} a ser inserida no banco de dados.
     * @param id {@code id} referente a radoc que será atualizada no banco de
     * dados.
     * @throws IdentificadorExistente caso o identificador já exista no banco de
     * dados.
     * @throws IdentificadorDesconhecido caso o identificador não exista no
     * banco de dados.
     * @see Radoc
     */
    public void updateRadocById(String id, Radoc radoc);

    /**
     * Método responsável por remover uma instancia de {@code radoc} existente no
     * banco referente a um {@code id}.
     *
     * @param id identificador da {@code radoc} a ser removido.
     * @throws IdentificadorDesconhecido caso o identificador não exista.
     */
    public void deleteRadocById(String id);

    /**
     * Método responsável por excluir a coleção referente á {@code radoc}.
     */
    public void deleteAllRadoc();

}
