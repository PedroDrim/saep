/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepConversor;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.persistencia.DAO.ResolucaoDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
        
        List<String> dependeDe = new ArrayList<>();
        dependeDe.add("B = 1 + 1");
        dependeDe.add("C = 2*5");
        
        Regra regra1 = new Regra("B + C", 60 , 0 , dependeDe, "Regra de teste", "A");
        regra1.setTipoRegra(Regra.EXPRESSAO);
        
        List<Regra> regras = new ArrayList<>();
        regras.add(regra1);
        
        Date date = Calendar.getInstance().getTime();
        Resolucao resolucao = new Resolucao("Res1", "resolucao de teste", date, regras);
        
        Document resoDoc = SaepConversor.convertResolucaoToDocument(resolucao);
        
        ResolucaoDAO resolucaoDAO = new ResolucaoDAO(database);
        
        resolucaoDAO.insert(resoDoc);
        System.out.println("Insert: " + resoDoc.toJson());
        
        Document x = resolucaoDAO.search("Res1");
        System.out.println("get: " + x.toJson());
        
        
        System.out.println("OK:");
        database.drop();
    }

}
