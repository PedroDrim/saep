/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.service;

import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceTipoDAO;
import com.google.gson.Gson;
import com.mongodb.MongoWriteException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import java.util.Collection;
import java.util.HashSet;
import org.bson.Document;

/**
 *
 * @author Pedro Henrique Silva Farias
 * @see InterfaceTipoDAO
 *
 * Classe responsável por realizar operações no banco de dados mongo referentes
 * aos Tipos do projeto.
 */
public class BasicTipoDAO implements InterfaceTipoDAO {

    /**
     * Instancia referente a um banco de dados Mongo.
     */
    private MongoDatabase mongoDatabase;

    /**
     * Instancia referente a uma collection de dado Mongo aonde se armazenara os
     * Tipos.
     *
     * @see Tipo
     * @see InterfaceResolucaoDAO
     */
    private MongoCollection mongoCollection;

    /**
     * Palavra-chave referente ao identificador unico de um {@code Tipo}.
     *
     * @see Tipo
     */
    private final String ID = "id";

    /**
     * Construtor responsável por inicializar a coleção referente aos Tipo.
     *
     * @param mongoDatabase Instancia do banco de dados mongo.
     * @param collectionName Nome da coleção referente ao {@code Tipo}
     * @see Tipo
     */
    public BasicTipoDAO(MongoDatabase mongoDatabase, String collectionName) {
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
    public Tipo findTipoById(String id) {
        FindIterable<Document> iterable = this.mongoCollection.find(new Document("id", id));

        if (iterable.first() == null) {
            throw new IdentificadorDesconhecido(id);
        }

        Gson gson = new Gson();
        Tipo tipo = gson.fromJson(iterable.first().toJson(), Tipo.class);

        return (tipo);
    }

    /**
     * A descrição do método está na interface {@code InterfaceTipoDAO}
     *
     * @see InterfaceTipoDAO
     */
    @Override
    public HashSet<Tipo> findAllTipo() {
        Collection<Tipo> collectionTipo = this.mongoCollection.find().into(new HashSet<>());

        return ((HashSet<Tipo>) collectionTipo);
    }

    /**
     * A descrição do método está na interface {@code InterfaceTipoDAO}
     *
     * @see InterfaceTipoDAO
     */
    @Override
    public void updateTipoById(String id, Tipo tipo) {
        Gson gson = new Gson();
        Document docTipo = Document.parse(gson.toJson(tipo));

        try {
            Object foar = this.mongoCollection.findOneAndReplace(new Document("id", id), docTipo);
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
    public void deleteTipoById(String id) {
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
    public void deleteAllTipo() {
        this.mongoCollection.drop();
    }

    /**
     * A descrição do método está na interface {@code InterfaceTipoDAO}
     *
     * @see InterfaceTipoDAO
     */
    @Override
    public void insertTipo(Tipo tipo) {
        Gson gson = new Gson();
        Document docTipo = Document.parse(gson.toJson(tipo));

        try {
            this.mongoCollection.insertOne(docTipo);
        } catch (MongoWriteException e) {
            throw new IdentificadorExistente(tipo.getId());
        }
    }

}
