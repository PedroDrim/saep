/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.peristencia.service;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Relato;
import br.ufg.inf.es.saep.sandbox.dominio.Radoc;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceRadocDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.service.BasicRadocDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

import org.junit.Test;

/**
 *
 * @author pedro
 */
public class TesteBasicRadocDAO  extends TestCase {

    private InterfaceRadocDAO basicRadocDAO;

    public TesteBasicRadocDAO() {
        MongoClient cliente = new MongoClient();
        MongoDatabase mongoDatabase = cliente.getDatabase("Saep");
        this.basicRadocDAO = new BasicRadocDAO(mongoDatabase, "Radoc");

    }

    private void dropCollection() {
        this.basicRadocDAO.deleteAllRadoc();
    }

    @Test
    public void testInsertRadoc() {

        List<Relato> relatos = new ArrayList<>();

        Valor valor1 = new Valor("A");
        Valor valor2 = new Valor(true);
        Valor valor3 = new Valor(120);

        Map<String, Valor> mapa = new HashMap<>();
        mapa.put("v1S", valor1);
        mapa.put("v2L", valor2);
        mapa.put("v3R", valor3);

        Atributo atributo1 = new Atributo("att1", "Atributo de teste 1", Atributo.LOGICO);
        Atributo atributo2 = new Atributo("att2", "Atributo de teste 2", Atributo.REAL);
        Atributo atributo3 = new Atributo("att3", "Atributo de teste 3", Atributo.STRING);

        Set<Atributo> atributos = new HashSet<>();
        atributos.add(atributo1);
        atributos.add(atributo2);
        atributos.add(atributo3);

        Tipo tipo1 = new Tipo("TT", "radoc1", "Um radoc de teste", atributos);

        Relato relato = new Relato(tipo1.getId(), mapa);
        relatos.add(relato);

        Radoc radoc = new Radoc("RAD1", 2016, relatos);

        this.basicRadocDAO.insertRadoc(radoc);
        assertTrue("O Radoc " + radoc.getId() + " foi inserido no banco", true);
        this.dropCollection();
    }

    @Test(expected = IdentificadorExistente.class)
    public void testInsertRadocExistente() {

        List<Relato> relatos = new ArrayList<>();

        Valor valor1 = new Valor("A");
        Valor valor2 = new Valor(true);
        Valor valor3 = new Valor(120);

        Map<String, Valor> mapa = new HashMap<>();
        mapa.put("v1S", valor1);
        mapa.put("v2L", valor2);
        mapa.put("v3R", valor3);

        Atributo atributo1 = new Atributo("att1", "Atributo de teste 1", Atributo.LOGICO);
        Atributo atributo2 = new Atributo("att2", "Atributo de teste 2", Atributo.REAL);
        Atributo atributo3 = new Atributo("att3", "Atributo de teste 3", Atributo.STRING);

        Set<Atributo> atributos = new HashSet<>();
        atributos.add(atributo1);
        atributos.add(atributo2);
        atributos.add(atributo3);

        Tipo tipo1 = new Tipo("TT", "radoc1", "Um radoc de teste", atributos);

        Relato relato = new Relato(tipo1.getId(), mapa);
        relatos.add(relato);

        Radoc radoc = new Radoc("RAD1", 2016, relatos);

        try {
            this.basicRadocDAO.insertRadoc(radoc);
            this.basicRadocDAO.insertRadoc(radoc);
            fail("Não houve captura de exception para o radoc " + radoc.getId());
        } catch (IdentificadorExistente e) {
            assertTrue("O Radoc " + radoc.getId() + " já existe no banco", true);
        }
        this.dropCollection();
    }

