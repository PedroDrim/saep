/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufg.inf.es.saep.sandbox.persistencia.testUnitatio;

import br.ufg.inf.es.saep.sandbox.dominio.Regra;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepConversor;
import br.ufg.inf.es.saep.sandbox.persistencia.Serialization.SaepDeconversor;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author pedro
 */
public class TestRegra {

    public static void test1() {

        List<String> dependeDe = new ArrayList<>();
        dependeDe.add("B = 1 + 1");
        dependeDe.add("C = 2 * 5");

        Regra regra1 = new Regra("A",Regra.EXPRESSAO, "Regra de teste",
                60, 0, "B + C", null, null, "RELATO" , 52 , dependeDe);

        Document regraDoc1 = SaepConversor.convertRegraToDocument(regra1);
        System.out.println("Regra1 : " + regraDoc1.toJson());

        List<String> dependeDe2 = new ArrayList<>();
        dependeDe2.add("X = 1 + 1");
        dependeDe2.add("Y = 2");
        dependeDe2.add("C = 2*5");

        Regra regra2 = new Regra("Z",Regra.CONDICIONAL, "Regra de teste condicional",
                10, 5, " X > Y", "2*C", "C-2", "RELATO" , 52 , dependeDe2);

        Document regraDoc2 = SaepConversor.convertRegraToDocument(regra2);
        System.out.println("Regra2 : " + regraDoc2.toJson());

        Regra regra1R = SaepDeconversor.deconvertDocumentToRegra(regraDoc1);
        Regra regra2R = SaepDeconversor.deconvertDocumentToRegra(regraDoc2);

        System.out.println("Fim do teste");
    }
}
