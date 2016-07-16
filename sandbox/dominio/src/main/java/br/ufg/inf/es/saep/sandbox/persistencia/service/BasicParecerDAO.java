/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.service;

import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Parecer;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceParecerDAO;
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
 * @see InterfaceParecerDAO
 *
 * Classe responsável por realizar operações no banco de dados mongo referentes
 * aos Parecers do projeto.
 */
public class BasicParecerDAO implements InterfaceParecerDAO {

    /**
     * Instancia referente a um banco de dados Mongo.
     */
    private MongoDatabase mongoDatabase;

    /**
     * Instancia referente a uma collection de dado Mongo aonde se armazenara os
     * Parecers.
     *
     * @see Parecer
     * @see InterfaceResolucaoDAO
     */
    private MongoCollection mongoCollection;

    /**
     * Palavra-chave referente ao identificador unico de um {@code Parecer}.
     *
     * @see Parecer
     */
    private final String ID = "id";

    /**
     * Construtor responsável por inicializar a coleção referente aos Parecer.
     *
     * @param mongoDatabase Instancia do banco de dados mongo.
     * @param collectionName Nome da coleção referente ao {@code Parecer}
     * @see Parecer
     */
    public BasicParecerDAO(MongoDatabase mongoDatabase, String collectionName) {
        this.mongoDatabase = mongoDatabase;
        this.mongoCollection = this.mongoDatabase.getCollection(collectionName);

        Document index = new Document(ID, 1);
        this.mongoCollection.createIndex(index, new IndexOptions().unique(true));
    }

    /**
     * A descrição do método está na interface {@code InterfaceParecerDAO}
     *
     * @see InterfaceParecerDAO
     */
    @Override
    public Parecer findParecerById(String id) {
        FindIterable<Document> iterable = this.mongoCollection.find(new Document("id", id));

        if (iterable.first() == null) {
            throw new IdentificadorDesconhecido(id);
        }

        Gson gson = new Gson();
        Parecer parecer = gson.fromJson(iterable.first().toJson(), Parecer.class);

        return (parecer);
    }

    /**
     * A descrição do método está na interface {@code InterfaceParecerDAO}
     *
     * @see InterfaceParecerDAO
     */
    @Override
    public HashSet<Parecer> findAllParecer() {
        Collection<Parecer> collectionParecer = this.mongoCollection.find().into(new HashSet<>());

        return ((HashSet<Parecer>) collectionParecer);
    }

    /**
     * A descrição do método está na interface {@code InterfaceParecerDAO}
     *
     * @see InterfaceParecerDAO
     */
    @Override
    public void updateParecerById(String id, Parecer parecer) {
        Gson gson = new Gson();
        Document docParecer = Document.parse(gson.toJson(parecer));

        try {
            Object foar = this.mongoCollection.findOneAndReplace(new Document("id", id), docParecer);
            if (foar == null) {
                throw new IdentificadorDesconhecido(id);
            }
        } catch (MongoWriteException e) {
            throw new IdentificadorExistente(id);
        }
    }

    /**
     * A descrição do método está na interface {@code InterfaceParecerDAO}
     *
     * @see InterfaceParecerDAO
     */
    @Override
    public void deleteParecerById(String id) {
        Object foad = this.mongoCollection.findOneAndDelete(new Document("id", id));
        if (foad == null) {
            throw new IdentificadorDesconhecido(id);
        }
    }

    /**
     * A descrição do método está na interface {@code InterfaceParecerDAO}
     *
     * @see InterfaceParecerDAO
     */
    @Override
    public void deleteAllParecer() {
        this.mongoCollection.drop();
    }

    /**
     * A descrição do método está na interface {@code InterfaceParecerDAO}
     *
     * @see InterfaceParecerDAO
     */
    @Override
    public void insertParecer(Parecer parecer) {
        Gson gson = new Gson();
        Document docParecer = Document.parse(gson.toJson(parecer));

        try {
            this.mongoCollection.insertOne(docParecer);
        } catch (MongoWriteException e) {
            throw new IdentificadorExistente(parecer.getId());
        }
    }

}