    @Test
    public void testFindRadocExistente() {

        String id = "RAD1";

        List<Relato> relatos = new ArrayList<>();

        Valor valor1 = new Valor("A");
        Valor valor2 = new Valor(true);
        Valor valor3 = new Valor(120);

        Map<String, Valor> mapa = new HashMap<>();
        mapa.put("v1S", valor1);
        mapa.put("v2L", valor2);
        mapa.put("v3R", valor3);

        Atributo atributo1 = new Atributo("att1", "Atributo de teste 1", Atributo.LOGICO);
        Atributo atributo2 = new Atributo("att2", "Atributo de teste 2", Atributo.REAL);
        Atributo atributo3 = new Atributo("att3", "Atributo de teste 3", Atributo.STRING);

        Set<Atributo> atributos = new HashSet<>();
        atributos.add(atributo1);
        atributos.add(atributo2);
        atributos.add(atributo3);

        Tipo tipo1 = new Tipo("TT", "radoc1", "Um radoc de teste", atributos);

        Relato relato = new Relato(tipo1.getId(), mapa);
        relatos.add(relato);

        Radoc radoc = new Radoc(id, 2016, relatos);

        this.basicRadocDAO.insertRadoc(radoc);
        assertEquals("Os radocs inseridos e buscados são iguais.", radoc, this.basicRadocDAO.findRadocById(id));
        this.dropCollection();
    }

    @Test(expected = IdentificadorDesconhecido.class)
    public void testFindRadocInexistente() {

        String id = "RAD2";

        List<Relato> relatos = new ArrayList<>();

        Valor valor1 = new Valor("A");
        Valor valor2 = new Valor(true);
        Valor valor3 = new Valor(120);

        Map<String, Valor> mapa = new HashMap<>();
        mapa.put("v1S", valor1);
        mapa.put("v2L", valor2);
        mapa.put("v3R", valor3);

        Atributo atributo1 = new Atributo("att1", "Atributo de teste 1", Atributo.LOGICO);
        Atributo atributo2 = new Atributo("att2", "Atributo de teste 2", Atributo.REAL);
        Atributo atributo3 = new Atributo("att3", "Atributo de teste 3", Atributo.STRING);

        Set<Atributo> atributos = new HashSet<>();
        atributos.add(atributo1);
        atributos.add(atributo2);
        atributos.add(atributo3);

        Tipo tipo1 = new Tipo("TT", "radoc1", "Um radoc de teste", atributos);

        Relato relato = new Relato(tipo1.getId(), mapa);
        relatos.add(relato);

        Radoc radoc = new Radoc("RAD1", 2016, relatos);

        this.basicRadocDAO.insertRadoc(radoc);
        assertNotNull(this.basicRadocDAO.findRadocById(id));
        this.dropCollection();
    }

    @Test
    public void testAllFindRadocExistente() {

        List<Relato> relatos = new ArrayList<>();

        Valor valor1 = new Valor("A");
        Valor valor2 = new Valor(true);
        Valor valor3 = new Valor(120);

        Map<String, Valor> mapa = new HashMap<>();
        mapa.put("v1S", valor1);
        mapa.put("v2L", valor2);
        mapa.put("v3R", valor3);

        Atributo atributo1 = new Atributo("att1", "Atributo de teste 1", Atributo.LOGICO);
        Atributo atributo2 = new Atributo("att2", "Atributo de teste 2", Atributo.REAL);
        Atributo atributo3 = new Atributo("att3", "Atributo de teste 3", Atributo.STRING);

        Set<Atributo> atributos = new HashSet<>();
        atributos.add(atributo1);
        atributos.add(atributo2);
        atributos.add(atributo3);

        Tipo tipo1 = new Tipo("TT", "radoc1", "Um radoc de teste", atributos);

        Relato relato = new Relato(tipo1.getId(), mapa);
        relatos.add(relato);

        Radoc radoc1 = new Radoc("RAD1", 2016, relatos);
        Radoc radoc2 = new Radoc("RAD2", 2016, relatos);

        
        this.basicRadocDAO.insertRadoc(radoc1);
        this.basicRadocDAO.insertRadoc(radoc2);

        HashSet<Radoc> setRadocs = new HashSet<>();
        assertEquals("As coleções inseridos e buscados são iguais.", setRadocs, this.basicRadocDAO.findAllRadoc());
        this.dropCollection();
    }

