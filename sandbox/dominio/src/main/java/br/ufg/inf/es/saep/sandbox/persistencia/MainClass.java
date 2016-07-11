/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.persistencia.testUnitatio.TestTipo;
import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepConversor;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.persistencia.DAO.ResolucaoDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.DAO.ResolucaoPersistence;
import br.ufg.inf.es.saep.sandbox.persistencia.DAO.TipoDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepDeconversor;
import br.ufg.inf.es.saep.sandbox.persistencia.testUnitatio.TestResolucaoPersistence;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        // TODO code application logic here
        MongoClient cliente = new MongoClient();
        MongoDatabase database = cliente.getDatabase("Saep");
        
        MongoIterable<String> collectionNames = database.listCollectionNames();
        for(String name : collectionNames){
            System.out.println("Collection: " + name);
            System.out.println("Tamanho: " + database.getCollection(name).count() );
        }
        
        TestResolucaoPersistence.teste1(database);
        //database.drop();
    }

}
