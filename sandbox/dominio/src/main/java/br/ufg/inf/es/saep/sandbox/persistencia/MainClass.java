/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.persistencia.testUnitatio.TestPontuacao;
import br.ufg.inf.es.saep.sandbox.persistencia.testUnitatio.TestRadoc;
import br.ufg.inf.es.saep.sandbox.persistencia.testUnitatio.TestRelato;
import br.ufg.inf.es.saep.sandbox.persistencia.testUnitatio.TestResolucaoPersistence;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

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
        
        TestRadoc.test1();
        //database.drop();
    }

}
