/*
 * Copyright (c) 2016. Fábrica de Software - Instituto de Informática (UFG)
 * Creative Commons Attribution 4.0 International License.
 */
package br.ufg.inf.es.saep.sandbox.peristencia.service;

import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorDesconhecido;
import br.ufg.inf.es.saep.sandbox.dominio.IdentificadorExistente;
import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.persistencia.model.InterfaceResolucaoDAO;
import br.ufg.inf.es.saep.sandbox.persistencia.service.BasicResolucaoDAO;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import junit.framework.TestCase;
import org.junit.Test;

/**
 *
 * @author Pedro Henrique Silva Farias
 */
public class TestBasicResolucaoDAO extends TestCase {

    private InterfaceResolucaoDAO basicResolucaoDAO;

    public TestBasicResolucaoDAO() {
        MongoClient cliente = new MongoClient();
        MongoDatabase mongoDatabase = cliente.getDatabase("Saep");
        this.basicResolucaoDAO = new BasicResolucaoDAO(mongoDatabase, "Resolucao");

    }

    private void dropCollection() {
        this.basicResolucaoDAO.deleteAllResolucao();
    }

    @Test
    public void testInsertResolucao() {

        List<String> dependeDe = new ArrayList<>();
        dependeDe.add("B = 1 + 1");
        dependeDe.add("C = 2 * 5");
        Regra regra1 = new Regra("A", Regra.EXPRESSAO, "Regra de teste",
                60, 0, "B + C", null, null, "RELATO", 52, dependeDe);

        List<String> dependeDe2 = new ArrayList<>();
        dependeDe2.add("X = 1 + 1");
        dependeDe2.add("Y = 2");
        dependeDe2.add("C = 2*5");
        Regra regra2 = new Regra("Z", Regra.CONDICIONAL, "Regra de teste condicional",
                10, 5, " X > Y", "2*C", "C-2", "RELATO", 52, dependeDe2);

        List<Regra> regras = new ArrayList<>();
        regras.add(regra1);
        regras.add(regra2);

        Resolucao resolucao = new Resolucao("R1", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);

        this.basicResolucaoDAO.insertResolucao(resolucao);
        assertTrue("O Resolucao " + resolucao.getId() + " foi inserido no banco", true);
        this.dropCollection();
    }

    @Test(expected = IdentificadorExistente.class)
    public void testInsertResolucaoExistente() {

        List<String> dependeDe = new ArrayList<>();
        dependeDe.add("B = 1 + 1");
        dependeDe.add("C = 2 * 5");
        Regra regra1 = new Regra("A", Regra.EXPRESSAO, "Regra de teste",
                60, 0, "B + C", null, null, "RELATO", 52, dependeDe);

        List<String> dependeDe2 = new ArrayList<>();
        dependeDe2.add("X = 1 + 1");
        dependeDe2.add("Y = 2");
        dependeDe2.add("C = 2*5");
        Regra regra2 = new Regra("Z", Regra.CONDICIONAL, "Regra de teste condicional",
                10, 5, " X > Y", "2*C", "C-2", "RELATO", 52, dependeDe2);

        List<Regra> regras = new ArrayList<>();
        regras.add(regra1);
        regras.add(regra2);

        Resolucao resolucao = new Resolucao("R1", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);

        try {
            this.basicResolucaoDAO.insertResolucao(resolucao);
            this.basicResolucaoDAO.insertResolucao(resolucao);
            fail("Não houve captura de exception para o resolucao " + resolucao.getId());
        } catch (IdentificadorExistente e) {
            assertTrue("O Resolucao " + resolucao.getId() + " já existe no banco", true);
        }
        this.dropCollection();
    }

