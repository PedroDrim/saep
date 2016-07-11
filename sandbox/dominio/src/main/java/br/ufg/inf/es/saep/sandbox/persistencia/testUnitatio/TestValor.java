/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.testUnitatio;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.dominio.Valor;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepConversor;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepDeconversor;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class TestValor {

    public static void test1() {

        Valor valor1 = new Valor("Oi");
        Document docValor1 = SaepConversor.convertValorToDocument(valor1, Atributo.STRING);
        System.out.println("Valor1 : " + docValor1.toJson());

        Valor valor2 = new Valor(true);
        Document docValor2 = SaepConversor.convertValorToDocument(valor2, Atributo.LOGICO);
        System.out.println("Valor2 : " + docValor2.toJson());

        Valor valor3 = new Valor(120);
        Document docValor3 = SaepConversor.convertValorToDocument(valor3, Atributo.REAL);
        System.out.println("Valor3 : " + docValor3.toJson());

        Valor docValorR1 = SaepDeconversor.deconvertDocumentToValor(docValor1);
        Valor docValorR2 = SaepDeconversor.deconvertDocumentToValor(docValor2);
        Valor docValorR3 = SaepDeconversor.deconvertDocumentToValor(docValor3);
    
        System.out.println("Fim do teste");
    }
}
