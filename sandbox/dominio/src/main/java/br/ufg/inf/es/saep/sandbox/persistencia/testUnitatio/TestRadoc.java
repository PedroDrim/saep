/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.testUnitatio;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepConversor;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepDeconversor;
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
public class TestRadoc {

    public static void test1() {

        List<Relato> relatos = new ArrayList<>();

        Valor valor1 = new Valor(240);
        Valor valor2 = new Valor(360);
        Valor valor3 = new Valor(120);

        Map<String, Valor> mapa = new HashMap<>();
        mapa.put("att1", valor1);
        mapa.put("att2", valor2);
        mapa.put("att3", valor3);

        Atributo atributo1 = new Atributo("att1", "Atributo de teste 1", Atributo.LOGICO);
        Atributo atributo2 = new Atributo("att2", "Atributo de teste 2", Atributo.REAL);
        Atributo atributo3 = new Atributo("att3", "Atributo de teste 3", Atributo.STRING);

        Set<Atributo> atributos = new HashSet<>();
        atributos.add(atributo1);
        atributos.add(atributo2);
        atributos.add(atributo3);

        Tipo tipo1 = new Tipo("TT", "tipo1", "Um tipo de teste", atributos);
        
        Relato relato = new Relato(tipo1.getId(), mapa);
        relatos.add(relato);
        
        List<Tipo> listTipo = new ArrayList<>();
        listTipo.add(tipo1);
        
        Radoc radoc = new Radoc("RAD1", 2016, relatos);
        
        Document doc = SaepConversor.convertRadocToDocument(radoc, listTipo);
        Radoc radocR = SaepDeconversor.deconvertDocumentToRadoc(doc);
        
        System.out.println("Fim do teste");
    }
}
