/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.model;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import java.util.Set;

/**
 *
 * @author Pedro Henrique Silva Farias
 * Interface Referente as funcionalidades de CRUD de uma Resolucao.
 */
public interface InterfaceResolucaoDAO {
    
    /**
     * Método responsável por buscar uma instancia de {@code resolucao} referente a um {@code id}.
     * 
     * @param id identificador da {@code resolucao} a ser buscada.
     * @return {@code Resolucao} referente ao identificador inserido como entrada.
     * @throws IdentificadorDesconhecido caso o identificador não exista.
     * @see Resolucao
     */
    public Resolucao findResolucaoById(String id);
    
    /**
     * Método responsável por buscar todas as instancias de {@code resolucao} existentes no banco de dados.
     * 
     * @return {@code Set<Resolucao>} referente a uma coleção contendo todas as Resoluções do banco.
     * @see Resolucao
     */
    public Set<Resolucao> findAllResolucao();

    /**
     * Método responsável por inserir uma instancia de {@code resolucao}.
     * 
     * @param resolucao {@code resolucao} a ser inserida no banco de dados.
     * @throws IdentificadorExistente caso o identificador já exista no banco de dados.
     * @see Resolucao
     */
    public void insertResolucao(Resolucao resolucao);

    /**
     * Método responsável por atualizar uma instancia de {@code resolucao} com base em um {@code  id}.
     * 
     * @param resolucao {@code resolucao} a ser inserida no banco de dados.
     * @param id {@code id} referente a Resolução que será atualizada no banco de dados.
     * @throws IdentificadorExistente caso o identificador já exista no banco de dados.
     * @throws IdentificadorDesconhecido caso o identificador não exista no banco de dados.
     * @see Resolucao
     */
    public void updateResolucaoById(String id, Resolucao resolucao);

    /**
     * Método responsável por remover uma instancia de {@code resolucao} existente no banco referente a um {@code id}.
     * 
     * @param id identificador da {@code resolucao} a ser removido.
     * @throws IdentificadorDesconhecido caso o identificador não exista.
     */
    public void deleteResolucaoById(String id);
    
    /**
     * Método responsável por excluir a coleção referente á {@code resolucao}.
     */
    public void deleteAllResolucao();
}
