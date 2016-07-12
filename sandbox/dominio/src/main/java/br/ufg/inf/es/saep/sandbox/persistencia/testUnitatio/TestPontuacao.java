/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.testUnitatio;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.Pontuacao;
import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepConversor;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepDeconversor;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class TestPontuacao {

    public static void test1() {

        Valor valor1 = new Valor("OI");

        Pontuacao pontuacao = new Pontuacao("teste", valor1);
        Document doc = SaepConversor.convertPontuacaoToDocument(pontuacao, Atributo.STRING);
        
        Pontuacao pontuacaoR = SaepDeconversor.deconvertDocumentToPontuacao(doc);
        
        System.out.println("Fim do teste");
    }

}
