/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.DAO;

import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepConversor;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepDeconversor;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import org.bson.BsonDocument;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class ResolucaoDAO implements InterfaceDAO{

    public static final String COLLECTION_NAME = "Resolucao";
    private MongoDatabase database;
    
    public ResolucaoDAO(MongoDatabase database){
        this.database = database;
    }
    
    @Override
    public void insert(Document object) {
        
        this.database.getCollection(COLLECTION_NAME).insertOne(object);
    }

    @Override
    public void update(Object id, Document object) {

    }

    @Override
    public Document search(Object id) {
        String resolucaoId = (String) id;
        
        Iterable<Document> iterable = this.database.getCollection(COLLECTION_NAME).find();
        for(Document document : iterable){
            
            Resolucao resolucao = SaepDeconversor.deconvertDocumentToResolucao(document);
            if( resolucaoId.equals(resolucao.getId()) ){
                return( SaepConversor.convertResolucaoToDocument(resolucao) );
            }
        }
        
        throw new IdentificadorDesconhecido("Nao foi encontrado resolucao com o id " + resolucaoId);
    }

    @Override
    public boolean remove(Object id) {

        String resolucaoId = (String) id;
        
        Iterable<Document> iterable = this.database.getCollection(COLLECTION_NAME).find();
        for(Document document : iterable){
            
            Resolucao resolucao = SaepDeconversor.deconvertDocumentToResolucao(document);
            if( resolucaoId.equals(resolucao.getId()) ){
                
                BsonDocument bdoc = BsonDocument.parse(document.toJson());
                this.database.getCollection(COLLECTION_NAME).findOneAndDelete(bdoc);
                return(true);
            }
        }
        
        throw new IdentificadorDesconhecido("Nao foi encontrado resolucao com o id " + resolucaoId);

    }

    @Override
    public List<String> listAll() {

        List<String> listaIds = new ArrayList<>();
        
        Iterable<Document> iterable = this.database.getCollection(COLLECTION_NAME).find();
        for(Document document : iterable){
            
            Resolucao resolucao = SaepDeconversor.deconvertDocumentToResolucao(document);
            listaIds.add(resolucao.getId());
        }        
        
        return(listaIds);
    }
    
}
