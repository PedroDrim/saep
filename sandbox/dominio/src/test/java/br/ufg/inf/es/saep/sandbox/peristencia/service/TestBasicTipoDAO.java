/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.peristencia.service;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceTipoDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.service.BasicTipoDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author Pedro Henrique Silva Farias
 */
public class TestBasicTipoDAO extends TestCase {

    private InterfaceTipoDAO basicTipoDAO;

    public TestBasicTipoDAO() {
        MongoClient cliente = new MongoClient();
        MongoDatabase mongoDatabase = cliente.getDatabase("Saep");
        this.basicTipoDAO = new BasicTipoDAO(mongoDatabase, "Tipo");

    }

    private void dropCollection() {
        this.basicTipoDAO.deleteAllTipo();
    }

    @Test
    public void testInsertTipo() {

        Set<Atributo> setAtributo = new HashSet<>();
        Atributo atributo1 = new Atributo("Atrib1", "Um atributo de testes", Atributo.STRING);
        setAtributo.add(atributo1);
        Tipo tipo = new Tipo("tId1", "Tipo teste", "Um tipo utilizado apenas para teste.", setAtributo);

        this.basicTipoDAO.insertTipo(tipo);
        assertTrue("O Tipo " + tipo.getId() + " foi inserido no banco", true);
        this.dropCollection();
    }

    @Test(expected = IdentificadorExistente.class)
    public void testInsertTipoExistente() {

        Set<Atributo> setAtributo = new HashSet<>();
        Atributo atributo1 = new Atributo("Atrib1", "Um atributo de testes", Atributo.STRING);
        setAtributo.add(atributo1);
        Tipo tipo = new Tipo("tId1", "Tipo teste", "Um tipo utilizado apenas para teste.", setAtributo);

        try {
            this.basicTipoDAO.insertTipo(tipo);
            this.basicTipoDAO.insertTipo(tipo);
            fail("Não houve captura de exception para o tipo " + tipo.getId());
        } catch (IdentificadorExistente e) {
            assertTrue("O Tipo " + tipo.getId() + " já existe no banco", true);
        }
        this.dropCollection();
    }

    @Test
    public void testFindTipoExistente() {

        String id = "tId1";

        Set<Atributo> setAtributo = new HashSet<>();
        Atributo atributo1 = new Atributo("Atrib1", "Um atributo de testes", Atributo.STRING);
        setAtributo.add(atributo1);
        Tipo tipo = new Tipo(id, "Tipo teste", "Um tipo utilizado apenas para teste.", setAtributo);

        this.basicTipoDAO.insertTipo(tipo);
        assertEquals("Os tipos inseridos e buscados são iguais.", tipo, this.basicTipoDAO.findTipoById(id));
        this.dropCollection();
    }

    @Test(expected = IdentificadorDesconhecido.class)
    public void testFindTipoInexistente() {

        String id = "tId2";

        Set<Atributo> setAtributo = new HashSet<>();
        Atributo atributo1 = new Atributo("Atrib1", "Um atributo de testes", Atributo.STRING);
        setAtributo.add(atributo1);
        Tipo tipo = new Tipo(id, "Tipo teste", "Um tipo utilizado apenas para teste.", setAtributo);

        this.basicTipoDAO.insertTipo(tipo);
        assertNotNull(this.basicTipoDAO.findTipoById(id));
        this.dropCollection();
    }

    @Test
    public void testAllFindTipoExistente() {

        Set<Atributo> setAtributo = new HashSet<>();
        Atributo atributo1 = new Atributo("Atrib1", "Um atributo de testes", Atributo.STRING);
        setAtributo.add(atributo1);

        Tipo tipo1 = new Tipo("tId1", "Tipo teste", "Um tipo utilizado apenas para teste.", setAtributo);
        Tipo tipo2 = new Tipo("tId2", "Tipo teste", "Um tipo utilizado apenas para teste.", setAtributo);

        this.basicTipoDAO.insertTipo(tipo1);
        this.basicTipoDAO.insertTipo(tipo2);

        HashSet<Tipo> setTipos = new HashSet<>();
        assertEquals("As coleções inseridos e buscados são iguais.", setTipos, this.basicTipoDAO.findAllTipo());
        this.dropCollection();
    }

    @Test
    public void testUpdateTipo() {

        Set<Atributo> setAtributo = new HashSet<>();
        Atributo atributo1 = new Atributo("Atrib1", "Um atributo de testes", Atributo.STRING);
        setAtributo.add(atributo1);

        Tipo tipo1 = new Tipo("tId1", "Tipo a ser inserido", "Um tipo utilizado apenas para teste.", setAtributo);
        Tipo tipo2 = new Tipo("tId1", "Tipo a ser atualizado", "Um tipo utilizado apenas para teste.", setAtributo);

        this.basicTipoDAO.insertTipo(tipo1);
        this.basicTipoDAO.updateTipoById(tipo1.getId(), tipo2);

        assertEquals("A coleção foi atualizada.", tipo2.getNome(), this.basicTipoDAO.findTipoById("tId1").getNome());
        this.dropCollection();
    }

    @Test(expected = IdentificadorExistente.class)
    public void testUpdateTipoExistente() {

        Set<Atributo> setAtributo = new HashSet<>();
        Atributo atributo1 = new Atributo("Atrib1", "Um atributo de testes", Atributo.STRING);
        setAtributo.add(atributo1);

        Tipo tipo1 = new Tipo("tId1", "Tipo a ser inserido1", "Um tipo utilizado apenas para teste.", setAtributo);
        Tipo tipo2 = new Tipo("tId2", "Tipo a ser inserido2", "Um tipo utilizado apenas para teste.", setAtributo);
        Tipo tipo3 = new Tipo("tId2", "Tipo a ser atualizado", "Um tipo utilizado apenas para teste.", setAtributo);

        this.basicTipoDAO.insertTipo(tipo1);
        this.basicTipoDAO.insertTipo(tipo2);
        this.basicTipoDAO.updateTipoById(tipo1.getId(), tipo3);

        assertEquals("A coleção nao foi atualizada.", tipo3.getNome(), this.basicTipoDAO.findTipoById("tId2").getNome());
        this.dropCollection();
    }

    @Test(expected = IdentificadorDesconhecido.class)
    public void testUpdateTipoInexistente() {

        Set<Atributo> setAtributo = new HashSet<>();
        Atributo atributo1 = new Atributo("Atrib1", "Um atributo de testes", Atributo.STRING);
        setAtributo.add(atributo1);

        Tipo tipo1 = new Tipo("tId1", "Tipo a ser inserido1", "Um tipo utilizado apenas para teste.", setAtributo);
        Tipo tipo2 = new Tipo("tId3", "Tipo a ser inserido2", "Um tipo utilizado apenas para teste.", setAtributo);

        this.basicTipoDAO.insertTipo(tipo1);
        this.basicTipoDAO.updateTipoById("tId3", tipo2);

        assertEquals("A coleção nao foi atualizada.", tipo2.getNome(), this.basicTipoDAO.findTipoById("tId3").getNome());
        this.dropCollection();
    }
}