    @Test
    public void testFindResolucaoExistente() {

        String id = "R1";

        List<String> dependeDe = new ArrayList<>();
        dependeDe.add("B = 1 + 1");
        dependeDe.add("C = 2 * 5");
        Regra regra1 = new Regra("A", Regra.EXPRESSAO, "Regra de teste",
                60, 0, "B + C", null, null, "RELATO", 52, dependeDe);

        List<String> dependeDe2 = new ArrayList<>();
        dependeDe2.add("X = 1 + 1");
        dependeDe2.add("Y = 2");
        dependeDe2.add("C = 2*5");
        Regra regra2 = new Regra("Z", Regra.CONDICIONAL, "Regra de teste condicional",
                10, 5, " X > Y", "2*C", "C-2", "RELATO", 52, dependeDe2);

        List<Regra> regras = new ArrayList<>();
        regras.add(regra1);
        regras.add(regra2);

        Resolucao resolucao = new Resolucao("R1", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);

        this.basicResolucaoDAO.insertResolucao(resolucao);
        assertEquals("Os resolucaos inseridos e buscados são iguais.", resolucao, this.basicResolucaoDAO.findResolucaoById(id));
        this.dropCollection();
    }

    @Test(expected = IdentificadorDesconhecido.class)
    public void testFindResolucaoInexistente() {

        String id = "R2";

        List<String> dependeDe = new ArrayList<>();
        dependeDe.add("B = 1 + 1");
        dependeDe.add("C = 2 * 5");
        Regra regra1 = new Regra("A", Regra.EXPRESSAO, "Regra de teste",
                60, 0, "B + C", null, null, "RELATO", 52, dependeDe);

        List<String> dependeDe2 = new ArrayList<>();
        dependeDe2.add("X = 1 + 1");
        dependeDe2.add("Y = 2");
        dependeDe2.add("C = 2*5");
        Regra regra2 = new Regra("Z", Regra.CONDICIONAL, "Regra de teste condicional",
                10, 5, " X > Y", "2*C", "C-2", "RELATO", 52, dependeDe2);

        List<Regra> regras = new ArrayList<>();
        regras.add(regra1);
        regras.add(regra2);

        Resolucao resolucao = new Resolucao("R1", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);

        this.basicResolucaoDAO.insertResolucao(resolucao);
        assertNotNull(this.basicResolucaoDAO.findResolucaoById(id));
        this.dropCollection();
    }

    @Test
    public void testAllFindResolucaoExistente() {

        List<String> dependeDe = new ArrayList<>();
        dependeDe.add("B = 1 + 1");
        dependeDe.add("C = 2 * 5");
        Regra regra1 = new Regra("A", Regra.EXPRESSAO, "Regra de teste",
                60, 0, "B + C", null, null, "RELATO", 52, dependeDe);

        List<String> dependeDe2 = new ArrayList<>();
        dependeDe2.add("X = 1 + 1");
        dependeDe2.add("Y = 2");
        dependeDe2.add("C = 2*5");
        Regra regra2 = new Regra("Z", Regra.CONDICIONAL, "Regra de teste condicional",
                10, 5, " X > Y", "2*C", "C-2", "RELATO", 52, dependeDe2);

        List<Regra> regras = new ArrayList<>();
        regras.add(regra1);
        regras.add(regra2);

        Resolucao resolucao1 = new Resolucao("R1", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);
        Resolucao resolucao2 = new Resolucao("R2", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);

        this.basicResolucaoDAO.insertResolucao(resolucao1);
        this.basicResolucaoDAO.insertResolucao(resolucao2);

        HashSet<Resolucao> setResolucaos = new HashSet<>();
        assertEquals("As coleções inseridos e buscados são iguais.", setResolucaos, this.basicResolucaoDAO.findAllResolucao());
        this.dropCollection();
    }

