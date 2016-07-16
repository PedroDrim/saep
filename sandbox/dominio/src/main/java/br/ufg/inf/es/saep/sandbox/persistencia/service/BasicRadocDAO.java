/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.service;

import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceRadocDAO;
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
 * @see InterfaceRadocDAO
 *
 * Classe responsável por realizar operações no banco de dados mongo referentes
 * aos Radocs do projeto.
 */
public class BasicRadocDAO implements InterfaceRadocDAO {

    /**
     * Instancia referente a um banco de dados Mongo.
     */
    private MongoDatabase mongoDatabase;

    /**
     * Instancia referente a uma collection de dado Mongo aonde se armazenara os
     * Radocs.
     *
     * @see Radoc
     * @see InterfaceResolucaoDAO
     */
    private MongoCollection mongoCollection;

    /**
     * Palavra-chave referente ao identificador unico de um {@code Radoc}.
     *
     * @see Radoc
     */
    private final String ID = "id";

    /**
     * Construtor responsável por inicializar a coleção referente aos Radoc.
     *
     * @param mongoDatabase Instancia do banco de dados mongo.
     * @param collectionName Nome da coleção referente ao {@code Radoc}
     * @see Radoc
     */
    public BasicRadocDAO(MongoDatabase mongoDatabase, String collectionName) {
        this.mongoDatabase = mongoDatabase;
        this.mongoCollection = this.mongoDatabase.getCollection(collectionName);

        Document index = new Document(ID, 1);
        this.mongoCollection.createIndex(index, new IndexOptions().unique(true));
    }

    /**
     * A descrição do método está na interface {@code InterfaceRadocDAO}
     *
     * @see InterfaceRadocDAO
     */
    @Override
    public Radoc findRadocById(String id) {
        FindIterable<Document> iterable = this.mongoCollection.find(new Document("id", id));

        if (iterable.first() == null) {
            throw new IdentificadorDesconhecido(id);
        }

        Gson gson = new Gson();
        Radoc radoc = gson.fromJson(iterable.first().toJson(), Radoc.class);

        return (radoc);
    }

    /**
     * A descrição do método está na interface {@code InterfaceRadocDAO}
     *
     * @see InterfaceRadocDAO
     */
    @Override
    public HashSet<Radoc> findAllRadoc() {
        Collection<Radoc> collectionRadoc = this.mongoCollection.find().into(new HashSet<>());

        return ((HashSet<Radoc>) collectionRadoc);
    }

    /**
     * A descrição do método está na interface {@code InterfaceRadocDAO}
     *
     * @see InterfaceRadocDAO
     */
    @Override
    public void updateRadocById(String id, Radoc radoc) {
        Gson gson = new Gson();
        Document docRadoc = Document.parse(gson.toJson(radoc));

        try {
            Object foar = this.mongoCollection.findOneAndReplace(new Document("id", id), docRadoc);
            if (foar == null) {
                throw new IdentificadorDesconhecido(id);
            }
        } catch (MongoWriteException e) {
            throw new IdentificadorExistente(id);
        }
    }

    /**
     * A descrição do método está na interface {@code InterfaceRadocDAO}
     *
     * @see InterfaceRadocDAO
     */
    @Override
    public void deleteRadocById(String id) {
        Object foad = this.mongoCollection.findOneAndDelete(new Document("id", id));
        if (foad == null) {
            throw new IdentificadorDesconhecido(id);
        }
    }

    /**
     * A descrição do método está na interface {@code InterfaceRadocDAO}
     *
     * @see InterfaceRadocDAO
     */
    @Override
    public void deleteAllRadoc() {
        this.mongoCollection.drop();
    }

    /**
     * A descrição do método está na interface {@code InterfaceRadocDAO}
     *
     * @see InterfaceRadocDAO
     */
    @Override
    public void insertRadoc(Radoc radoc) {
        Gson gson = new Gson();
        Document docRadoc = Document.parse(gson.toJson(radoc));

        try {
            this.mongoCollection.insertOne(docRadoc);
        } catch (MongoWriteException e) {
            throw new IdentificadorExistente(radoc.getId());
        }
    }

}