    @Test
    public void testUpdateRadoc() {

        List<Relato> relatos = new ArrayList<>();

        Valor valor1 = new Valor("A");
        Valor valor2 = new Valor(true);
        Valor valor3 = new Valor(120);

        Map<String, Valor> mapa = new HashMap<>();
        mapa.put("v1S", valor1);
        mapa.put("v2L", valor2);
        mapa.put("v3R", valor3);

        Atributo atributo1 = new Atributo("att1", "Atributo de teste 1", Atributo.LOGICO);
        Atributo atributo2 = new Atributo("att2", "Atributo de teste 2", Atributo.REAL);
        Atributo atributo3 = new Atributo("att3", "Atributo de teste 3", Atributo.STRING);

        Set<Atributo> atributos = new HashSet<>();
        atributos.add(atributo1);
        atributos.add(atributo2);
        atributos.add(atributo3);

        Tipo tipo1 = new Tipo("TT", "radoc1", "Um radoc de teste", atributos);

        Relato relato = new Relato(tipo1.getId(), mapa);
        relatos.add(relato);

        Radoc radoc1 = new Radoc("RAD1", 2016, relatos);
        Radoc radoc2 = new Radoc("RAD2", 2017, relatos);
        
        this.basicRadocDAO.insertRadoc(radoc1);
        this.basicRadocDAO.updateRadocById(radoc1.getId(), radoc2);

        assertEquals("A coleção foi atualizada.", radoc2.getAnoBase(), this.basicRadocDAO.findRadocById("RAD1").getAnoBase());
        this.dropCollection();
    }

    @Test(expected = IdentificadorExistente.class)
    public void testUpdateRadocExistente() {

        List<Relato> relatos = new ArrayList<>();

        Valor valor1 = new Valor("A");
        Valor valor2 = new Valor(true);
        Valor valor3 = new Valor(120);

        Map<String, Valor> mapa = new HashMap<>();
        mapa.put("v1S", valor1);
        mapa.put("v2L", valor2);
        mapa.put("v3R", valor3);

        Atributo atributo1 = new Atributo("att1", "Atributo de teste 1", Atributo.LOGICO);
        Atributo atributo2 = new Atributo("att2", "Atributo de teste 2", Atributo.REAL);
        Atributo atributo3 = new Atributo("att3", "Atributo de teste 3", Atributo.STRING);

        Set<Atributo> atributos = new HashSet<>();
        atributos.add(atributo1);
        atributos.add(atributo2);
        atributos.add(atributo3);

        Tipo tipo1 = new Tipo("TT", "radoc1", "Um radoc de teste", atributos);

        Relato relato = new Relato(tipo1.getId(), mapa);
        relatos.add(relato);

        Radoc radoc1 = new Radoc("RAD1", 2016, relatos);
        Radoc radoc2 = new Radoc("RAD2", 2017, relatos);
        Radoc radoc3 = new Radoc("RAD2", 2017, relatos);
        
        this.basicRadocDAO.insertRadoc(radoc1);
        this.basicRadocDAO.insertRadoc(radoc2);
        this.basicRadocDAO.updateRadocById(radoc1.getId(), radoc3);

        assertEquals("A coleção nao foi atualizada.", radoc3.getAnoBase(), this.basicRadocDAO.findRadocById("RAD2").getAnoBase());
        this.dropCollection();
    }

    @Test(expected = IdentificadorDesconhecido.class)
    public void testUpdateRadocInexistente() {

        List<Relato> relatos = new ArrayList<>();

        Valor valor1 = new Valor("A");
        Valor valor2 = new Valor(true);
        Valor valor3 = new Valor(120);

        Map<String, Valor> mapa = new HashMap<>();
        mapa.put("v1S", valor1);
        mapa.put("v2L", valor2);
        mapa.put("v3R", valor3);

        Atributo atributo1 = new Atributo("att1", "Atributo de teste 1", Atributo.LOGICO);
        Atributo atributo2 = new Atributo("att2", "Atributo de teste 2", Atributo.REAL);
        Atributo atributo3 = new Atributo("att3", "Atributo de teste 3", Atributo.STRING);

        Set<Atributo> atributos = new HashSet<>();
        atributos.add(atributo1);
        atributos.add(atributo2);
        atributos.add(atributo3);

        Tipo tipo1 = new Tipo("TT", "radoc1", "Um radoc de teste", atributos);

        Relato relato = new Relato(tipo1.getId(), mapa);
        relatos.add(relato);

        Radoc radoc1 = new Radoc("RAD1", 2016, relatos);
        Radoc radoc2 = new Radoc("RAD2", 2017, relatos);

        this.basicRadocDAO.insertRadoc(radoc1);
        this.basicRadocDAO.updateRadocById("RAD3", radoc2);

        assertEquals("A coleção nao foi atualizada.", radoc2.getAnoBase(), this.basicRadocDAO.findRadocById("RAD3").getAnoBase());
        this.dropCollection();
    }
    
}