    @Test
    public void testUpdateResolucao() {

        List<String> dependeDe = new ArrayList<>();
        dependeDe.add("B = 1 + 1");
        dependeDe.add("C = 2 * 5");
        Regra regra1 = new Regra("A", Regra.EXPRESSAO, "Regra de teste",
                60, 0, "B + C", null, null, "RELATO", 52, dependeDe);

        List<String> dependeDe2 = new ArrayList<>();
        dependeDe2.add("X = 1 + 1");
        dependeDe2.add("Y = 2");
        dependeDe2.add("C = 2*5");
        Regra regra2 = new Regra("Z", Regra.CONDICIONAL, "Regra de teste condicional",
                10, 5, " X > Y", "2*C", "C-2", "RELATO", 52, dependeDe2);

        List<Regra> regras = new ArrayList<>();
        regras.add(regra1);
        regras.add(regra2);

        Resolucao resolucao1 = new Resolucao("R1", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);
        Resolucao resolucao2 = new Resolucao("R2", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);

        this.basicResolucaoDAO.insertResolucao(resolucao1);
        this.basicResolucaoDAO.updateResolucaoById(resolucao1.getId(), resolucao2);

        assertEquals("A coleção foi atualizada.", resolucao2.getNome(), this.basicResolucaoDAO.findResolucaoById("R1").getNome());
        this.dropCollection();
    }

    @Test(expected = IdentificadorExistente.class)
    public void testUpdateResolucaoExistente() {

        List<String> dependeDe = new ArrayList<>();
        dependeDe.add("B = 1 + 1");
        dependeDe.add("C = 2 * 5");
        Regra regra1 = new Regra("A", Regra.EXPRESSAO, "Regra de teste",
                60, 0, "B + C", null, null, "RELATO", 52, dependeDe);

        List<String> dependeDe2 = new ArrayList<>();
        dependeDe2.add("X = 1 + 1");
        dependeDe2.add("Y = 2");
        dependeDe2.add("C = 2*5");
        Regra regra2 = new Regra("Z", Regra.CONDICIONAL, "Regra de teste condicional",
                10, 5, " X > Y", "2*C", "C-2", "RELATO", 52, dependeDe2);

        List<Regra> regras = new ArrayList<>();
        regras.add(regra1);
        regras.add(regra2);

        Resolucao resolucao1 = new Resolucao("R1", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);
        Resolucao resolucao2 = new Resolucao("R2", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);
        Resolucao resolucao3 = new Resolucao("R3", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);
                
        this.basicResolucaoDAO.insertResolucao(resolucao1);
        this.basicResolucaoDAO.insertResolucao(resolucao2);
        this.basicResolucaoDAO.updateResolucaoById(resolucao1.getId(), resolucao3);

        assertEquals("A coleção nao foi atualizada.", resolucao3.getNome(), this.basicResolucaoDAO.findResolucaoById("tId2").getNome());
        this.dropCollection();
    }

    @Test(expected = IdentificadorDesconhecido.class)
    public void testUpdateResolucaoInexistente() {

        List<String> dependeDe = new ArrayList<>();
        dependeDe.add("B = 1 + 1");
        dependeDe.add("C = 2 * 5");
        Regra regra1 = new Regra("A",Regra.EXPRESSAO, "Regra de teste",
                60, 0, "B + C", null, null, "RELATO" , 52 , dependeDe);

        List<String> dependeDe2 = new ArrayList<>();
        dependeDe2.add("X = 1 + 1");
        dependeDe2.add("Y = 2");
        dependeDe2.add("C = 2*5");
        Regra regra2 = new Regra("Z",Regra.CONDICIONAL, "Regra de teste condicional",
                10, 5, " X > Y", "2*C", "C-2", "RELATO" , 52 , dependeDe2);

        List<Regra> regras = new ArrayList<>();
        regras.add(regra1);
        regras.add(regra2);

        Resolucao resolucao1 = new Resolucao("R1", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);
        Resolucao resolucao2 = new Resolucao("R2", "Resolucao 1", "Resolucao de testes", Calendar.getInstance().getTime(), regras);
        
        this.basicResolucaoDAO.insertResolucao(resolucao1);
        this.basicResolucaoDAO.updateResolucaoById("tId3", resolucao2);

        assertEquals("A coleção nao foi atualizada.", resolucao2.getNome(), this.basicResolucaoDAO.findResolucaoById("tId3").getNome());
        this.dropCollection();
    }
}
