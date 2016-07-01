/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia;

import br.ufg.inf.es.saep.sandbox.dominio.Atributo;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepConversor;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepDeconversor;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class TestAtributo {

    public static void test1() {

        Atributo atributo1 = new Atributo("T_att1", "Atributo de teste", Atributo.LOGICO);
        Document docAtributo1 = SaepConversor.convertAtributoToDocument(atributo1);
        System.out.println("Atributo1 : " + docAtributo1.toJson());

        Atributo atributo2 = new Atributo("T_att2", "Atributo de teste 2", Atributo.REAL);
        Document docAtributo2 = SaepConversor.convertAtributoToDocument(atributo2);
        System.out.println("Atributo2 : " + docAtributo2.toJson());

        Atributo atributo3 = new Atributo("T_att3", "Atributo de teste 3", Atributo.STRING);
        Document docAtributo3 = SaepConversor.convertAtributoToDocument(atributo3);
        System.out.println("Atributo3 : " + docAtributo3.toJson());

        Atributo atributo1R = SaepDeconversor.deconvertDocumentToAtributo(docAtributo1);
        Atributo atributo2R = SaepDeconversor.deconvertDocumentToAtributo(docAtributo2);
        Atributo atributo3R = SaepDeconversor.deconvertDocumentToAtributo(docAtributo3);
    
        System.out.println("Fim do teste");
    }
}
