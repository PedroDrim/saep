/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.service;

import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceResolucaoDAO;
import com.google.gson.Gson;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.bson.Document;

/**
 *
 * @author Pedro Henrique Silva Farias
 * @see InterfaceResolucaoDAO
 *
 * Classe responsável por realizar operações no banco de dados mongo referentes
 * ás Resoluções do projeto.
 */
public class BasicResolucaoDAO implements InterfaceResolucaoDAO {

    /**
     * Instancia referente a um banco de dados Mongo.
     */
    private MongoDatabase mongoDatabase;

    /**
     * Instancia referente a uma collection de dado Mongo aonde se armazenara as
     * Resoluções.
     *
     * @see Resolucao
     * @see InterfaceResolucaoDAO
     */
    private MongoCollection mongoCollection;

    /**
     * Palavra-chave referente ao identificador unico de uma {@code Resolucao}.
     *
     * @see Resolucao
     */
    private final String ID = "id";

    /**
     * Construtor responsável por inicializar a coleção referente ás Resoluções.
     * 
     * @param mongoDatabase Instancia do banco de dados mongo.
     * @param collectionName Nome da coleção referente á {@code Resolucao}
     * @see Resolucao
     */
    public BasicResolucaoDAO(MongoDatabase mongoDatabase, String collectionName) {
        this.mongoDatabase = mongoDatabase;
        this.mongoCollection = this.mongoDatabase.getCollection(collectionName);

        Document index = new Document(ID, 1);
        this.mongoCollection.createIndex(index, new IndexOptions().unique(true));
    }

    /**
     * A descrição do método está na interface {@code InterfaceTipoDAO}
     *
     * @see InterfaceTipoDAO
     */
    @Override
    public Resolucao findResolucaoById(String id) {
        FindIterable<Document> iterable = this.mongoCollection.find(new Document("id", id));

        if (iterable.first() == null) {
            throw new IdentificadorDesconhecido(id);
        }

        Gson gson = new Gson();
        Resolucao resolucao = gson.fromJson(iterable.first().toJson(), Resolucao.class);

        return (resolucao);
    }

    /**
     * A descrição do método está na interface {@code InterfaceTipoDAO}
     *
     * @see InterfaceTipoDAO
     */
    @Override
    public Set<Resolucao> findAllResolucao() {
        Collection<Resolucao> collectionTipo = this.mongoCollection.find().into(new HashSet<>());

        return ((HashSet<Resolucao>) collectionTipo);
    }

    /**
     * A descrição do método está na interface {@code InterfaceTipoDAO}
     *
     * @see InterfaceTipoDAO
     */
    @Override
    public void insertResolucao(Resolucao resolucao) {
        Gson gson = new Gson();
        Document docTipo = Document.parse(gson.toJson(resolucao));

        try {
            this.mongoCollection.insertOne(docTipo);
        } catch (MongoWriteException e) {
            throw new IdentificadorExistente(resolucao.getId());
        }
    }

    /**
     * A descrição do método está na interface {@code InterfaceTipoDAO}
     *
     * @see InterfaceTipoDAO
     */
    @Override
    public void updateResolucaoById(String id, Resolucao resolucao) {
        Gson gson = new Gson();
        Document docResolucao = Document.parse(gson.toJson(resolucao));

        try {
            Object foar = this.mongoCollection.findOneAndReplace(new Document("id", id), docResolucao);
            if (foar == null) {
                throw new IdentificadorDesconhecido(id);
            }
        } catch (MongoWriteException e) {
            throw new IdentificadorExistente(id);
        }
    }

    /**
     * A descrição do método está na interface {@code InterfaceTipoDAO}
     *
     * @see InterfaceTipoDAO
     */
    @Override
    public void deleteResolucaoById(String id) {
        Object foad = this.mongoCollection.findOneAndDelete(new Document("id", id));
        if (foad == null) {
            throw new IdentificadorDesconhecido(id);
        }
    }

    /**
     * A descrição do método está na interface {@code InterfaceTipoDAO}
     *
     * @see InterfaceTipoDAO
     */
    @Override
    public void deleteAllResolucao() {
        this.mongoCollection.drop();
    }

}
