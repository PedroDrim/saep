/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.DAO;

import br.ufg.inf.es.saep.sandbox.dominio.SaepException;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
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
public class TipoDAO implements InterfaceDAO{

    public static final String COLLECTION_NAME = "Tipo";
    private MongoDatabase database;
    
    public TipoDAO(MongoDatabase database){
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
        String tipoId = (String) id;
        
        Iterable<Document> iterable = this.database.getCollection(COLLECTION_NAME).find();
        for(Document document : iterable){
            
            Tipo tipo = SaepDeconversor.deconvertDocumentToTipo(document);
            if( tipoId.equals(tipo.getId()) ){
                return( SaepConversor.convertTipoToDocument(tipo) );
            }
        }
        
        throw new SaepException("Nao foi encontrado tipo com o id " + tipoId);
    }

    @Override
    public boolean remove(Object id) {

        String tipoId = (String) id;
        
        Iterable<Document> iterable = this.database.getCollection(COLLECTION_NAME).find();
        for(Document document : iterable){
            
            Tipo tipo = SaepDeconversor.deconvertDocumentToTipo(document);
            if( tipoId.equals(tipo.hashCode()) ){
                
                BsonDocument bdoc = BsonDocument.parse(document.toJson());
                this.database.getCollection(COLLECTION_NAME).findOneAndDelete(bdoc);
                return(true);
            }
        }
        
        throw new SaepException("Nao foi encontrado tipo com o id " + tipoId);

    }

    @Override
    public List<String> listAll() {

        List<String> listaIds = new ArrayList<>();
        
        Iterable<Document> iterable = this.database.getCollection(COLLECTION_NAME).find();
        for(Document document : iterable){
            
            Tipo tipo = SaepDeconversor.deconvertDocumentToTipo(document);
            listaIds.add(tipo.getId());
        }        
        
        return(listaIds);
    }
    
}
