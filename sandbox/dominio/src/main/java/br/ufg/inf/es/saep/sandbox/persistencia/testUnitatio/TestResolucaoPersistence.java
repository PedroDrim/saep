/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.testUnitatio;

import br.ufg.inf.es.saep.sandbox.dominio.Resolucao;
import br.ufg.inf.es.saep.sandbox.dominio.Tipo;
import br.ufg.inf.es.saep.sandbox.persistencia.DAO.ResolucaoPersistence;
import com.mongodb.client.MongoDatabase;

/**
 *
 * @author pedro
 */
public class TestResolucaoPersistence {

    public static void teste1(MongoDatabase database){
        
        TestResolucao.test1();
        Resolucao resolucao = TestResolucao.get();
        
        TestTipo.test1();
        Tipo tipo = TestTipo.get();
        
        ResolucaoPersistence resolucaoPersistence = new ResolucaoPersistence(database);
        
        resolucaoPersistence.persisteTipo(tipo);
    }
}
