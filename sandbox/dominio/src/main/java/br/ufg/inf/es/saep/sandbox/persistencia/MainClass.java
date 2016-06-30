/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.util.AbstractSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

        Atributo att = new Atributo("A1", "Um atributo de teste", Atributo.LOGICO);
        Atributo att2 = new Atributo("A2", "Segundo atributo de teste", Atributo.STRING);
        
        Valor valor = new Valor(true);
        Valor valor2 = new Valor("Valor2");

        Map<String, Valor> mapaRelato = new HashMap<>();
        mapaRelato.put("A1", valor);
        mapaRelato.put("A2", valor2);

        Set<Atributo> atributos = new HashSet<>();
        atributos.add(att);
        atributos.add(att2);

        Tipo tipo = new Tipo("teste", "T1", "um tipo de teste", atributos);

        Relato relato = new Relato("Teste", mapaRelato);
        Document relDoc = SaepConversor.convertRelatoToDocument(relato, tipo);
        
        List<Relato> listaRelato = new ArrayList<>();
        listaRelato.add(relato);
        
        Radoc radoc = new Radoc(listaRelato);
        Document radDoc = SaepConversor.converterRadocToDocument(radoc);
        System.out.println("Radoc: " + radDoc.toJson());
        
        System.out.println("OK:");
    }

}
